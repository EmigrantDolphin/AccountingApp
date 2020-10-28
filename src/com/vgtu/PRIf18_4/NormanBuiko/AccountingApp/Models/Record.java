package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Record implements Serializable {
    public String name;
    public Double amount;
    public LocalDateTime creationDate;
    public UserView userCreator;
}
