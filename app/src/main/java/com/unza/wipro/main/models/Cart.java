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
    private List<CartItem> mCartData = new ArrayList<>();

    public static Cart getInstance() {
        if (mInstance == null) mInstance = new Cart();
        return mInstance;
    }

    private Cart() {
        mCartData = new ArrayList<>();
    }

    public List<CartItem> getCartData() {
        return mCartData;
    }

    private int isExist(Product product) {
        int index = -1;
        for (int i = 0; i < mCartData.size(); i++) {
            if (mCartData.get(i).product.getId().equalsIgnoreCase(product.getId())) {
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
            mCartData.get(index).setAmount(mCartData.get(index).getAmount() + amount);
        } else {
            mCartData.add(new CartItem(product, amount));
        }
    }

    public void updateProduct(Product product, int amount) {
        int index = isExist(product);
        if (index > -1 && index < mCartData.size()) {
            mCartData.get(index).setAmount(amount);
        } else {
            mCartData.add(new CartItem(product, amount));
        }
    }

    public void removeProduct(Product product) {
        int index = isExist(product);
        if (index > -1 && index < mCartData.size()) mCartData.remove(index);
    }

    public void clear() {
        mCartData.clear();
    }

    public CartItem getCartItem(int position) {
        if (position >= 0 && position < mCartData.size()) return mCartData.get(position);
        return null;
    }

    public int getTotalProduct() {
        return mCartData.size();
    }

    public int getTotalAmount() {
        int total = 0;
        for (CartItem item : mCartData) {
            total += item.getAmount();
        }
        return total;
    }

    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : mCartData) {
            total += item.getTotalPrice();
        }
        return total;
    }
}
