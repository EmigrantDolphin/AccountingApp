package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.GUI;

import javax.swing.*;

public class MainTabbedPane extends JTabbedPane{
    private AccountingAppForm accountingAppForm;

    private UserPanel userPanel;

    public MainTabbedPane(AccountingAppForm accAppForm){
        this.accountingAppForm = accAppForm;
        userPanel = new UserPanel();
        this.addTab("Users", userPanel);
    }
}