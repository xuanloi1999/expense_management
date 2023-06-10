package com.example.expensemanagement.Model;

import java.util.Date;

public class Transaction {
    private String _id;
    private Double Expense;
    private String CategoryID;
    private Date TransactionAt;
    private String Note;
    private TypeTransaction TypeTransaction;
    private String AccountID;
    private String GroupID;

    public Transaction(String _id, Double expense, String category, Date transactionAt, String note, com.example.expensemanagement.Model.TypeTransaction typeTransaction, String accountID) {
        this._id = _id;
        Expense = expense;
        CategoryID = category;
        TransactionAt = transactionAt;
        Note = note;
        TypeTransaction = typeTransaction;
        AccountID = accountID;
    }

    public Transaction(String _id, Double expense, String categoryID, String note, com.example.expensemanagement.Model.TypeTransaction typeTransaction, String accountID) {
        this._id = _id;
        Expense = expense;
        CategoryID = categoryID;
        Note = note;
        TypeTransaction = typeTransaction;
        AccountID = accountID;
    }

    public Transaction(Double expense, String category, Date transactionAt, String note, com.example.expensemanagement.Model.TypeTransaction typeTransaction, String accountID) {
        Expense = expense;
        CategoryID = category;
        TransactionAt = transactionAt;
        Note = note;
        TypeTransaction = typeTransaction;
        AccountID = accountID;
    }

    public Transaction(Double expense, String category, Date transactionAt, String note, com.example.expensemanagement.Model.TypeTransaction typeTransaction, String accountID, String groupID) {
        Expense = expense;
        CategoryID = category;
        TransactionAt = transactionAt;
        Note = note;
        TypeTransaction = typeTransaction;
        AccountID = accountID;
        GroupID = groupID;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Double getExpense() {
        return Expense;
    }

    public void setExpense(Double expense) {
        Expense = expense;
    }

    public String getCategory() {
        return CategoryID;
    }

    public void setCategory(String category) {
        CategoryID = category;
    }

    public Date getTransactionAt() {
        return TransactionAt;
    }

    public void setTransactionAt(Date transactionAt) {
        TransactionAt = transactionAt;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public com.example.expensemanagement.Model.TypeTransaction getTypeTransaction() {
        return TypeTransaction;
    }

    public void setTypeTransaction(com.example.expensemanagement.Model.TypeTransaction typeTransaction) {
        TypeTransaction = typeTransaction;
    }

    public String getAccountID() {
        return AccountID;
    }

    public void setAccountID(String accountID) {
        AccountID = accountID;
    }

    public String getGroupID() {
        return GroupID;
    }

    public void setGroupID(String groupID) {
        GroupID = groupID;
    }
}
