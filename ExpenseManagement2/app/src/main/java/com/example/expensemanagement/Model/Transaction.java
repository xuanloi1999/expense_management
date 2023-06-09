package com.example.expensemanagement.Model;

import java.util.Date;

public class Transaction {
    private Double Expense;
    private String Category;
    private Date TransactionAt;
    private String Note;
    private TypeTransaction TypeTransaction;
    private String AccountID;
    private String GroupID;

    public Transaction(Double expense, String category, Date transactionAt, String note, com.example.expensemanagement.Model.TypeTransaction typeTransaction, String accountID, String groupID) {
        Expense = expense;
        Category = category;
        TransactionAt = transactionAt;
        Note = note;
        TypeTransaction = typeTransaction;
        AccountID = accountID;
        GroupID = groupID;
    }

    public Double getExpense() {
        return Expense;
    }

    public void setExpense(Double expense) {
        Expense = expense;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
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
