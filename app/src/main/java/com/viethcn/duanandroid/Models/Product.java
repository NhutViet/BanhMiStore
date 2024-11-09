package com.viethcn.duanandroid.Models;

public class Product {
    private int img;
    private String name;
    private int price;

    public Product(int img, String name, int price) {
        this.img = img;
        this.name = name;
        this.price = price;
    }

    public int getImg() {
        return img;
    }
    public String getName() {
        return name;
    }
    public int getPrice() {
        return price;
    }

    public void setImg(int img) {
        this.img = img;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(int price) {
        this.price = price;
    }
}
