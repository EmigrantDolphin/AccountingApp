package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp;

import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Interfaces.ISaveable;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Interfaces.Icrud;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models.Category;

import java.util.ArrayList;

public class CategoryManager implements Icrud<Category>, ISaveable {
    public Category currentCategory;

    private final FileDriver<Category> fileDriver = new FileDriver<>();
    private final String categoryPath = "./categoryStorage.txt";

    public CategoryManager(){
        loadCategory();
    }

    private void loadCategory(){
        Category loadedCategory = fileDriver.importFile(categoryPath);
        if (loadedCategory == null){
            var user = UserManager.getLoggedInUser();
            currentCategory = new Category(user, "Root");
        }else{
            currentCategory = loadedCategory;
        }
    }

    @Override
    public void add(Category item) {
        currentCategory.subCategories.add(item);
    }

    @Override
    public void remove(Category item) {
        var index = currentCategory.subCategories.indexOf(item);
        if (index < 0){
            System.out.println("Object not found. The object reference is not in a list");
        }else{
            currentCategory.subCategories.remove(index);
        }
    }

    @Override
    public void update(Category item) {
        var index = currentCategory.subCategories.indexOf(item);
        if (index < 0){
            System.out.println("Object not found. The object reference is not in a list");
        }else{
            currentCategory.subCategories.set(index, item);
        }
    }

    @Override
    public ArrayList<Category> read() {
        return currentCategory.subCategories;
    }

    @Override
    public void save(){
        fileDriver.exportFile(currentCategory, categoryPath);
    }
}
