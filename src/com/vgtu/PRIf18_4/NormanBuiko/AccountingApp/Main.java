package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
//        var app = new App();
//        app.Run();
        JFrame frame = new JFrame("Accounting App");
        frame.setContentPane(new AccountingForm().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
