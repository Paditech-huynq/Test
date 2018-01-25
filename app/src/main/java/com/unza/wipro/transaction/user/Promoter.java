package com.unza.wipro.transaction.user;

import com.google.gson.annotations.SerializedName;

public class Promoter extends User {
    @SerializedName("customers")
    private String numberCustomers;
    @SerializedName("income")
    private String saleHave;
    @SerializedName("goal")
    private String saleWant;
    @SerializedName("from")
    private String from;
    @SerializedName("to")
    private String to;

    public String getSaleWant() {
        return saleWant;
    }

    public void setSaleWant(String saleWant) {
        this.saleWant = saleWant;
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

    public String getSaleHave() {
        return saleHave;
    }

    public void setSaleHave(String saleHave) {
        this.saleHave = saleHave;
    }

    public String getNumberCustomers() {
        return numberCustomers;
    }

    public void setNumberCustomers(String numberCustomers) {
        this.numberCustomers = numberCustomers;
    }
}