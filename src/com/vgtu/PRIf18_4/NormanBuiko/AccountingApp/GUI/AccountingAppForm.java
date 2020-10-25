package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.GUI;

import javax.swing.*;
import java.awt.*;

public class AccountingAppForm extends JFrame {
    private LoginPanel loginPanel;
    private MainTabbedPane mainPanel;

    public AccountingAppForm(){
        loginPanel = new LoginPanel(this);

        this.setPreferredSize(new Dimension(600, 400));
        this.add(loginPanel);
        //mainPanel = new MainTabbedPane(this);
        //this.add(mainPanel);
    }

    public void OpenMainPanel(){
        this.remove(loginPanel);
        mainPanel = new MainTabbedPane(this);
        this.add(mainPanel);
        revalidate();
    }

    public void OpenLoginPanel(){

    }

}
