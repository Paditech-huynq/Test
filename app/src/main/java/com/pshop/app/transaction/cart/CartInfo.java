package com.pshop.app.transaction.cart;

import android.util.SparseArray;

import com.pshop.app.main.models.Product;

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
     * Get total quanlity of item in current Cart
     *
     * @return
     */
    int getTotalQuantity();

    /**
     * Get count of product
     *
     * @return item count
     */
    int getItemCount();

    boolean contain(int productId);

    Product findItem(int productId);

    double getTotalPrice(int productId);
}
