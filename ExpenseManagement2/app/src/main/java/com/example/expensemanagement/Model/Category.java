package com.example.expensemanagement.Model;

public class Category {
    private  String _id;
    private String Name;
    private String Icon;
    private TypeTransaction TypeTransaction;

    public Category(String _id, String name, String icon, com.example.expensemanagement.Model.TypeTransaction typeTransaction) {
        this._id = _id;
        Name = name;
        Icon = icon;
        TypeTransaction = typeTransaction;
    }

    public Category() {
    }

    public Category(String name, String icon, com.example.expensemanagement.Model.TypeTransaction typeTransaction) {
        Name = name;
        Icon = icon;
        TypeTransaction = typeTransaction;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }

    public com.example.expensemanagement.Model.TypeTransaction getTypeTransaction() {
        return TypeTransaction;
    }

    public void setTypeTransaction(com.example.expensemanagement.Model.TypeTransaction typeTransaction) {
        TypeTransaction = typeTransaction;
    }
}
