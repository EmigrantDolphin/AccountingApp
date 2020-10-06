package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp;

import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Interfaces.IUI;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models.Category;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models.Record;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models.Wrapper;

import java.util.ArrayList;
import java.util.Scanner;

public class CategoryManagerUI implements IUI<CategoryManager> {
    private final Scanner scanner = new Scanner(System.in);
    private final IUI<ArrayList<Record>> recordUI = new RecordManagerUI();
    private CategoryManager categoryManager;

    @Override
    public void loop(CategoryManager categoryManager) {
        this.categoryManager = categoryManager;
        boolean exiting = false;

        while(!exiting){
            System.out.printf("\nCurrent category: %s\n", categoryManager.currentCategory.getName());
            System.out.println("1. Add category");
            System.out.println("2. Read categories");
            System.out.println("3. update category");
            System.out.println("4. remove category");
            System.out.println("5. stats");
            System.out.println("6. Manage income records");
            System.out.println("7. Manage spending records");
            System.out.println("8. go to category");
            System.out.println("9. go back a category");
            System.out.println("10. go back");

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
                    printCurrentCategory();
                    break;
                case 6:
                    recordUI.loop(categoryManager.currentCategory.income);
                    break;
                case 7:
                    recordUI.loop(categoryManager.currentCategory.spending);
                    break;
                case 8:
                    goToCategory();
                    break;
                case 9:
                    goBackACategory();
                    break;
                case 10:
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

        var indexWrapper = new Wrapper<Integer>();
        if (!Input.TryGetNextInt(indexWrapper)) return;

        if (indexWrapper.value < categoryManager.currentCategory.subCategories.size()){
            var category = categoryManager.currentCategory.subCategories.get(indexWrapper.value);
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
        var categories = categoryManager.read();
        System.out.print("Type category id to update:");

        var indexWrapper = new Wrapper<Integer>();
        if (!Input.TryGetNextInt(indexWrapper)) return;

        if (indexWrapper.value < categories.size()){
            var category = categories.get(indexWrapper.value);

            System.out.print("name: ");
            var name = scanner.nextLine();

            category.setName(name);
        }
    }

    private void remove(){
        var categories = categoryManager.read();
        System.out.print("Type category id to remove:");

        var indexWrapper = new Wrapper<Integer>();
        if (!Input.TryGetNextInt(indexWrapper)) return;

        if (indexWrapper.value < categories.size()){
            categories.remove(categories.get(indexWrapper.value));
        }
    }
}
