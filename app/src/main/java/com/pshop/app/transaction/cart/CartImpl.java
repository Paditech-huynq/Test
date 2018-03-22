package com.pshop.app.transaction.cart;

import com.pshop.app.main.models.Product;

import java.util.List;

public interface CartImpl {
    /**
     * Clear all item in current Cart
     */
    void clear();

    /**
     * Delete item by Id
     * <p>
     * If item exist, it will be removed from current Cart
     *
     * @param productId
     */
    boolean remove(int productId);

    /**
     * Delete item by id and amount
     *
     * @param productId
     * @param inputAmount
     * @return true if success
     */
    boolean reduce(int productId, int inputAmount);

    /**
     * insert one item item to Cart
     * <p>
     * If item is not exist in current Cart, it will be added with quantity = 1
     *
     * @param product
     * @return true if success
     */
    boolean insert(Product product);

    /**
     * insert product with amount
     *
     * @param productId
     * @param inputAmount
     * @return true if success
     */
    boolean insert(int productId, int inputAmount);

    /**
     * Replace exist product in current cart
     *
     * @param product updated item
     * @return true if success
     */
    boolean update(Product product);

    /**
     * update amount by product Id
     *
     * @param productId
     * @param value
     */
    void update(int productId, int value);

    boolean insert(List<Product> products);
}