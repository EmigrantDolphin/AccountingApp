package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp;

import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Interfaces.IUserManager;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Interfaces.Icrud;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models.User;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models.UserView;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Repositories.UserRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserManager implements IUserManager, Icrud<User>{

    private static User loggedInUser = null;
    private final ArrayList<User> allUsers = new ArrayList<>();

    private final UserRepository userRepository = new UserRepository();

    private static UserManager userManager;

    public UserManager(){
        var loadedUsers = loadUsers();

        if (loadedUsers != null){
            allUsers.addAll(loadedUsers);
        }
    }

    private ArrayList<User> loadUsers(){
        try{
            return userRepository.getAll();
        }catch (SQLException e){
            GlobalMessage.show(e.getMessage());
            return null;
        }
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
    public void add(User item) {
        if (loggedInUser != null && loggedInUser.isSystemAdmin){

            try{
                userRepository.add(item);
            }catch (SQLException e){
                GlobalMessage.show(e.getMessage());
                item.username = "";
                return;
            }

            var index = allUsers.indexOf(item);
            if (index >= 0){
                System.out.printf("User with username '%s' already exists\n", item.username);

                try{
                    userRepository.delete(item);
                }catch (SQLException e){
                    GlobalMessage.show(e.getMessage());
                }
            }else{
                allUsers.add(item);
            }
        }
    }

    @Override
    public void remove(User item) {
        if (loggedInUser != null && loggedInUser.isSystemAdmin){

            try{
                userRepository.delete(item);
            }catch (SQLException e){
                var message = String.format("Make sure to delete all categories where this user(%s) is admin first.\n", item.username);
                GlobalMessage.show(message + e.getMessage());
                return;
            }

            var index = allUsers.indexOf(item);
            if (index >= 0){
                allUsers.remove(item);
            }
        }
    }

    @Override
    public void update(User item) {
        if (loggedInUser != null && loggedInUser.isSystemAdmin){

            try{
                userRepository.update(item);
            }catch (SQLException e){
                GlobalMessage.show(e.getMessage());
                return;
            }

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

}
