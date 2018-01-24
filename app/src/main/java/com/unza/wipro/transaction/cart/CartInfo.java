package com.unza.wipro.transaction.cart;

import android.util.SparseArray;

import com.unza.wipro.main.models.Product;

public interface CartInfo {
    SparseArray<Product> getProducts();

    /**
     * Get total Price of item in current Cart
     *
     * @return
     */
    double getTotalPrice();

    /**
     * Get amount of item in current Cart
     *
     * @param productId
     * @return
     */
    int getAmount(int productId);

    /**
     * Get item of current Cart by position
     *
     * @param position
     * @return
     */
    Product getItem(int position);

    /**
     * Get count of item in current Cart
     *
     * @return
     */
    int getItemCount();

    boolean contain(int productId);

    Product findItem(int productId);

    double getTotalPrice(int productId);
}
