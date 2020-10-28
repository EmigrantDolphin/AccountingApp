package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp;

import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.GUI.AccountingAppForm;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new AccountingAppForm();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); //trick to start the window in the middle of the screen
        frame.pack();
        frame.setVisible(true);
    }
}
