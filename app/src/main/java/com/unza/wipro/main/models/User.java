package com.unza.wipro.main.models;


public class User {
    private int numberSales;
    private int numberPoint;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private String avar;
    private int typeUse;

    public User(int typeUse) {
        this.typeUse = typeUse;
    }

    public User(int numberSales, int numberPoint, String name, String phoneNumber, String email, String address, String avar, int typeUse) {
        this.numberSales = numberSales;
        this.numberPoint = numberPoint;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.avar = avar;
        this.typeUse = typeUse;
    }

    public int getNumberSales() {
        return numberSales;
    }

    public void setNumberSales(int numberSales) {
        this.numberSales = numberSales;
    }

    public int getNumberPoint() {
        return numberPoint;
    }

    public void setNumberPoint(int numberPoint) {
        this.numberPoint = numberPoint;
    }

    public int getTypeUse() {
        return typeUse;
    }

    public void setTypeUse(int typeUse) {
        this.typeUse = typeUse;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvar() {
        return avar;
    }

    public void setAvar(String avar) {
        this.avar = avar;
    }
}
