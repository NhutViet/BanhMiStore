package com.viethcn.duanandroid.Models;

import java.io.Serializable;
import java.util.List;

public class DonHang implements Serializable {
    private String id;
    private String address;
    private String owner;
    private String phone;
    private Double total;
    private String note;
    private List<MainModel> listProduct;
    private String status;
    private Double createAt;

    public DonHang(String id, String address, String owner, String phone, Double total, String note, List<MainModel> listProduct, String status, Double createAt) {
        this.id = id;
        this.address = address;
        this.owner = owner;
        this.phone = phone;
        this.total = total;
        this.note = note;
        this.listProduct = listProduct;
        this.status = status;
        this.createAt = createAt;
    }

    public DonHang(String id, String address, String owner, String phone, Double total, String note, List<MainModel> listProduct, String status) {
        this.id = id;
        this.address = address;
        this.owner = owner;
        this.phone = phone;
        this.total = total;
        this.note = note;
        this.listProduct = listProduct;
        this.status = status;
    }


    public DonHang(String address, String owner, String phone, Double total, String note, List<MainModel> listProduct) {
        this.address = address;
        this.owner = owner;
        this.phone = phone;
        this.total = total;
        this.note = note;
        this.listProduct = listProduct;
    }

    public DonHang(String address, String owner, String phone, Double total, List<MainModel> listProduct, String status) {
        this.address = address;
        this.owner = owner;
        this.phone = phone;
        this.total = total;
        this.listProduct = listProduct;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Double createAt) {
        this.createAt = createAt;
    }
}
