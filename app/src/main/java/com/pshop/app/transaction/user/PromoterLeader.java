package com.pshop.app.transaction.user;

import com.google.gson.annotations.SerializedName;

public class PromoterLeader extends Promoter {
    @SerializedName("member_group_id")
    private String memberGroupId;

    public PromoterLeader() {
        role = TYPE_PROMOTER_LEADER;
    }

    public String getMemberGroupId() {
        return memberGroupId;
    }

    public void setMemberGroupId(String memberGroupId) {
        this.memberGroupId = memberGroupId;
    }
}
