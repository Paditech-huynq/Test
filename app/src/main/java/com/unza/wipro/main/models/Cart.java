package com.unza.wipro.main.models;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/19/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public class Cart {

    private static Cart mInstance;
    private List<Product> mCartData = new ArrayList<>();

    public static Cart getInstance() {
        if (mInstance == null) mInstance = new Cart();
        return mInstance;
    }

    private Cart() {
        mCartData = new ArrayList<>();
    }

    public List<Product> getCartData() {
        return mCartData;
    }

    private int isExist(Product product) {
        int index = -1;
        for (int i = 0; i < mCartData.size(); i++) {
            if (mCartData.get(i).getId().equalsIgnoreCase(product.getId())) {
                index = i;
                break;
            }
        }
        return index;
    }

    public void addProduct(Product product) {
        addProduct(product, 1);
    }

    public void addProduct(Product product, int amount) {
        int index = isExist(product);
        if (index > -1 && index < mCartData.size()) {
            int quantity = mCartData.get(index).getQuantity() + amount;
            mCartData.get(index).setQuantity(quantity);
        } else {
            product.setQuantity(amount);
            mCartData.add(product);
        }
    }

    public void updateProduct(Product product, int amount) {
        int index = isExist(product);
        if (index > -1 && index < mCartData.size()) {
            product.setQuantity(amount);
        } else {
            product.setQuantity(amount);
            mCartData.add(product);
        }
    }

    public void removeProduct(Product product) {
        int index = isExist(product);
        if (index > -1 && index < mCartData.size()) mCartData.remove(index);
    }

    public void clear() {
        mCartData.clear();
    }

    public Product getCartItem(int position) {
        if (position >= 0 && position < mCartData.size()) return mCartData.get(position);
        return null;
    }

    public int getTotalProduct() {
        return mCartData.size();
    }

    public int getTotalAmount() {
        int total = 0;
        for (Product item : mCartData) {
            total += Integer.valueOf(item.getQuantity());
        }
        return total;
    }

    public double getTotalPrice() {
        double total = 0;
        for (Product item : mCartData) {
            total += item.getTotalPrice();
        }
        return total;
    }
}
