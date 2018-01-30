package com.unza.wipro.transaction.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.paditech.core.helper.StringUtil;
import com.unza.wipro.main.models.UserData;
import com.unza.wipro.main.models.UserInfo;

public abstract class User implements UserInfo {
    static final int TYPE_CUSTOMER = 0;
    static final int TYPE_PROMOTER = 1;
    static final int TYPE_PROMOTER_LEADER = 2;

    public enum Type {
        CUSTOMER("customer"), PROMOTER("promoter"), PROMOTER_LEADER("promoter_leader");

        public String getValue() {
            return value;
        }

        private String value;

        Type(String value) {
            this.value = value;
        }
    }


    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("address")
    private String address;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("order")
    private String numberOrders;

    public String getNumberOrders() {
        return numberOrders;
    }

    public void setNumberOrders(String numberCustomers) {
        if(StringUtil.isEmpty(numberCustomers)){
            this.numberOrders = "0";
            return;
        }
        this.numberOrders = numberCustomers;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if(StringUtil.isEmpty(phone)){
            this.phone = "";
            return;
        }
        this.phone = phone;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        if(StringUtil.isEmpty(id)){
            this.id = "0";
            return;
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(StringUtil.isEmpty(name)){
            this.name = "";
            return;
        }
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(StringUtil.isEmpty(email)){
            this.email = "";
            return;
        }
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
        if(StringUtil.isEmpty(phone)){
            this.address = "";
            return;
        }
        this.address = address;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            return ((User) obj).id.equals(this.id);
        }
        return super.equals(obj);
    }

    public static class Builder {
        private UserData userData;

        public Builder(UserData loginInfo) {
            this.userData = loginInfo;
        }

        public User build() {
            User user = null;
            switch (userData.getMemberTypeInt()) {
                case TYPE_CUSTOMER:
                    user = new Customer();
                    ((Customer) user).setPoint(userData.getPoint());
                    break;
                case TYPE_PROMOTER:
                    if (userData.isManager()) {
                        user = new PromoterLeader();
                        ((PromoterLeader) user).setMemberGroupId(userData.getMemberGroupId());
                    } else {
                        user = new Promoter();
                    }
                    ((Promoter) user).setNumberCustomers(String.valueOf(userData.getCustomers()));
                    ((Promoter) user).setSalesActual(String.valueOf(userData.getSalesActual()));
                    ((Promoter) user).setSalesExpect(String.valueOf(userData.getSalesExpect()));
                    ((Promoter) user).setFrom(String.valueOf(userData.getFrom()));
                    ((Promoter) user).setTo(String.valueOf(userData.getTo()));
                    break;
            }
            updateBase(user);
            return user;
        }

        private void updateBase(User user) {
            user.setId(String.valueOf(userData.getId()));
            user.setAddress(userData.getAddress());
            user.setAvatar(userData.getAvatar());
            user.setEmail(userData.getEmail());
            user.setName(userData.getName());
            user.setNumberOrders(String.valueOf(userData.getOrder()));
            user.setPhone(userData.getPhone());
        }
    }

    @SerializedName("role")
    protected int role = TYPE_CUSTOMER;

    public int getRole() {
        return role;
    }
}
