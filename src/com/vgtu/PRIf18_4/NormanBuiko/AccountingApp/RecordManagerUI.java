package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp;

import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Interfaces.IUI;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models.Record;
import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models.Wrapper;

import java.util.ArrayList;
import java.util.Scanner;

public class RecordManagerUI implements IUI<ArrayList<Record>> {
    private final Scanner scanner = new Scanner(System.in);
    private ArrayList<Record> records;

    @Override
    public void loop(ArrayList<Record> parent) {
        records = parent;
        boolean exiting = false;

        while(!exiting){
            System.out.println("\n1. Add record");
            System.out.println("2. Read records");
            System.out.println("3. update record");
            System.out.println("4. remove record");
            System.out.println("5. go back");

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
                    exiting = true;
                    break;

            }
        }
    }

    private void add(){
        System.out.println("________Record creation________");
        System.out.print("name: ");
        var name = scanner.nextLine();

        System.out.print("amount: ");
        var amountWrapper = new Wrapper<Double>();
        if (!Input.TryGetNextDouble(amountWrapper)) return;

        var record = new Record();
        record.name = name;
        record.amount = amountWrapper.value;
        records.add(record);
    }

    private void read(){
        for (int i = 0; i < records.size(); i++){
            System.out.printf("%d. {\n", i);
            System.out.printf("   name: %s\n", records.get(i).name);
            System.out.printf("   amount: %.2f\n", records.get(i).amount);
            System.out.println("}");
        }
    }

    private void update(){
        System.out.print("Type record ID to update");

        var indexWrapper = new Wrapper<Integer>();
        if (!Input.TryGetNextInt(indexWrapper)) return;

        if (indexWrapper.value < records.size()){
            System.out.print("name: ");
            var name = scanner.nextLine();

            System.out.print("amount: ");
            var amountWrapper = new Wrapper<Double>();
            if (!Input.TryGetNextDouble(amountWrapper)) return;

            var record = records.get(indexWrapper.value);
            record.name = name;
            record.amount = amountWrapper.value;
        }
    }

    private void remove(){
        System.out.print("Type record ID to remove");

        var indexWrapper = new Wrapper<Integer>();
        if (!Input.TryGetNextInt(indexWrapper)) return;

        if (indexWrapper.value < records.size()){
            records.remove(records.get(indexWrapper.value));
        }
    }
}
