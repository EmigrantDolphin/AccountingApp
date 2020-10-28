package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.GUI;

import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.UserManager;

import javax.swing.*;

public class MainTabbedPane extends JTabbedPane{
    private AccountingAppForm accountingAppForm;

    private UserPanel userPanel;
    private CategoryPanel categoryPanel;

    public MainTabbedPane(AccountingAppForm accAppForm){
        this.accountingAppForm = accAppForm;

        if (UserManager.getLoggedInUser().isSystemAdmin){
            userPanel = new UserPanel();
            this.addTab("Users", userPanel);
        }

        categoryPanel = new CategoryPanel();
        this.addTab("Categories", categoryPanel);
    }
}