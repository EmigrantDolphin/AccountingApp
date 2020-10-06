package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Interfaces;

import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models.UserView;

import java.util.ArrayList;

public interface IUserManager {
    void login(String username, String password);
    void loop();
}
