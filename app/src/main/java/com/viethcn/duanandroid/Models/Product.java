package com.viethcn.duanandroid.Models;

public class Product {
    private int img;
    private String name;
    private String price;

    public Product(){}

    public Product(int img, String name, String price) {
        this.img = img;
        this.name = name;
        this.price = price;
    }

    public Product(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public int getImg() {
        return img;
    }
    public String getName() {
        return name;
    }
    public String getPrice() {
        return price;
    }

    public void setImg(int img) {
        this.img = img;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(String price) {
        this.price = price;
    }
}
