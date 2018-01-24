package com.unza.wipro.main.models;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User_ {
    private Date dateStart;
    private long saleWant;
    private long saleHave;
    private int numberSales;
    private int numberPoint;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private String avar;
    private int typeUse;
    private int numberCustom;
    private List<User_> members = new ArrayList<>();

    public List<User_> getMembers() {
        return members;
    }

    public void setMembers(List<User_> members) {
        this.members = members;
    }

    public int getNumberCustom() {
        return numberCustom;
    }

    public void setNumberCustom(int numberCustom) {
        this.numberCustom = numberCustom;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public long getSaleWant() {
        return saleWant;
    }

    public void setSaleWant(long saleWant) {
        this.saleWant = saleWant;
    }

    public long getSaleHave() {
        return saleHave;
    }

    public void setSaleHave(long saleHave) {
        this.saleHave = saleHave;
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

    public int getTypeUse() {
        return typeUse;
    }

    public void setTypeUse(int typeUse) {
        this.typeUse = typeUse;
    }
}
