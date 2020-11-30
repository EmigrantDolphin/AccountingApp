package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Record extends BaseModel implements Serializable {
    private int userCreatorId;
    private int categoryId;
    public String name;
    public Double amount;
    public LocalDateTime creationDate;
    public UserView userCreator;
    public boolean isSpending;

    public void setUserCreatorId(int userCreatorId) throws Exception{
        if (this.userCreatorId == 0){
            this.userCreatorId = userCreatorId;
            return;
        }

        throw new Exception("Can't overwrite ID that is already assigned");
    }
    public int getUserCreatorId(){return userCreatorId;}

    public void setCategoryId(int categoryId) throws Exception{
        if (this.categoryId == 0){
            this.categoryId = categoryId;
            return;
        }

        throw new Exception("Can't overwrite ID that is already assigned");
    }

    public int getCategoryId() {return categoryId;}
}
