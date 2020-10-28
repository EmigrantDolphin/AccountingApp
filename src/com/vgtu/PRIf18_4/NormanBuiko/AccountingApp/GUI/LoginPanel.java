package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.GUI;

import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.UserManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    private AccountingAppForm accountingAppForm;

    private JPasswordField fieldPassword;
    private JLabel labelPassword;
    private JTextField fieldUsername;
    private JLabel labelUsername;
    private JButton buttonLogin;
    private JButton buttonHelpCredentials;
    private final UserManager userManager = new UserManager();

    public LoginPanel(AccountingAppForm accAppForm){
        this.accountingAppForm = accAppForm;
        this.setBorder(new EmptyBorder(100, 200, 200, 200));
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        init();
    }

    private void init(){
        labelUsername = new JLabel("Username: ");
        add(labelUsername);
        fieldUsername = new JTextField();
        add(fieldUsername);

        labelPassword = new JLabel("Password: ");
        add(labelPassword);
        fieldPassword = new JPasswordField();
        add(fieldPassword);

        buttonLogin = new JButton();
        buttonLogin.setText("Login");
        add(buttonLogin);

        buttonHelpCredentials = new JButton();
        buttonHelpCredentials.setText("Recover credentials");
        add(buttonHelpCredentials);

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
                JOptionPane.showMessageDialog(null, "Username: root, Password: admin. (Only system admins can view and edit User List)");
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

        //this.setVisible(false);
        accountingAppForm.OpenMainPanel();
    }
}
