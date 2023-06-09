package com.example.expensemanagement.Model;

import androidx.annotation.NonNull;

import java.util.Date;

public class Account {
    private String Email;
    private String Pasword;
    private String Username;
    private Double Money;

    public Account(String email, String pasword, String username, Double money) {
        Email = email;
        Pasword = pasword;
        Username = username;
        Money = money;
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
