package com.unza.wipro.transaction.user;

import android.widget.EditText;
import android.widget.TextView;

import com.unza.wipro.R;

import butterknife.BindView;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/25/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public class DeliveryInfo {
    private String name;
    private String phone;
    private String  address;
    private String date;
    private String note;

    public DeliveryInfo(String name, String phone, String address, String date, String note) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.date = date;
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
