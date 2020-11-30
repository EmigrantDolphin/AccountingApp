package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.GUI;

import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.CategoryManager;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.GlobalMessage;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models.Category;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models.Record;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Repositories.RecordRepository;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.UserManager;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class CategoryPanel extends JPanel {
    private CategoryManager categoryManager = new CategoryManager();
    private RecordRepository recordRepository = new RecordRepository();

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
        setupIncomeContainer();
        setupSpendingContainer();
        setupFooterContainer();


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

        for (var income : categoryManager.currentCategory.income){
            incomeEntryContainer.add(createIncomeEntry(income));
        }
    }

    private void setupSpendingContainer() {
        spendingEntryContainer = new JPanel();
        spendingEntryContainer.setLayout(new BoxLayout(spendingEntryContainer, BoxLayout.PAGE_AXIS));
        spendingEntryContainer.setBorder(new LineBorder(Color.black));

        var labelFlow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labelFlow.add(new JLabel("Spending"));
        spendingEntryContainer.add(labelFlow);

        for (var spending : categoryManager.currentCategory.spending){
            spendingEntryContainer.add(createSpendingEntry(spending));
        }
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
                    double amount;

                    try{
                        amount = Double.parseDouble(amountField.getText());
                    }catch (NumberFormatException ex){
                        JOptionPane.showMessageDialog(null, "'Amount spent' value has to be a number optionally separated by a '.' (dot)");
                        return;
                    }

                    var record = new Record();
                    record.name = nameField.getText();
                    record.amount = amount;
                    record.creationDate = LocalDateTime.now();
                    record.userCreator = UserManager.getLoggedInUser();
                    record.isSpending = false;

                    try{
                        record.setCategoryId(categoryManager.currentCategory.getId());
                    }catch (Exception ex){
                        GlobalMessage.show(ex.getMessage());
                        return;
                    }

                    try{
                        recordRepository.add(record);
                    }catch (SQLException ex){
                        GlobalMessage.show(ex.getMessage());
                        return;
                    }

                    categoryManager.currentCategory.income.add(record);
                    incomeEntryContainer.add(createIncomeEntry(record));
                    revalidate();
                }
            }
        });
        footerContainer.add(addIncomeButton);


        var addSpendingButton = new JButton("Add Spending");
        addSpendingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var nameField = new JTextField();
                var amountField = new JTextField();

                var fields = new Object[]{
                        "Spending name:", nameField,
                        "Amount spent", amountField
                };

                var result = JOptionPane.showConfirmDialog(null, fields, "title", JOptionPane.OK_CANCEL_OPTION );
                if (result == JOptionPane.OK_OPTION){
                    double amount;

                    try{
                        amount = Double.parseDouble(amountField.getText());
                    }catch (NumberFormatException ex){
                        JOptionPane.showMessageDialog(null, "'Amount spent' value has to be a number optionally separated by a '.' (dot)");
                        return;
                    }

                    var record = new Record();
                    record.name = nameField.getText();
                    record.amount = amount;
                    record.creationDate = LocalDateTime.now();
                    record.userCreator = UserManager.getLoggedInUser();
                    record.isSpending = true;

                    try{
                        record.setCategoryId(categoryManager.currentCategory.getId());
                    }catch (Exception ex){
                        GlobalMessage.show(ex.getMessage());
                        return;
                    }

                    try{
                        recordRepository.add(record);
                    }catch (SQLException ex){
                        GlobalMessage.show(ex.getMessage());
                        return;
                    }

                    categoryManager.currentCategory.spending.add(record);
                    spendingEntryContainer.add(createSpendingEntry(record));
                    revalidate();
                }
            }
        });
        footerContainer.add(addSpendingButton);

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

    private JPanel createSpendingEntry(Record record){
        var jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        var label = new JLabel(String.format("%s, %.2f$", record.name, record.amount));
        jPanel.add(label);

        var viewButton = new JButton("View");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var userCreatorNameField = new JTextField(record.userCreator.name + " " + record.userCreator.surname);
                userCreatorNameField.setEnabled(false);
                userCreatorNameField.setDisabledTextColor(Color.black);
                var creationDateField = new JTextField(record.creationDate.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)));
                creationDateField.setEnabled(false);
                creationDateField.setDisabledTextColor(Color.black);

                var nameField = new JTextField(record.name);
                var amountField = new JTextField(record.amount.toString());
                var shouldRemove = new JRadioButton();

                var fields = new Object[]{
                        "record created by:", userCreatorNameField,
                        "record created at:", creationDateField,
                        "spending name:", nameField,
                        "Amount spent:", amountField,
                        "Remove:", shouldRemove
                };

                var result = JOptionPane.showConfirmDialog(null, fields, "Spending view", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION){
                    if (shouldRemove.isSelected()){
                        try{
                            recordRepository.delete(record);
                        }catch (SQLException ex){
                            GlobalMessage.show(ex.getMessage());
                            return;
                        }
                        categoryManager.currentCategory.spending.remove(record);
                        spendingEntryContainer.remove(jPanel);
                    }else{
                        try{
                            record.amount = Double.parseDouble(amountField.getText());
                            record.name = nameField.getText();
                            try{
                                recordRepository.update(record);
                            }catch (SQLException ex){
                                GlobalMessage.show(ex.getMessage());
                                return;
                            }
                        }catch (NumberFormatException ex){
                            JOptionPane.showMessageDialog(null, "'Amount spent' value has to be a number optionally separated by a '.' (dot)");
                            return;
                        }
                        setupCategoryPanel();
                    }
                }
            }
        });
        jPanel.add(viewButton);

        return jPanel;
    }

    private JPanel createIncomeEntry(Record record){
        var jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        var label = new JLabel(String.format("%s, %.2f$", record.name, record.amount));
        jPanel.add(label);

        var viewButton = new JButton("View");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var userCreatorNameField = new JTextField(record.userCreator.name + " " + record.userCreator.surname);
                userCreatorNameField.setEnabled(false);
                userCreatorNameField.setDisabledTextColor(Color.black);
                var creationDateField = new JTextField(record.creationDate.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)));
                creationDateField.setEnabled(false);
                creationDateField.setDisabledTextColor(Color.black);

                var nameField = new JTextField(record.name);
                var amountField = new JTextField(record.amount.toString());
                var shouldremove = new JRadioButton();

                var fields = new Object[]{
                        "record created by:", userCreatorNameField,
                        "record created at:", creationDateField,
                        "Income name:", nameField,
                        "Amount earned:", amountField,
                        "Remove:", shouldremove
                };

                var result = JOptionPane.showConfirmDialog(null, fields, "Income view", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION){
                    if (shouldremove.isSelected()){
                        try{
                            recordRepository.delete(record);
                        }catch (SQLException ex){
                            GlobalMessage.show(ex.getMessage());
                            return;
                        }
                        categoryManager.currentCategory.income.remove(record);
                        incomeEntryContainer.remove(jPanel);
                    }else{
                        try{
                            record.amount = Double.parseDouble(amountField.getText());
                            record.name = nameField.getText();
                            try{
                                recordRepository.update(record);
                            }catch (SQLException ex){
                                GlobalMessage.show(ex.getMessage());
                                return;
                            }
                        }catch (NumberFormatException ex){
                            JOptionPane.showMessageDialog(null, "'Amount earned' value has to be a number optionally separated by a '.' (dot)");
                            return;
                        }
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
                var catNameField = new JTextField(category.getName());
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
                        categoryManager.update(category);
                    }
                }
            }
        });
        jPanel.add(categoryViewButton);

        return jPanel;
    }

}