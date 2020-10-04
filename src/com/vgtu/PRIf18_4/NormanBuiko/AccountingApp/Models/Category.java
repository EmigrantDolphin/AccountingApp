package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Category {
    private String name;
    private ArrayList<UserView> admins = new ArrayList<>();
    public ArrayList<Record> spending = new ArrayList<>();
    public ArrayList<Record> income = new ArrayList<>();
    public ArrayList<Category> subCategories = new ArrayList<>();

    public Category(UserView admin, String name){
        admins.add(admin);
        this.name = name;
    }

    public Double GetTotalSpending(){
        return spending.stream().mapToDouble(a -> a.amount).sum();
    }

    public Double GetTotalIncome(){
        return income.stream().mapToDouble(a -> a.amount).sum();
    }

    public Double GetTotalIncomeWithSubCategories(){
        var sum = income.stream().mapToDouble(a -> a.amount).sum();
        sum += subCategories.stream().mapToDouble(sc -> sc.GetTotalIncomeWithSubCategories()).sum();
        return sum;
    }

    public Double GetTotalSpendingWithSubCategories(){
        var sum = spending.stream().mapToDouble(a -> a.amount).sum();
        sum += subCategories.stream().mapToDouble(sc -> sc.GetTotalSpendingWithSubCategories()).sum();
        return sum;
    }

    public void SetAdmins(UserView loggedInUserView, ArrayList<UserView> usersToAdd){
        if (!isAdmin(loggedInUserView)){
            return;
        }

        admins.addAll(usersToAdd);
    }

    public ArrayList<UserView> GetAdmins(){
        return admins;
    }

    public void SetName(UserView loggedInUserView, String name){
        if (!isAdmin(loggedInUserView)){
            return;
        }

        this.name = name;
    }

    public String getName(){
        return name;
    }

    private boolean isAdmin(UserView userView){
        return admins.stream().map(a -> a.username).collect(Collectors.toList()).contains(userView.username);
    }
}
