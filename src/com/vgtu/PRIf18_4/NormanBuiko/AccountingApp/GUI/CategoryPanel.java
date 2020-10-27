package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.GUI;

import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.CategoryManager;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models.Category;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models.Record;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.UserManager;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.temporal.JulianFields;

public class CategoryPanel extends JPanel {
    private CategoryManager categoryManager = new CategoryManager();

    private JPanel footerContainer;
    private JPanel categoryEntryContainer;
    private JPanel incomeEntryContainer;
    private JPanel spendingEntryContainer;

    public CategoryPanel(){

        setupCategoryPanel();
    }

    private void setupCategoryPanel(){

        this.setLayout(new GridBagLayout());

        this.setBorder(new LineBorder(Color.black));


        if (categoryEntryContainer != null)
            this.remove(categoryEntryContainer);
        if (incomeEntryContainer != null)
            this.remove(incomeEntryContainer);
        if (spendingEntryContainer != null)
            this.remove(spendingEntryContainer);
        if (footerContainer != null)
            this.remove(footerContainer);

        setupCategoryContainer();
        setupFooterContainer();
        setupIncomeContainer();
        setupSpendingContainer();


        // Category entry container. First row whole row
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(categoryEntryContainer, gbc);

        // incomeEntryContainer. Second row first column
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(incomeEntryContainer, gbc);


        // spendingEntryContainer. Second row second column
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(spendingEntryContainer, gbc);


        // footerContainer. Third row whole row
        gbc.gridwidth = 2;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.weighty = 0.1;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        this.add(footerContainer, gbc);

        revalidate();
    }

    private void setupCategoryContainer() {
        categoryEntryContainer = new JPanel();
        categoryEntryContainer.setLayout(new BoxLayout(categoryEntryContainer, BoxLayout.PAGE_AXIS));
        categoryEntryContainer.setBorder(new LineBorder(Color.BLACK));

        var labelFlow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labelFlow.add(new JLabel("Category: " + categoryManager.currentCategory.getName()));
        categoryEntryContainer.add(labelFlow);

        for (var category : categoryManager.currentCategory.subCategories){
            categoryEntryContainer.add(createCategoryEntry(category));
        }
    }

    private void setupIncomeContainer() {
        incomeEntryContainer = new JPanel();
        incomeEntryContainer.setLayout(new BoxLayout(incomeEntryContainer, BoxLayout.PAGE_AXIS));
        incomeEntryContainer.setBorder(new LineBorder(Color.black));

        var labelFlow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labelFlow.add(new JLabel("Income"));
        incomeEntryContainer.add(labelFlow);
    }

    private void setupSpendingContainer() {
        spendingEntryContainer = new JPanel();
        spendingEntryContainer.setLayout(new BoxLayout(spendingEntryContainer, BoxLayout.PAGE_AXIS));
        spendingEntryContainer.setBorder(new LineBorder(Color.black));

        var labelFlow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labelFlow.add(new JLabel("Spending"));
        spendingEntryContainer.add(labelFlow);
    }


    private void setupFooterContainer() {
        footerContainer = new JPanel();
        footerContainer.setLayout(new FlowLayout());
        footerContainer.setBorder(new LineBorder(Color.black));

        var addCategoryButton = new JButton("Add Category");
        addCategoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var catNameField = new JTextField();

                var fields = new Object[]{
                        "Category name", catNameField,
                };

                int result = JOptionPane.showConfirmDialog(null, fields, "title", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION){
                    var category = new Category(UserManager.getLoggedInUser(), catNameField.getText(), categoryManager.currentCategory);

                    categoryManager.add(category);
                    var entry = createCategoryEntry(category);
                    categoryEntryContainer.add(entry);
                    revalidate();
                }
            }
        });
        footerContainer.add(addCategoryButton);

        var addIncomeButton = new JButton("Add Income");
        addIncomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var nameField = new JTextField();
                var amountField = new JTextField();

                var fields = new Object[]{
                        "Income name:", nameField,
                        "Amount earned", amountField
                };

                var result = JOptionPane.showConfirmDialog(null, fields, "title", JOptionPane.OK_CANCEL_OPTION );
                if (result == JOptionPane.OK_OPTION){
                    var record = new Record();
                    record.name = nameField.getText();
                    record.amount = Double.parseDouble(amountField.getText());
                    //todo: check if double legit
                    incomeEntryContainer.add(createIncomeEntry(record));
                    setupCategoryPanel();
                }
            }
        });
        footerContainer.add(addIncomeButton);

        if (categoryManager.currentCategory.parentCategory != null){
            var backButton = new JButton("Back");
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    categoryManager.currentCategory = categoryManager.currentCategory.parentCategory;
                    setupCategoryPanel();
                }
            });
            footerContainer.add(backButton);
        }
    }

    private JPanel createIncomeEntry(Record record){
        var jPanel = new JPanel();

        var label = new JLabel(String.format("%s, %.2d$", record.name, record.amount));
        jPanel.add(label);

        var viewButton = new JButton("View");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var nameField = new JTextField();
                var amountField = new JTextField();
                var shouldremove = new JRadioButton();

                var fields = new Object[]{
                        "Income name:", nameField,
                        "Amount earned:", amountField,
                        "Remove:", shouldremove
                };

                var result = JOptionPane.showConfirmDialog(null, fields, "Income view", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION){
                    if (shouldremove.isSelected()){
                        categoryManager.currentCategory.income.remove(record);
                        incomeEntryContainer.remove(jPanel);
                    }else{
                        record.name = nameField.getText();
                        record.amount = Double.parseDouble(amountField.getText());
                        //todo: check if double legit
                        setupCategoryPanel();
                    }
                }
            }
        });
        jPanel.add(viewButton);

        return jPanel;
    }

    private JPanel createCategoryEntry(Category category){
        var jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        var categoryGoToButton = new JButton(category.getName());
        categoryGoToButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                categoryManager.currentCategory = category;
                setupCategoryPanel();
            }
        });
        jPanel.add(categoryGoToButton);

        var categoryViewButton = new JButton("View");
        categoryViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var catNameField = new JTextField();
                var shouldRemoveRadio = new JRadioButton();

                var fields = new Object[]{
                        "Category name", catNameField,
                        "Remove category", shouldRemoveRadio
                };

                int result = JOptionPane.showConfirmDialog(null, fields, "title", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION){
                    if (shouldRemoveRadio.isSelected()){
                        categoryManager.remove(category);
                        categoryEntryContainer.remove(jPanel);
                    }else{
                        category.setName(catNameField.getText());
                        categoryGoToButton.setText(catNameField.getText());
                    }
                }
            }
        });
        jPanel.add(categoryViewButton);

        return jPanel;
    }

}