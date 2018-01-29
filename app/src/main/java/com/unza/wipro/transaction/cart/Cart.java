package com.unza.wipro.transaction.cart;

import android.util.Log;
import android.util.SparseArray;

import com.unza.wipro.main.models.Product;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/19/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public class Cart implements CartImpl, CartInfo {
    private static final String TAG = "CART";
    private SparseArray<Product> productSparseArray = new SparseArray<>();
    private Set<CartChangeListener> listenerSet = new LinkedHashSet<>();

    public void addListener(CartChangeListener listener) {
        listenerSet.add(listener);
    }

    public void removeListener(CartChangeListener listener) {
        listenerSet.remove(listener);
    }

    public void removeAllListener() {
        listenerSet.clear();
    }

    private void notifyDataChange() {
        for (CartChangeListener listener : listenerSet) {
            listener.onCartUpdate();
        }
    }

    @Override
    public SparseArray<Product> getProducts() {
        return productSparseArray.clone();
    }

    @Override
    public double getTotalPrice() {
        int totalPrice = 0;
        for (int i = 0; i < productSparseArray.size(); i++) {
            int key = productSparseArray.keyAt(i);
            Product product = productSparseArray.get(key);
            totalPrice += product.getTotalPrice();
        }
        return totalPrice;
    }

    @Override
    public int getAmount(int productId) {
        return productSparseArray.get(productId).getQuantity();
    }

    @Override
    public Product getItem(int position) {
        return productSparseArray.get(productSparseArray.keyAt(position));
    }

    @Override
    public int getItemCount() {
        return productSparseArray.size();
    }

    @Override
    public boolean contain(int productId) {
        return findItem(productId) != null;
    }

    @Override
    public Product findItem(int productId) {
        return productSparseArray.get(productId);
    }

    @Override
    public double getTotalPrice(int productId) {
        if (contain(productId)) {
            return findItem(productId).getTotalPrice();
        }
        return 0;
    }

    @Override
    public void clear() {
        productSparseArray.clear();
        Log.i(TAG, String.format("Clear all item in current Cart"));
        notifyDataChange();
    }

    @Override
    public boolean reduce(int productId) {
        try {
            if (productSparseArray.get(productId) != null) {
                productSparseArray.delete(productId);
                Log.i(TAG, String.format("Remove item from Cart: %s", productId));
                notifyDataChange();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, String.format("Can't remove item from Cart: %s", productId));
            return false;
        }

        return true;
    }

    @Override
    public boolean reduce(int productId, int inputAmount) {
        try {
            Product product = findItem(productId);
            if (product != null) {
                int currentQuantity = product.getQuantity();
                int newQuantity = Math.max(0, currentQuantity - inputAmount);
                if (newQuantity > 0) {
                    product.setQuantity(newQuantity);
                    Log.i(TAG, String.format("Reduce item %s by %s", product.getId(), newQuantity));
                    notifyDataChange();
                } else {
                    return reduce(productId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, String.format("Can't reduce item from Cart: %s", productId));
            return false;
        }
        return true;
    }

    @Override
    public boolean insert(Product product) {
        try {
            if (!contain(product.getId())) {
                product.setQuantity(1);
                productSparseArray.put(product.getId(), product);
                Log.i(TAG, String.format("Insert new Item to Cart: %s", product.getId()));
                notifyDataChange();
            } else {
                return insert(product.getId(), 1);
            }
        } catch (Exception e) {
            Log.i(TAG, String.format("Can't insert new item to Cart: %s", product.getId()));
            return false;
        }

        return true;
    }

    @Override
    public boolean insert(int productId, int inputAmount) {
        try {
            Product product = findItem(productId);
            if (product != null) {
                int currentAmount = product.getQuantity();
                productSparseArray.get(productId).setQuantity(inputAmount + currentAmount);
                Log.i(TAG, String.format("Add one item to Cart: %s", product.getId()));
                notifyDataChange();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, String.format("Can't add more item to Cart: %s", productId));
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Product product) {
        try {
            if (contain(product.getId())) {
                productSparseArray.put(product.getId(), product);
                Log.i(TAG, String.format("Update item: %s", product.getId()));
                notifyDataChange();
            } else {
                Log.i(TAG, String.format("Item not found: %s", product.getId()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, String.format("Can't update item to Cart: %s", product.getId()));
            return false;
        }
        return true;
    }

    @Override
    public void update(int productId, int value) {
        if (contain(productId)) {
            Log.i(TAG, String.format("Update quanlity of item: %s to %s", productId, value));
            findItem(productId).setQuantity(value);
            notifyDataChange();
        }
    }

    @Override
    public boolean insert(List<Product> products) {
        try {
            if(products == null)
            {
                return false;
            }
            for (int i = 0; i < products.size(); i++) {
                Product product = products.get(i);
                productSparseArray.put(product.getId(), product);
                notifyDataChange();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public interface CartChangeListener {
        void onCartUpdate();
    }
}
