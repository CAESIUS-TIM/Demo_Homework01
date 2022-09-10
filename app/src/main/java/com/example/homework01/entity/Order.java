package com.example.homework01.entity;

public class Order {
    public int id;
    public String customName;
    public int age;
    public String country;

    public Order() {
    }

    public Order(int id, String customName, int age, String country) {
        this.id = id;
        this.customName = customName;
        this.age = age;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}