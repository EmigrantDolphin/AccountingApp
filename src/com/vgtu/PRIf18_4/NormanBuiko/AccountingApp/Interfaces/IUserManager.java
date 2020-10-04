package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Interfaces;

import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models.UserView;

import java.util.ArrayList;

public interface IUserManager {
    public UserView getLoggedInUser();
    public void login(String username, String password);
    public ArrayList<UserView> getAllUserViews();
    public void loop();
}
