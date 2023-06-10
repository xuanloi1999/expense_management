package com.example.expensemanagement.Model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

public class Account implements Serializable {
    private String _id;
    private String Email;
    private String Pasword;
    private String Username;
    private Double Money;

    public Account(String _id, String email, String pasword, String username, Double money) {
        this._id = _id;
        Email = email;
        Pasword = pasword;
        Username = username;
        Money = money;
    }

    public Account(String email, String pasword, String username, Double money) {
        Email = email;
        Pasword = pasword;
        Username = username;
        Money = money;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPasword() {
        return Pasword;
    }

    public void setPasword(String pasword) {
        Pasword = pasword;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public Double getMoney() {
        return Money;
    }

    public void setMoney(Double money) {
        Money = money;
    }

    @NonNull
    @Override
    public String toString() {
        return "Username: "+ getUsername() + "Password: " + getPasword();
    }
}
