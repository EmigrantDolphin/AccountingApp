package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp;

import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Interfaces.ICategoryManager;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Interfaces.Icrud;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Interfaces.IUI;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models.Category;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models.UserView;

import java.util.ArrayList;

public class CategoryManager implements ICategoryManager, Icrud<Category> {
    public Category currentCategory;
    private IUI<CategoryManager> UI;

    public CategoryManager(){
        UI = new CategoryManagerUI();
        UserView uv = new UserView();
        uv.username = "Bobbie";
        uv.surname = "The uncle";
        uv.name = "Bob";
        currentCategory = new Category(uv, "Root");
    }

    @Override
    public void loop() {
        UI.loop(this);
    }

    @Override
    public void add(Category item) {
        currentCategory.subCategories.add(item);
    }

    @Override
    public void remove(Category item) {

    }

    @Override
    public void update(Category item) {

    }

    @Override
    public ArrayList<Category> read() {
        return currentCategory.subCategories;
    }
}
