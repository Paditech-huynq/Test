package com.unza.wipro.transaction.user;

import com.google.gson.annotations.SerializedName;

public class PromoterLeader extends Promoter {
    @SerializedName("is_manager")
    private String isManager = "1";

    @SerializedName("member_group_id")
    private String memberGroupId;

    public String getMemberGroupId() {
        return memberGroupId;
    }

    public void setMemberGroupId(String memberGroupId) {
        this.memberGroupId = memberGroupId;
    }

}
