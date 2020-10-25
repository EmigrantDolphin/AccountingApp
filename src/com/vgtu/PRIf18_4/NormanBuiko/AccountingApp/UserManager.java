package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp;

import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Interfaces.ISaveable;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Interfaces.IUserManager;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Interfaces.IUI;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Interfaces.Icrud;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models.User;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models.UserView;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class UserManager implements IUserManager, Icrud<User>, ISaveable {

    private final IUI<UserManager> userManagerUI = new UserManagerUI();
    private static User loggedInUser = null;
    private final ArrayList<User> allUsers = new ArrayList<>();

    private final FileDriver<ArrayList<User>> fileDriver = new FileDriver<>();
    private final String userPath = "./userStorage.txt";

    private static UserManager userManager;

    public UserManager(){
        var loadedUsers = fileDriver.importFile(userPath);
        if (loadedUsers != null){
            allUsers.addAll(loadedUsers);
        }

        var adminUser = new User();
        adminUser.username = "root";
        adminUser.password = "admin";
        adminUser.name = "Elon";
        adminUser.surname = "Musk";
        adminUser.isSystemAdmin = true;

        allUsers.add(adminUser);
    }
    public static UserManager getUserManager(){
        if (userManager == null){
            userManager = new UserManager();
        }
        return userManager;
    }

    public static UserView getLoggedInUser() {
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
    public void loop() {
        userManagerUI.loop(this);
    }

    @Override
    public void add(User item) {
        if (loggedInUser != null && loggedInUser.isSystemAdmin){
            var index = allUsers.indexOf(item);
            if (index >= 0){
                System.out.printf("User with name '%s' already exists\n", item.username);
            }else{
                allUsers.add(item);
            }
        }
    }

    @Override
    public void remove(User item) {
        if (loggedInUser != null && loggedInUser.isSystemAdmin){
            var index = allUsers.indexOf(item);
            if (index >= 0){
                allUsers.remove(item);
            }
        }
    }

    @Override
    public void update(User item) {
        if (loggedInUser != null && loggedInUser.isSystemAdmin){
            var index = allUsers.indexOf(item);
            if (index >= 0){
                allUsers.set(index, item);
            }
        }
    }

    @Override
    public ArrayList<User> read() {
        return allUsers;
    }

    @Override
    public void save(){
        var usersWithoutRoot = allUsers.stream().filter(u -> !u.username.equals("root")).collect(Collectors.toCollection(ArrayList::new));
        fileDriver.exportFile(usersWithoutRoot, userPath);
    }
}
