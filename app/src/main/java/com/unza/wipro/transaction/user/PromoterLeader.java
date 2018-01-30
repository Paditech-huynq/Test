package com.unza.wipro.transaction.user;

import com.google.gson.annotations.SerializedName;
import com.paditech.core.helper.StringUtil;

public class PromoterLeader extends Promoter {
    @SerializedName("is_manager")
    private String isManager = "1";

    @SerializedName("member_group_id")
    private String memberGroupId;

    public String getMemberGroupId() {
        return memberGroupId;
    }

    public void setMemberGroupId(String memberGroupId) {
        if(StringUtil.isEmpty(memberGroupId)){
            this.memberGroupId = "";
            return;
        }
        this.memberGroupId = memberGroupId;
    }

}
