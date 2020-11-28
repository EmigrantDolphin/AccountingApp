package com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Repositories.Interfaces;

import com.vgtu.PRIf18_4.NormanBuiko.AccountingApp.Models.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IUserRepository {
    void addUser(User userToAdd) throws SQLException;
    ArrayList<User> getAllUsers() throws SQLException;
    void updateUser(User updatedUser) throws SQLException;
    void deleteUser(User userToDelete) throws SQLException;
}
