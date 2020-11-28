package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Repositories.Abstract;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AccountingAppAbstraction {
    private static String url = "jdbc:mysql://localhost:3306/accounting_app_store";
    private static Connection connection;

    protected Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()){

            try{
                Class.forName("com.mysql.jdbc.Driver");
            }catch (ClassNotFoundException ex){
                throw new SQLException(ex);
            }

            Connection connection = DriverManager.getConnection(url,"user","");
            return connection;
        }
        else{
            return connection;
        }
    }

    //TODO: Register an on closing event to disconnect from database

}
