package com.pshop.app.main.models.responses;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pshop.app.transaction.user.Promoter;

import java.util.ArrayList;
import java.util.List;

public class GetListPromoterInGroupRSP {
    @SerializedName("data")
    @Expose
    private List<Promoter> data = new ArrayList<>();

    public List<Promoter> getData() {
        return data;
    }

    public void setData(List<Promoter> data) {
        this.data = data;
    }
}
