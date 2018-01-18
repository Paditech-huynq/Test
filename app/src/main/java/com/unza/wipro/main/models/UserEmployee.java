package com.unza.wipro.main.models;


import java.util.Date;

public class UserEmployee extends User {
    private Date dateStart;
    private long saleWant;
    private long saleHave;

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public long getSaleWant() {
        return saleWant;
    }

    public void setSaleWant(long saleWant) {
        this.saleWant = saleWant;
    }

    public long getSaleHave() {
        return saleHave;
    }

    public void setSaleHave(long saleHave) {
        this.saleHave = saleHave;
    }
}
