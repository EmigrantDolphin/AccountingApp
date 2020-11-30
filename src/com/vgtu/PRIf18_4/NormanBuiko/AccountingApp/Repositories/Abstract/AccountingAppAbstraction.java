package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Repositories.Abstract;

import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.GUI.AccountingAppForm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AccountingAppAbstraction {
    private static String url = "jdbc:mysql://localhost:3306/accounting_app_store";
    private static Connection connection;

    protected Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()){
            if (connection == null){
                disconnectOnWindowClosing();
            }

            try{
                Class.forName("com.mysql.jdbc.Driver");
            }catch (ClassNotFoundException ex){
                throw new SQLException(ex);
            }

            connection = DriverManager.getConnection(url,"user","");
        }
        return connection;
    }

    private void disconnectOnWindowClosing(){
        AccountingAppForm.OnClosing.add(
                new Runnable() {
                    @Override
                    public void run() {
                        try{
                            connection.close();
                        }catch (SQLException e){
                            System.out.println("Failed closing sql connection. " + e.getMessage());
                        }
                    }
                }
        );
    }

}
