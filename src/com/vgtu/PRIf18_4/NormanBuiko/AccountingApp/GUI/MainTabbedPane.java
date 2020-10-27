package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.GUI;

import javax.swing.*;

public class MainTabbedPane extends JTabbedPane{
    private AccountingAppForm accountingAppForm;

    private UserPanel userPanel;
    private CategoryPanel categoryPanel;

    public MainTabbedPane(AccountingAppForm accAppForm){
        this.accountingAppForm = accAppForm;
        userPanel = new UserPanel();
        categoryPanel = new CategoryPanel();
        this.addTab("Users", userPanel);
        this.addTab("Categories", categoryPanel);
    }
}