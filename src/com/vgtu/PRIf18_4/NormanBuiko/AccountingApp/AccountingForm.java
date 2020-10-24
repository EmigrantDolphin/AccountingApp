package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountingForm {
    public JPanel panelMain;
    private JPanel panelLogin;
    private JPasswordField fieldPassword;
    private JTextField fieldUsername;
    private JButton buttonLogin;
    private JPanel panelApp;
    private JButton buttonHelpCredentials;
    private JTabbedPane tabbedPanel;
    private JPanel categoryPanel;
    private JPanel userPanel;
    private final UserManager userManager = new UserManager();

    public AccountingForm(){
        registerListeners();
        panelApp.setVisible(false);
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

        panelApp.setVisible(true);
        panelLogin.setVisible(false);
    }
}
