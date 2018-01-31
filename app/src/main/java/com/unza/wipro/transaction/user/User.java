package com.unza.wipro.transaction.user;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
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
        this.numberOrders = numberCustomers;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
                    ((Promoter) user).setNumberCustomers(userData.getCustomers());
                    ((Promoter) user).setSalesActual(userData.getSalesActual());
                    ((Promoter) user).setSalesExpect(userData.getSalesExpect());
                    ((Promoter) user).setFrom(userData.getFrom());
                    ((Promoter) user).setTo(userData.getTo());
                    Log.e("AAA", userData.getSalesActual());
                    break;
            }
            updateBase(user);
            return user;
        }

        private void updateBase(User user) {
            user.setId(userData.getId());
            user.setAddress(userData.getAddress());
            user.setAvatar(userData.getAvatar());
            user.setEmail(userData.getEmail());
            user.setName(userData.getName());
            user.setNumberOrders(userData.getOrder());
            user.setPhone(userData.getPhone());
        }
    }

    @SerializedName("role")
    protected int role = TYPE_CUSTOMER;

    public int getRole() {
        return role;
    }
}
