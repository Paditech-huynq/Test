package com.unza.wipro.transaction.user;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserData {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("member_group_id")
    @Expose
    private String memberGroupId;
    @SerializedName("order")
    @Expose
    private String order;
    @SerializedName("customers")
    @Expose
    private String customers;
    @SerializedName("saleHave")
    @Expose
    private String saleHave;
    // chờ server cập nhập
    @SerializedName("")
    @Expose
    private String saleWant;
    @SerializedName("point")
    @Expose
    private String point;

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMemberGroupId() {
        return memberGroupId;
    }

    public void setMemberGroupId(String memberGroupId) {
        this.memberGroupId = memberGroupId;
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

    public String getSaleHave() {
        return saleHave;
    }

    public void setSaleHave(String saleHave) {
        this.saleHave = saleHave;
    }
}
