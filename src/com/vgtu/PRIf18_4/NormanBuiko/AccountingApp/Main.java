package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.GUI.AccountingAppForm;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models.User;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Repositories.UserRepository;

import javax.swing.*;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Main {
    private static String url = "jdbc:mysql://localhost:3306/chatter?createDatabaseIfNotExist=true";
    private static String noDbUrl = "jdbc:mysql://localhost:3306";

    public static Connection getConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,"user","");
            return connection;
        }catch (Exception e){
            return null;
        }
    }


    public static int updateEntry(Connection connection){
        try{
            PreparedStatement pStatement = connection.prepareStatement("update `users` set `username` = ? where `id` = ?");
            pStatement.setString(1, "newUser");
            pStatement.setInt(2, 2);
            return pStatement.executeUpdate();
        }catch (Exception e){
            return -1;
        }
    }

    public static ResultSet selectEntry(Connection connection){
        try{
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * from users");
            return resultSet;

//            while (resultSet.next()){
//                System.out.println(resultSet.getString("username"));
//            }
        }catch(Exception e){
            return null;
        }
    }

    public static boolean insertEntry(Connection connection){
        try{
            String query = "insert into users(username, passwordHash, authToken, createdAt, updatedAt) values(?,?,?,?,?)";
            PreparedStatement pStatement = connection.prepareStatement(query);
            pStatement.setString(1, "SecondUser");
            pStatement.setString(2, "pswHash");
            pStatement.setString(3, "authToken");
            pStatement.setDate(4, new Date(2020, 07, 15));
            pStatement.setDate(5, new Date(2929, 03, 13));

            return pStatement.execute();
        }catch (Exception e){
            return false;
        }
    }

    public static int createTable(Connection connection){
        try{
            String query = "create table if not exists `newTable` (" +
                    "id int not null," +
                    "name varchar(45) not null," +
                    "date date not null)";

            var pStatement = connection.prepareStatement(query);
            return pStatement.executeUpdate();
        }catch(Exception e){
            return -1;
        }
    }



    public static void main(String[] args) {
//        UUID asdf = UUID.randomUUID();
//        try{
//            Connection connection = getConnection();
//            updateEntry(connection);
//            selectEntry(connection);
//            insertEntry(connection);
//            createTable(connection);
//            //delete entry: executeUpdate
//            //create database: executeUpdate but needs root permissions probs
//            //create table: executeUpdate

//        }catch (Exception e){
//            System.out.println("DEEZ CUCKS");
//        }


//        UserRepository ur = new UserRepository();

//        try{
//            var users = ur.getAllUsers();
//            var user = users.get(0);
//            user.name = "John cena";
//            ur.updateUser(user);
//            ur.deleteUser(user);
//        }catch (SQLException e){
//            System.out.println(e.getMessage());
//        }

        JFrame frame = new AccountingAppForm();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); //trick to start the window in the middle of the screen
        frame.pack();
        frame.setVisible(true);
    }
}
