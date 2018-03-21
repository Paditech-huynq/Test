
package com.unza.wipro.main.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OrderData {

    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("member_id")
    @Expose
    private String memberId;
    @SerializedName("created_id")
    @Expose
    private Object createdId;
    @SerializedName("payment_date")
    @Expose
    private String paymentDate;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("billing_name")
    @Expose
    private String billingName;
    @SerializedName("billing_phone")
    @Expose
    private String billingPhone;
    @SerializedName("billing_address")
    @Expose
    private String billingAddress;
    @SerializedName("billing_date")
    @Expose
    private String billingDate;
    @SerializedName("billing_note")
    @Expose
    private String billingNote;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("product")
    @Expose
    private List<Product> product = new ArrayList<>();
    @SerializedName("money")
    @Expose
    private String money;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Object getCreatedId() {
        return createdId;
    }

    public void setCreatedId(Object createdId) {
        this.createdId = createdId;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getBillingName() {
        return billingName;
    }

    public void setBillingName(String billingName) {
        this.billingName = billingName;
    }

    public String getBillingPhone() {
        return billingPhone;
    }

    public void setBillingPhone(String billingPhone) {
        this.billingPhone = billingPhone;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(String billingDate) {
        this.billingDate = billingDate;
    }

    public String getBillingNote() {
        return billingNote;
    }

    public void setBillingNote(String billingNote) {
        this.billingNote = billingNote;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

}
