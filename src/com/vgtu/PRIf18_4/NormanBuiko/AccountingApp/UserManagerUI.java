package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp;

import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Interfaces.IUI;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models.User;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models.Wrapper;

import java.util.Scanner;

public class UserManagerUI implements IUI<UserManager> {
    private Scanner scanner = new Scanner(System.in);
    private UserManager userManager;

    @Override
    public void loop(UserManager userManager) {
        this.userManager = userManager;
        boolean exiting = false;

        while(!exiting){
            System.out.println("1. Add user");
            System.out.println("2. Read users");
            System.out.println("3. update user");
            System.out.println("4. remove user");
            System.out.println("5. go back");

            var choiceWrapper = new Wrapper<Integer>();
            if (!Input.TryGetNextInt(choiceWrapper)) continue;

            switch (choiceWrapper.value){
                case 1:
                    add();
                    break;
                case 2:
                    read();
                    break;
                case 3:
                    update();
                    break;
                case 4:
                    remove();
                    break;
                case 5:
                    exiting = true;
                    break;

            }
        }
    }

    private void remove(){
        System.out.print("username to remove: ");
        var usernameToRemove = scanner.nextLine();
        var userToRemove = userManager.read().stream().filter(u -> u.username.equals(usernameToRemove)).findFirst().orElse(null);
        if (userToRemove != null){
            userManager.remove(userToRemove);
            return;
        }
        System.out.println("No user found");
    }

    private void update() {
        System.out.print("username to update: ");
        var usernameToUpdate = scanner.nextLine();
        var userToUpdate = userManager.read().stream().filter(u -> u.username.equals(usernameToUpdate)).findFirst().orElse(null);
        if (userToUpdate == null){
            System.out.println("No user found");
            return;
        }
        System.out.println("User found. Please provide updated info");
        System.out.print("password: ");
        var password = scanner.nextLine();
        System.out.print("first name: ");
        var name = scanner.nextLine();
        System.out.print("surname: ");
        var surname = scanner.nextLine();
        System.out.print("Is system admin? (0 == false, 1 == true): ");
        var isSystemAdminWrapper = new Wrapper<Integer>();
        if (!Input.TryGetNextInt(isSystemAdminWrapper)) return;

        userToUpdate.password = password;
        userToUpdate.name = name;
        userToUpdate.surname = surname;
        userToUpdate.isSystemAdmin = isSystemAdminWrapper.value != 0;

        userManager.update(userToUpdate);
    }

    private void read() {
        var users = userManager.read();
        for(User user : users){
            System.out.println("{");
            System.out.printf("  Username: %s\n", user.username);
            System.out.printf("  Password: %s\n", user.password);
            System.out.printf("  Name: %s\n", user.name);
            System.out.printf("  Surname: %s\n", user.surname);
            System.out.printf("  IsSystemAdmin: %s\n", user.isSystemAdmin);
            System.out.println("}");
        }
    }

    private void add(){
        System.out.print("username: ");
        var username = scanner.nextLine();
        System.out.print("password: ");
        var password = scanner.nextLine();
        System.out.print("first name: ");
        var name = scanner.nextLine();
        System.out.print("surname: ");
        var surname = scanner.nextLine();
        System.out.print("Is system admin? (0 == false, 1 == true): ");
        var isSystemAdminWrapper = new Wrapper<Integer>();
        if (!Input.TryGetNextInt(isSystemAdminWrapper)) return;

        var user = new User();
        user.username = username;
        user.password = password;
        user.name = name;
        user.surname = surname;
        user.isSystemAdmin = isSystemAdminWrapper.value != 0;
        userManager.add(user);
    }
}
