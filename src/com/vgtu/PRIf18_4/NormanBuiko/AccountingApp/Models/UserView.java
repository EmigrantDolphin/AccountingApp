package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models;

import java.io.Serializable;

public class UserView implements Serializable {
    private int id;
    public String username;
    public String name;
    public String surname;
    public boolean isSystemAdmin;

    public void setId(int id) throws Exception{
        if (this.id == 0){
            this.id = id;
            return;
        }

        throw new Exception("Can't overwrite ID that is already assigned");
    }

    public int getId() {return id;}
}