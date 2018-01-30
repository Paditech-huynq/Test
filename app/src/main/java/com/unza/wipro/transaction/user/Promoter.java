package com.unza.wipro.transaction.user;

import com.google.gson.annotations.SerializedName;
import com.paditech.core.helper.StringUtil;

public class Promoter extends User {
    @SerializedName("customers")
    private String numberCustomers;
    @SerializedName("income")
    private String salesActual;
    @SerializedName("goal")
    private String salesExpect;
    @SerializedName("from")
    private String from;
    @SerializedName("to")
    private String to;

    public String getSalesExpect() {
        return salesExpect;
    }

    public void setSalesExpect(String salesExpect) {
        if(StringUtil.isEmpty(salesExpect)){
            this.salesExpect = "0";
            return;
        }
        this.salesExpect = salesExpect;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        if(StringUtil.isEmpty(from)){
            this.from = "";
            return;
        }
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        if(StringUtil.isEmpty(to)){
            this.to = "";
            return;
        }
        this.to = to;
    }

    public String getSalesActual() {
        return salesActual;
    }

    public void setSalesActual(String salesActual) {
        if(StringUtil.isEmpty(salesActual)){
            this.salesActual = "0";
            return;
        }
        this.salesActual = salesActual;
    }

    public String getNumberCustomers() {
        return numberCustomers;
    }

    public void setNumberCustomers(String numberCustomers) {
        if(StringUtil.isEmpty(numberCustomers)){
            this.numberCustomers = "0";
            return;
        }
        this.numberCustomers = numberCustomers;
    }

    public Promoter()
    {
        role = TYPE_PROMOTER;
    }
}