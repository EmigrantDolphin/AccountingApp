package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Repositories;

import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models.Category;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models.Record;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models.User;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models.UserView;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Repositories.Abstract.AccountingAppAbstraction;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Repositories.Interfaces.IRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class CategoryRepository extends AccountingAppAbstraction implements IRepository<Category> {

    @Override
    public void add(Category categoryToAdd) throws SQLException {
        var connection = getConnection();
        connection.setAutoCommit(false);

        var categoryQuery = "insert into categories(name, parentCategory) values(?,?)";
        var categoryAdminsQuery = "insert into categoryAdmins(categoryId, userId) values(?,?)";

        PreparedStatement categoryPStatement = connection.prepareStatement(categoryQuery, Statement.RETURN_GENERATED_KEYS);
        PreparedStatement categoryAdminsPStatement = connection.prepareStatement(categoryAdminsQuery, Statement.RETURN_GENERATED_KEYS);

        categoryPStatement.setString(1, categoryToAdd.getName());
        if (categoryToAdd.parentCategory != null) {
            categoryPStatement.setInt(2, categoryToAdd.parentCategory.getId());
        }else{
            categoryPStatement.setNull(2, Types.INTEGER);
        }


        var affectedRows = categoryPStatement.executeUpdate();
        if (affectedRows == 0){
            connection.rollback();
            throw new SQLException("Failed to add a record");
        }

        ResultSet generatedKeys = categoryPStatement.getGeneratedKeys();
        generatedKeys.next();

        try{
            categoryToAdd.setId(generatedKeys.getInt(1));
        }catch (Exception e){
            connection.rollback();
            throw new SQLException(e);
        }

        for (var admin : categoryToAdd.admins){
            categoryAdminsPStatement.setInt(1, categoryToAdd.getId());
            categoryAdminsPStatement.setInt(2, admin.getId());
            categoryAdminsPStatement.executeUpdate();
        }

        connection.commit();
    }

    @Override
    public ArrayList<Category> getAll() throws SQLException {
        return getCategoriesWithParent(null);
    }

    private ArrayList<Category> getCategoriesWithParent(Category parentCategory) throws SQLException{
        var categories = new ArrayList<Category>();

        var connection = getConnection();

        var categoryQuery ="select * from categories where parentCategory = ?";
        PreparedStatement categoryStatement = connection.prepareStatement(categoryQuery);

        if (parentCategory == null){
            categoryStatement.setInt(1, 0);
        }else{
            categoryStatement.setInt(1, parentCategory.getId());
        }

        ResultSet resultSet = categoryStatement.executeQuery();

        while (resultSet.next()){
            var categoryId = resultSet.getInt("id");
            var categoryName = resultSet.getString("name");
            var adminUsers = getAdminUsersByCategoryId(categoryId);
            var records = getRecordsByCategoryId(categoryId);

            Category category = new Category(adminUsers.get(0), categoryName, parentCategory);
            try{
                category.setId(categoryId);
            }catch (Exception e){
                throw new SQLException(e);
            }
            category.spending = records.stream().filter(rec -> rec.isSpending).collect(Collectors.toCollection(ArrayList::new));
            category.income = records.stream().filter(rec -> !rec.isSpending).collect(Collectors.toCollection(ArrayList::new));
            category.subCategories = getCategoriesWithParent(category);

            categories.add(category);
        }

        return categories;
    }

    @Override
    public void update(Category updatedCategory) throws SQLException {
        var connection = getConnection();
        var query = "update `categories` set " +
                "`name` = ? where `id` = ?";

        PreparedStatement pStatement = connection.prepareStatement(query);
        pStatement.setString(1, updatedCategory.getName());
        pStatement.setInt(2, updatedCategory.getId());

        pStatement.executeUpdate();
    }

    @Override
    public void delete(Category categoryToDelete) throws SQLException {
        if (categoryToDelete.subCategories.size() != 0){
            for(var subCategory : categoryToDelete.subCategories){
                delete(subCategory);
            }
        }

        var connection = getConnection();
        connection.setAutoCommit(false);
        var query = "delete from `categories` where `id` = ?";
        var adminsQuery = "delete from `categoryAdmins` where `categoryId` = ?";
        var recordsQuery = "delete from `records` where `categoryId` = ?";

        PreparedStatement pStatement = connection.prepareStatement(query);
        pStatement.setInt(1, categoryToDelete.getId());

        PreparedStatement adminsPStatement = connection.prepareStatement(adminsQuery);
        adminsPStatement.setInt(1, categoryToDelete.getId());

        PreparedStatement recordsPStatement = connection.prepareStatement(recordsQuery);
        recordsPStatement.setInt(1, categoryToDelete.getId());

        try{
            adminsPStatement.executeUpdate();
            recordsPStatement.executeUpdate();
            pStatement.executeUpdate();
        }catch (SQLException e){
            connection.rollback();
            throw e;
        }

        connection.commit();
    }

    private ArrayList<UserView> getAdminUsersByCategoryId(int id) throws SQLException{
        var adminUsers = new ArrayList<UserView>();
        var connection = getConnection();

        var adminsQuery = "select * from users where id in (select userId from categoryAdmins where categoryId = ?)";
        PreparedStatement adminsStatement = connection.prepareStatement(adminsQuery);
        adminsStatement.setInt(1, id);
        ResultSet resultSet = adminsStatement.executeQuery();


        while (resultSet.next()){
            User user = new User();
            user.username = resultSet.getString("username");
            user.name = resultSet.getString("name");
            user.surname = resultSet.getString("surname");
            user.password = resultSet.getString("password");
            user.isSystemAdmin = resultSet.getBoolean("isSystemAdmin");
            try{
                user.setId(resultSet.getInt("id"));
            }catch (Exception e){
                throw new SQLException(e);
            }

            adminUsers.add(user);
        }

        return adminUsers;
    }

    private ArrayList<Record> getRecordsByCategoryId(int categoryId) throws SQLException{
        ArrayList<Record> records = new ArrayList<>();
        var connection = getConnection();

        var query ="select * from records where categoryId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, categoryId);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){
            Record record = new Record();
            record.name = resultSet.getString("name");
            record.amount = resultSet.getDouble("amount");
            record.creationDate = resultSet.getTimestamp("creationDate").toLocalDateTime();
            record.isSpending = resultSet.getBoolean("isSpending");
            record.userCreator = getUserById(resultSet.getInt("userCreatorId"));

            try{
                //todo: maybe delete this later since the object already has a user object that has an id
                record.setUserCreatorId(resultSet.getInt("userCreatorId"));
                record.setCategoryId(resultSet.getInt("categoryId"));
                record.setId(resultSet.getInt("id"));
            }catch (Exception e){
                throw new SQLException(e);
            }

            records.add(record);
        }

        return records;
    }

    private User getUserById(int userId) throws SQLException{
        var connection = getConnection();

        var query ="select * from users where id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);

        ResultSet resultSet = statement.executeQuery();

        resultSet.next();
        User user = new User();
        user.username = resultSet.getString("username");
        user.name = resultSet.getString("name");
        user.surname = resultSet.getString("surname");
        user.password = resultSet.getString("password");
        user.isSystemAdmin = resultSet.getBoolean("isSystemAdmin");


        try{
            user.setId(resultSet.getInt("id"));
        }catch (Exception e){
            throw new SQLException(e);
        }

        return user;
    }

}
