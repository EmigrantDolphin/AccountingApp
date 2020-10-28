package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.GUI;

import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.UserManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class AccountingAppForm extends JFrame {
    private LoginPanel loginPanel;
    private MainTabbedPane mainPanel;

    public static ArrayList<Runnable> OnClosing = new ArrayList<>();

    public AccountingAppForm(){
        loginPanel = new LoginPanel(this);

        setupOnCloseEvent();

        this.setPreferredSize(new Dimension(600, 400));
        this.add(loginPanel);
    }

    public void OpenMainPanel(){
        this.remove(loginPanel);
        mainPanel = new MainTabbedPane(this);
        this.add(mainPanel);
        revalidate();
    }

    private void setupOnCloseEvent(){
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);

                UserManager.getUserManager().save();

                for (var onClosingEvent : OnClosing){
                    onClosingEvent.run();
                }
            }
        });
    }
}
