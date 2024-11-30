package com.viethcn.duanandroid.Models;

import java.util.ArrayList;

public class DonHang {
    private String address;
    private String  owner;
    private String phone;
    private String total;
    private ArrayList<MainModel>  listProduct;

    public DonHang(String address, String owner, String phone, String total, ArrayList<MainModel> listProduct) {
        this.address = address;
        this.owner = owner;
        this.phone = phone;
        this.total = total;
        this.listProduct = listProduct;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ArrayList<MainModel> getListProduct() {
        return listProduct;
    }

    public void setListProduct(ArrayList<MainModel> listProduct) {
        this.listProduct = listProduct;
    }
}
