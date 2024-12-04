package com.viethcn.duanandroid.Models;

import java.io.Serializable;

public class MainModel implements Serializable {

    String name, price, img, description;
    int quantity;

    public MainModel(String name, String price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public MainModel() {
    }

    public MainModel(String name, String price, String img) {
        this.name = name;
        this.price = price;
        this.img = img;
    }

    public MainModel(String name, String price, String img, String description) {
        this.name = name;
        this.price = price;
        this.img = img;
        this.description = description;
    }

    public MainModel(String name, String price, String img, String description, int quantity) {
        this.name = name;
        this.price = price;
        this.img = img;
        this.description = description;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
