package com.unza.wipro.transaction.user;

import com.google.gson.annotations.SerializedName;

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
        if (salesActual == null) {
            return "0";
        }
        return salesExpect;
    }

    public void setSalesExpect(String salesExpect) {
        this.salesExpect = salesExpect;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSalesActual() {
        if (salesActual == null) {
            return "0";
        }
        return salesActual;
    }

    public void setSalesActual(String salesActual) {
        this.salesActual = salesActual;
    }

    public String getNumberCustomers() {
        if (numberCustomers == null) {
            return "0";
        }
        return numberCustomers;
    }

    public void setNumberCustomers(String numberCustomers) {
        this.numberCustomers = numberCustomers;
    }

    public Promoter() {
        role = TYPE_PROMOTER;
    }
}