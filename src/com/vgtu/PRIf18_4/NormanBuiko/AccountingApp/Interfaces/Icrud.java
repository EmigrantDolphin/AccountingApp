package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Interfaces;

import java.util.ArrayList;

public interface Icrud<T> {
    void add(T item);
    void remove(T item);
    void update(T item);
    ArrayList<T> read();
}
