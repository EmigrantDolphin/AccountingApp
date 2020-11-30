package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models;

import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.UserManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Category extends BaseModel implements Serializable {
    private String name;
    public ArrayList<UserView> admins = new ArrayList<>();
    public ArrayList<Record> spending = new ArrayList<>();
    public ArrayList<Record> income = new ArrayList<>();
    public Category parentCategory;
    public ArrayList<Category> subCategories = new ArrayList<>();

    public Category(UserView admin, String name){
        admins.add(admin);
        this.name = name;
    }

    public Category(UserView admin, String name, Category parentCategory){
        this(admin, name);
        this.parentCategory = parentCategory;
    }


    public Double getTotalSpending(){
        return spending.stream().mapToDouble(a -> a.amount).sum();
    }

    public Double getTotalIncome(){
        return income.stream().mapToDouble(a -> a.amount).sum();
    }

    public Double getTotalIncomeWithSubCategories(){
        var sum = income.stream().mapToDouble(a -> a.amount).sum();
        sum += subCategories.stream().mapToDouble(sc -> sc.getTotalIncomeWithSubCategories()).sum();
        return sum;
    }

    public Double getTotalSpendingWithSubCategories(){
        var sum = spending.stream().mapToDouble(a -> a.amount).sum();
        sum += subCategories.stream().mapToDouble(sc -> sc.getTotalSpendingWithSubCategories()).sum();
        return sum;
    }

    public void setName(String name){
        if (!isAdmin(UserManager.getLoggedInUser())){
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
