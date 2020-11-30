package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Repositories;

import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models.Record;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Repositories.Abstract.AccountingAppAbstraction;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Repositories.Interfaces.IRepository;

import java.sql.*;
import java.util.ArrayList;

public class RecordRepository extends AccountingAppAbstraction implements IRepository<Record> {


    @Override
    public void add(Record recordToAdd) throws SQLException {
        var connection = getConnection();
        connection.setAutoCommit(false);

        var query = "insert into records(name, amount, creationDate, userCreatorId, categoryId, isSpending) values(?,?,?,?,?,?)";

        PreparedStatement pStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        pStatement.setString(1, recordToAdd.name);
        pStatement.setDouble(2, recordToAdd.amount);
        pStatement.setTimestamp(3, Timestamp.valueOf(recordToAdd.creationDate));
        pStatement.setInt(4, recordToAdd.userCreator.getId());
        pStatement.setInt(5, recordToAdd.getCategoryId());
        pStatement.setBoolean(6, recordToAdd.isSpending);

        var affectedRows = pStatement.executeUpdate();
        if (affectedRows == 0){
            connection.rollback();
            throw new SQLException("Failed to add a record");
        }

        ResultSet generatedKeys = pStatement.getGeneratedKeys();
        generatedKeys.next();

        try{
            recordToAdd.setId(generatedKeys.getInt(1));
            connection.commit();
        }catch (Exception e){
            connection.rollback();
        }
    }

    @Override
    public ArrayList<Record> getAll() throws SQLException {
        ArrayList<Record> records = new ArrayList<>();
        var connection = getConnection();
        Statement statement = connection.createStatement();

        var query ="select * from records";

        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()){
            Record record = new Record();
            record.name = resultSet.getString("name");
            record.amount = resultSet.getDouble("amount");
            record.creationDate = resultSet.getTimestamp("creationDate").toLocalDateTime();
            record.isSpending = resultSet.getBoolean("isSpending");


            try{
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

    @Override
    public void update(Record updatedRecord) throws SQLException {
        var connection = getConnection();
        var query = "update `records` set " +
                "`name` = ?," +
                "`amount` = ?" +
                "where `id` = ?";
        PreparedStatement pStatement = connection.prepareStatement(query);
        pStatement.setString(1, updatedRecord.name);
        pStatement.setDouble(2, updatedRecord.amount);
        pStatement.setInt(3, updatedRecord.getId());

        pStatement.executeUpdate();
    }

    @Override
    public void delete(Record recordToDelete) throws SQLException {
        var connection = getConnection();
        var query = "delete from `records` where `id` = ?";

        PreparedStatement pStatement = connection.prepareStatement(query);
        pStatement.setInt(1, recordToDelete.getId());

        pStatement.executeUpdate();
    }
}
