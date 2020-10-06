package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models;

public class Wrapper<T> {
    public Wrapper(T val){
        value = val;
    }
    public Wrapper(){
    }
    public T value;
}
