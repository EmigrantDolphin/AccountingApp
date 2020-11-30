package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.GUI;

import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models.User;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.UserManager;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserPanel extends JPanel {
    private JPanel entryContainerPanel = new JPanel();
    private JPanel navButtonContainer = new JPanel();

    private JButton addButton;


    public UserPanel(){
        setupUserPanel();
    }

    private void setupUserPanel(){

        this.setLayout(new GridBagLayout());
        this.setBorder(new LineBorder(Color.black));

        setupEntryContainer();
        setupNavButtonContainer();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(entryContainerPanel, gbc);

        gbc.gridy =  1;
        gbc.weighty = 0.1;
        gbc.anchor = GridBagConstraints.EAST;
        this.add(navButtonContainer, gbc);
    }

    private void setupEntryContainer(){
        entryContainerPanel.setLayout(new BoxLayout(entryContainerPanel, BoxLayout.PAGE_AXIS));
        entryContainerPanel.setBorder(new LineBorder(Color.black));
        for (var user : UserManager.getUserManager().read()){
            entryContainerPanel.add(createUserEntryPanel(user));
        }
    }

    private void setupNavButtonContainer(){
        navButtonContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        navButtonContainer.setBorder(new LineBorder(Color.black));

        addButton = new JButton("Add");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField usernameField = new JTextField();
                JTextField passwordField = new JTextField();
                JTextField nameField = new JTextField();
                JTextField surnameField = new JTextField();
                JRadioButton isSysAdmin = new JRadioButton();
                Object[] fields = {
                        "username: ", usernameField,
                        "password: ", passwordField,
                        "name: ", nameField,
                        "surname: ", surnameField,
                        "isSysAdmin: ", isSysAdmin,
                };

                int result = JOptionPane.showConfirmDialog(null, fields, "title", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION){

                    var user = new User();
                    user.username = usernameField.getText();
                    user.password = passwordField.getText();
                    user.name = nameField.getText();
                    user.surname = surnameField.getText();
                    user.isSystemAdmin = isSysAdmin.isSelected();

                    UserManager.getUserManager().add(user);
                    if (user.username.equals("")) return;
                    entryContainerPanel.add(createUserEntryPanel(user));
                }

                revalidate();
            }
        });

        navButtonContainer.add(addButton);
    }

    private JPanel createUserEntryPanel(User user){
        var jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        var usernameLabel = new JLabel(user.username);
        jPanel.add(usernameLabel);

        var viewButton = new JButton("View");
        viewButton.addActionListener(e -> {
            JTextField usernameField = new JTextField(user.username);
            JTextField passwordField = new JTextField(user.password);
            JTextField nameField = new JTextField(user.name);
            JTextField surnameField = new JTextField(user.surname);
            JRadioButton isSysAdmin = new JRadioButton("Is system admin", user.isSystemAdmin);
            JRadioButton shouldRemove = new JRadioButton("Remove user");
            Object[] fields = {
                    "username: ", usernameField,
                    "password: ", passwordField,
                    "name: ", nameField,
                    "surname: ", surnameField,
                    "", isSysAdmin,
                    "", shouldRemove,
            };

            if (user.username.equals("root")){
                usernameField.setEditable(false);
                passwordField.setEditable(false);
                isSysAdmin.setEnabled(false);
                shouldRemove.setEnabled(false);
            }

            int result = JOptionPane.showConfirmDialog(null, fields, "title", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.CANCEL_OPTION)
                return;

            if (shouldRemove.isSelected()){
                UserManager.getUserManager().remove(user);
                entryContainerPanel.remove(jPanel);
            }
            else{
                user.username = usernameField.getText();
                user.password = passwordField.getText();
                user.name = nameField.getText();
                user.surname = surnameField.getText();
                user.isSystemAdmin = isSysAdmin.isSelected();
                usernameLabel.setText(user.username);
                UserManager.getUserManager().update(user);
            }
        });

        jPanel.add(viewButton);
        return jPanel;
    }
}
