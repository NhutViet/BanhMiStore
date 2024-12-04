package com.viethcn.duanandroid.Models;

public class User {
    public String name;
    public String password;
    public String rule;
    private String id;

    public User() {
    }

    public User(String id, String name, String password, String rule) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.rule = rule;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
