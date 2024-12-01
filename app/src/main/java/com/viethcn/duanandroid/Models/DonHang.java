package com.viethcn.duanandroid.Models;

import java.util.ArrayList;
import java.util.List;

public class DonHang {
    private String address;
    private String owner;
    private String phone;
    private Double total;
    private String note;
    private List<MainModel> listProduct;

    public DonHang(String address, String owner, String phone, Double total, String note, List<MainModel> listProduct) {
        this.address = address;
        this.owner = owner;
        this.phone = phone;
        this.total = total;
        this.note = note;
        this.listProduct = listProduct;
    }

    public DonHang(String address, String owner, String phone, Double total, List<MainModel> listProduct) {
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<MainModel> getListProduct() {
        return listProduct;
    }

    public void setListProduct(List<MainModel> listProduct) {
        this.listProduct = listProduct;
    }
}
