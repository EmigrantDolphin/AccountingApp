package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp;

import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.GUI.AccountingAppForm;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.GUI.LoginForm;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
//        var app = new App();
//        app.Run();
//        JFrame frame = new JFrame("Accounting App");
//        frame.setContentPane(new LoginForm().panelMain);
        JFrame frame = new AccountingAppForm();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); //trick to start the window in the middle of the screen
        frame.pack();
        frame.setVisible(true);
    }
}
