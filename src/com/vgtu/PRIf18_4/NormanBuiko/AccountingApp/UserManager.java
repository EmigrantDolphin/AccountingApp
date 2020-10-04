package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp;

import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Interfaces.IUserManager;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Interfaces.IUserManagerUI;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Interfaces.Icrud;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models.User;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models.UserView;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class UserManager implements IUserManager, Icrud<User> {

    private final IUserManagerUI userManagerUI = new UserManagerUI();
    private User loggedInUser = null;
    private final ArrayList<User> allUsers = new ArrayList<>();

    public UserManager(){
        var adminUser = new User();
        var normalUser = new User();
        adminUser.username = "root";
        adminUser.password = "admin";
        adminUser.name = "Elon";
        adminUser.surname = "Musk";
        adminUser.isSystemAdmin = true;

        normalUser.username = "user";
        normalUser.password = "pass";
        normalUser.name = "Jeff";
        normalUser.surname = "Bezos";
        normalUser.isSystemAdmin = false;

        allUsers.add(adminUser);
        allUsers.add(normalUser);
    }

    @Override
    public UserView getLoggedInUser() {
        return loggedInUser ;
    }

    @Override
    public void login(String username, String password) {
        loggedInUser = allUsers.stream()
                .filter(u -> u.username.equals(username) && u.password.equals(password))
                .findFirst()
                .orElse(null);
    }

    @Override
    public ArrayList<UserView> getAllUserViews() {
        var userViews = allUsers.stream().map(u -> (UserView)u).collect(Collectors.toList());
        return (ArrayList<UserView>)userViews;
    }

    @Override
    public void loop() {
        userManagerUI.Loop(this);
    }

    @Override
    public void add(User item) {
        if (loggedInUser != null && loggedInUser.isSystemAdmin){
            var user = findUser(item);
            if (user == null){
                allUsers.add(item);
            }
        }
    }

    @Override
    public void remove(User item) {
        if (loggedInUser != null && loggedInUser.isSystemAdmin){
            var userToRemove = findUser(item);
            if (userToRemove != null){
                allUsers.remove(userToRemove);
            }
        }
    }

    @Override
    public void update(User item) {
        if (loggedInUser != null && loggedInUser.isSystemAdmin){
            var userToUpdate = findUser(item);
            if (userToUpdate != null){
                //todo: this probs doesn't work. it overwrites a reference in local var, not list.
                userToUpdate = item;
            }
        }
    }

    @Override
    public ArrayList<User> read() {
        return allUsers;
    }

    private User findUser(User user){
        return allUsers.stream().filter(u -> u.username.equals(user.username)).findFirst().orElse(null);
    }
}
