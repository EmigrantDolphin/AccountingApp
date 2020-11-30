package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp;

import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Interfaces.Icrud;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models.Category;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Repositories.CategoryRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryManager implements Icrud<Category> {
    public Category currentCategory;

    private final CategoryRepository categoryRepository = new CategoryRepository();

    public CategoryManager(){
        loadCategory();
    }

    private void loadCategory(){
        Category loadedCategory = loadCategoryFromDB();
        if (loadedCategory == null){
            var user = UserManager.getLoggedInUser();
            currentCategory = new Category(user, "Root");
        }else{
            currentCategory = loadedCategory;
        }
    }

    private Category loadCategoryFromDB(){
        try{
            return categoryRepository.getAll().get(0);
        }catch (SQLException e){
            GlobalMessage.show(e.getMessage());
            return null;
        }
    }

    @Override
    public void add(Category item) {
        try{
            categoryRepository.add(item);
        }catch (SQLException e){
            GlobalMessage.show(e.getMessage());
        }
        currentCategory.subCategories.add(item);
    }

    @Override
    public void remove(Category item) {
        try{
            categoryRepository.delete(item);
        }catch (SQLException e){
            GlobalMessage.show(e.getMessage());
            return;
        }

        var index = currentCategory.subCategories.indexOf(item);
        if (index < 0){
            System.out.println("Object not found. The object reference is not in a list");
        }else{
            currentCategory.subCategories.remove(index);
        }
    }

    @Override
    public void update(Category item) {
        try{
            categoryRepository.update(item);
        }catch (SQLException e){
            GlobalMessage.show(e.getMessage());
            return;
        }

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
}
