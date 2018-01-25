package com.unza.wipro.transaction.user;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.unza.wipro.main.models.LoginInfo;
import com.unza.wipro.main.models.UserInfo;

public abstract class User implements UserInfo {
    static final String TYPE_CUSTOMER = "0";
    static final String TYPE_PROMOTER = "1";

    public enum Type {
        CUSTOMER("customer"), NORMAL(TYPE_PROMOTER);

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
        private LoginInfo loginInfo;

        public Builder(LoginInfo loginInfo) {
            this.loginInfo = loginInfo;
        }

        public User build() {
            User user = null;
            switch (loginInfo.getMemberType()) {
                case TYPE_CUSTOMER:
                    user = new Customer();
                    break;
                case TYPE_PROMOTER:
                    if (loginInfo.isManager()) {
                        user = new PromoterLeader();
                    } else {
                        user = new Promoter();
                    }
                    break;
            }
            updateBase(user);
            return user;
        }

        private void updateBase(User user) {
            user.setId(String.valueOf(loginInfo.getId()));
            user.setName(loginInfo.getName());
            user.setAddress(loginInfo.getAddress());
            user.setAvatar(loginInfo.getAvatar());
            user.setEmail(loginInfo.getEmail());
        }
    }
}
