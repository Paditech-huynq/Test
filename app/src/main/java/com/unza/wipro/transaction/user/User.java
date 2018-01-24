package com.unza.wipro.transaction.user;

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

    class Builder {
        private LoginInfo loginInfo;

        public Builder(LoginInfo loginInfo) {
            this.loginInfo = loginInfo;
        }

        User build() {
            switch (loginInfo.getMemberType()) {
                case TYPE_CUSTOMER:
                    return new Customer();
                case TYPE_PROMOTER:
                    if (loginInfo.isManager()) {
                        return new PromoterLeader();
                    } else {
                        return new Promoter();
                    }
            }

            return null;
        }
    }
}
