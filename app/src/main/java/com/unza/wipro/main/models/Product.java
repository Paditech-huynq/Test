package com.unza.wipro.main.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class Product {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("price")
    @Expose
    private double price;
    @SerializedName("cate_id")
    @Expose
    private String cateId;
    @SerializedName("category")
    @Expose
    private List<ProductCategory> categories;
    @SerializedName("stock")
    @Expose
    private List<ProductStock> stocks;
    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("sub_brand")
    @Expose
    private String subBrand;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("images")
    @Expose
    private List<ProductThumbnail> images = null;
    @SerializedName("thumbnail")
    @Expose
    private ProductThumbnail productThumbnail;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("quantity")
    @Expose
    private int quantity;
    @SerializedName("money")
    @Expose
    private double money;


    public int getId() {
        try {
            return Integer.parseInt(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public List<ProductCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<ProductCategory> categories) {
        this.categories = categories;
    }

    public List<ProductStock> getStocks() {
        return stocks;
    }

    public void setStocks(List<ProductStock> stocks) {
        this.stocks = stocks;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSubBrand() {
        return subBrand;
    }

    public void setSubBrand(String subBrand) {
        this.subBrand = subBrand;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<ProductThumbnail> getImages() {
        return images;
    }

    public void setImages(List<ProductThumbnail> images) {
        this.images = images;
    }

    public ProductThumbnail getProductThumbnail() {
        return productThumbnail;
    }

    public void setProductThumbnail(ProductThumbnail productThumbnail) {
        this.productThumbnail = productThumbnail;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getTotalPrice() {
        return price * quantity;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Product) {
            return Objects.equals(((Product) obj).id, this.id);
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}