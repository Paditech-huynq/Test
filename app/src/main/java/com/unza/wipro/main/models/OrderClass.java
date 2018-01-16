package com.unza.wipro.main.models;

import java.util.Date;


public class OrderClass {
    private int img;
    private String title;
    private Date date;
    private int price;
    private int numberOrder;

    public OrderClass(int img, String title, Date date, int price, int numberOrder) {
        this.img = img;
        this.title = title;
        this.date = date;
        this.price = price;
        this.numberOrder = numberOrder;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNumberOrder() {
        return numberOrder;
    }

    public void setNumberOrder(int numberOrder) {
        this.numberOrder = numberOrder;
    }
}
