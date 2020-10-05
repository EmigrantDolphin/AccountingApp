package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp;

import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Interfaces.IUI;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models.Category;

import java.util.Scanner;

public class CategoryManagerUI implements IUI<CategoryManager> {
    private final Scanner scanner = new Scanner(System.in);
    private CategoryManager categoryManager;

    @Override
    public void loop(CategoryManager categoryManager) {
        this.categoryManager = categoryManager;
        boolean exiting = false;

        while(!exiting){
            System.out.println("\n1. Add category");
            System.out.println("2. Read categories");
            System.out.println("3. update category");
            System.out.println("4. remove category");
            System.out.println("5. stats");
            System.out.println("6. go to category");
            System.out.println("7. go back a category");
            System.out.println("8. go back");

            int choice = 0;

            try{
                choice = scanner.nextInt();
                if (scanner.hasNextLine()){
                    scanner.nextLine();
                }
            }catch (Exception e){
                System.out.println("Please input a number...");
                scanner.nextLine();
            }

            switch (choice){
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
                    printCurrentCategory();
                    break;
                case 6:
                    goToCategory();
                    break;
                case 7:
                    goBackACategory();
                    break;
                case 8:
                    exiting = true;
                    break;

            }
        }
    }

    private void printCurrentCategory(){
        var curCat = categoryManager.currentCategory;

        System.out.printf("Category name: %s\n",curCat.getName());
        System.out.printf("Total income: %.2f\n",curCat.getTotalIncome());
        System.out.printf("Total spending: %.2f\n",curCat.getTotalSpending());
        System.out.printf("Total income with subCategories: %.2f\n",curCat.getTotalIncomeWithSubCategories());
        System.out.printf("Total spending with subCategories: %.2f\n",curCat.getTotalSpendingWithSubCategories());
    }

    private void goToCategory(){
        System.out.print("Type category id to go to:");
        int index = -1;

        try{
            index = scanner.nextInt();
            if (scanner.hasNextLine()){
                scanner.nextLine();
            }
        }catch (Exception e){
            System.out.println("Error. Not a number");
            return;
        }

        if (index < categoryManager.currentCategory.subCategories.size()){
            var category = categoryManager.currentCategory.subCategories.get(index);
            categoryManager.currentCategory = category;
        }
    }

    private void goBackACategory(){
        if (categoryManager.currentCategory.parentCategory != null){
            categoryManager.currentCategory = categoryManager.currentCategory.parentCategory;
        }
    }

    private void add(){
        System.out.println("________Category creation________");
        System.out.print("name: ");
        var name = scanner.nextLine();
        var loggedInUser = UserManager.getLoggedInUser();
        var category = new Category(loggedInUser, name, categoryManager.currentCategory);
        categoryManager.add(category);
    }

    private void read(){
        var categories = categoryManager.read();
        System.out.println("Category names: ");
        for (int i = 0; i < categories.size(); i++){
            System.out.printf("%d. %s\n", i, categories.get(i).getName());
        }
    }

    private void update(){
        System.out.print("Type category id to update:");
        int index = -1;

        try{
            index = scanner.nextInt();
            if (scanner.hasNextLine()){
                scanner.nextLine();
            }
        }catch (Exception e){
            System.out.println("Error. Not a number");
            return;
        }

        if (index < categoryManager.currentCategory.subCategories.size()){
            var category = categoryManager.currentCategory.subCategories.get(index);

            System.out.print("name: ");
            var name = scanner.nextLine();

            category.setName(name);
        }
    }

    private void remove(){
        System.out.print("Type category id to remove:");
        int index = -1;

        try{
            index = scanner.nextInt();
        }catch (Exception e){
            System.out.println("Error. Not a number");
            return;
        }

        if (index < categoryManager.currentCategory.subCategories.size()){
            categoryManager.currentCategory.subCategories.remove(index);
        }
    }
}
