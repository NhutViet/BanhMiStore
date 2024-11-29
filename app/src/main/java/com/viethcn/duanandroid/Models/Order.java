package com.viethcn.duanandroid.Models;

public class Order {
    private String name;
    private String date;
    private int price;

    public Order(String name, String date, int price) {
        this.name = name;
        this.date = date;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public int getPrice() {
        return price;
    }
}
