package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp;

import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models.Wrapper;

import java.util.Scanner;

public class Input {
    private static Scanner scanner = new Scanner(System.in);

    public static boolean TryGetNextDouble(Wrapper<Double> wrapper){
        try{
            wrapper.value = scanner.nextDouble();
            if (scanner.hasNextLine()){
                scanner.nextLine();
            }
            return true;
        }catch (Exception e){
            System.out.println("Input not a number... Use '.' (dot) as decimal separator");
            if (scanner.hasNextLine()){
                scanner.nextLine();
            }
            return false;
        }
    }

    public static boolean TryGetNextInt(Wrapper<Integer> wrapper){
        try{
            wrapper.value = scanner.nextInt();
            if (scanner.hasNextLine()){
                scanner.nextLine();
            }
            return true;
        }catch (Exception e){
            System.out.println("Input not a number...");
            if (scanner.hasNextLine()){
                scanner.nextLine();
            }
            return false;
        }
    }
}
