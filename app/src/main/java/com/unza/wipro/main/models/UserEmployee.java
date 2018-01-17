package com.unza.wipro.main.models;


import java.util.Date;

public class UserEmployee extends User {

    private Date dateStart;
    private long saleWant;
    private long saleHave;

    public UserEmployee(int typeUse) {
        super(typeUse);
    }

    public UserEmployee(int number_sales, int number_point, String name, String phoneNumber, String email, String address, String avar, int type_use, Date dateStart, long saleWant, long saleHave) {
        super(number_sales, number_point, name, phoneNumber, email, address, avar, type_use);
        this.dateStart = dateStart;
        this.saleWant = saleWant;
        this.saleHave = saleHave;
    }

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
