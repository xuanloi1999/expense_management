package com.example.expensemanagement.Model;

public class ItemMenu {
    private String itemName;
    private int icon;

    public ItemMenu(String itemName, int icon) {
        this.itemName = itemName;
        this.icon = icon;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
