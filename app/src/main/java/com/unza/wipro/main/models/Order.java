package com.unza.wipro.main.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/22/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public class Order {

    @SerializedName("id")
    private int id;
    @SerializedName("money")
    private double money;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("note")
    private String note;
    @SerializedName("products")
    List<Product> products;
    @SerializedName("created_at")
    private long createdAt;
    @SerializedName("updated_at")
    private long updateddAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdateddAt() {
        return updateddAt;
    }

    public void setUpdateddAt(long updateddAt) {
        this.updateddAt = updateddAt;
    }
}
