package com.example.expensemanagement.Model;

public interface Callback<T> {
    void onSuccess(T result);
    void onError(String error);
}

