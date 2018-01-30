package com.unza.wipro.main.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserData {

    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("order")
    @Expose
    private String order;
    @SerializedName("customers")
    @Expose
    private String customers;
    @SerializedName("income")
    @Expose
    private String salesActual;
    @SerializedName("goal")
    @Expose
    private String salesExpect;
    @SerializedName("point")
    @Expose
    private String point;
    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("member_type")
    private String memberType;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("address")
    private String address;
    @SerializedName("is_manager")
    private String isManager;
    @SerializedName("member_group_id")
    private String memberGroupId;

    public String getSalesExpect() {
        return salesExpect;
    }

    public void setSalesExpect(String salesExpect) {
        this.salesExpect = salesExpect;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getCustomers() {
        return customers;
    }

    public void setCustomers(String customers) {
        this.customers = customers;
    }

    public String getSalesActual() {
        return salesActual;
    }

    public void setSalesActual(String salesActual) {
        this.salesActual = salesActual;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMemberType() {
        return memberType;
    }

    public int getMemberTypeInt() {
        try {
            return Integer.parseInt(memberType);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isManager() {
        return isManager.equalsIgnoreCase("1");
    }

    public void setIsManager(String isManager) {
        this.isManager = isManager;
    }

    public String getMemberGroupId() {
        return memberGroupId;
    }

    public void setMemberGroupId(String memberGroupId) {
        this.memberGroupId = memberGroupId;
    }

    public String getIsManager() {
        return isManager;
    }
}
