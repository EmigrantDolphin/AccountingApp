package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.GUI;

import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.UserManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm {
    public JPanel panelMain;
    private JPanel panelLogin;
    private JPasswordField fieldPassword;
    private JTextField fieldUsername;
    private JButton buttonLogin;
    private JButton buttonHelpCredentials;
    private final UserManager userManager = new UserManager();

    public LoginForm(){
        registerListeners();
    }

    private void registerListeners(){
        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        buttonHelpCredentials.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Login: root, Password: admin");
            }
        });
    }

    private void login(){
        var username = fieldUsername.getText();
        var password = String.valueOf(fieldPassword.getPassword());
        userManager.login(username, password);
        if (UserManager.getLoggedInUser() == null){
            JOptionPane.showMessageDialog(null, "username or password is wrong");
            return;
        }

        panelLogin.setVisible(false);
    }
}
