package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp;

import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Interfaces.IApp;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Interfaces.IUserManager;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models.RootCategory;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models.UserView;

import java.util.ArrayList;
import java.util.Scanner;

public class App implements IApp {

    private final Scanner scanner = new Scanner(System.in);
    private final IUserManager userManager = new UserManager();
    private final ArrayList<RootCategory> rootCategories = new ArrayList<>();
    private final ArrayList<UserView> allUserViews = new ArrayList<>();

    public App(){
    }

    public void Run(){
        loginLoop();
        actionLoop();
    }

    private void loginLoop(){
        while (userManager.getLoggedInUser() == null){
            System.out.println("User is not logged in or failed to login...");
            System.out.println("System admin username: 'root' , password: 'admin'");
            System.out.println("normal username: 'user' , password: 'pass'");
            System.out.println("Please login:");
            System.out.print("username: ");
            var username = scanner.nextLine();
            System.out.print("password: ");
            var password = scanner.nextLine();

            userManager.login(username, password);
        }
        System.out.printf("Logged in successfully. Welcome %s :)",userManager.getLoggedInUser().name);
    }

    private void actionLoop(){
        boolean exiting = false;
        while(!exiting){
            System.out.println("Choose an action:");
            System.out.println("1. Categories");
            System.out.println("2. Users (Sys admin only)");
            System.out.println("3. exit");
            System.out.print("Action(number): ");
            var action = scanner.nextInt();

            switch (action){
                case 1:
                    CategoryLoop();
                    break;
                case 2:
                    if (userManager.getLoggedInUser().isSystemAdmin){
                        userManager.loop();
                    }else{
                        System.out.println("You're not system admin");
                    }
                    break;
                case 3:
                    exiting = true;
                    continue;
            }
        }
    }

    private void CategoryLoop(){}
}
