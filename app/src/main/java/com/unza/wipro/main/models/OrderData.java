package com.unza.wipro.main.models;

import android.annotation.SuppressLint;

import com.unza.wipro.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderData {

    private List<OrderClass> data = new ArrayList<>();

    public OrderData() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        try {
            data.add(new OrderClass(R.drawable.bg_test, "mỹ phẩm", format.parse("2018/01/02 02:15:25"), 1000000, 3));

        data.add(new OrderClass(R.drawable.bg_test, "mỹ phẩm", format.parse("2018/01/02 02:15:25"), 1000000, 3));
        data.add(new OrderClass(R.drawable.bg_test, "mỹ phẩm", format.parse("2018/02/02 02:15:25"), 1000000, 3));
        data.add(new OrderClass(R.drawable.bg_test, "mỹ phẩm", format.parse("2018/03/02 02:15:25"), 1000000, 3));
        data.add(new OrderClass(R.drawable.bg_test, "mỹ phẩm", format.parse("2018/04/02 02:15:25"), 1000000, 3));
        data.add(new OrderClass(R.drawable.bg_test, "mỹ phẩm", format.parse("2018/04/02 02:15:25"), 1000000, 3));
        data.add(new OrderClass(R.drawable.bg_test, "mỹ phẩm", format.parse("2018/05/02 02:15:25"), 1000000, 3));
        data.add(new OrderClass(R.drawable.bg_test, "mỹ phẩm", format.parse("2018/06/02 02:15:25"), 1000000, 3));
        data.add(new OrderClass(R.drawable.bg_test, "mỹ phẩm", format.parse("2018/10/02 02:15:25"), 1000000, 3));
        data.add(new OrderClass(R.drawable.bg_test, "mỹ phẩm", format.parse("2018/12/02 02:15:25"), 1000000, 3));
        data.add(new OrderClass(R.drawable.bg_test, "mỹ phẩm", format.parse("2018/12/02 02:15:25"), 1000000, 3));
        data.add(new OrderClass(R.drawable.bg_test, "mỹ phẩm", format.parse("2018/12/02 02:15:25"), 1000000, 3));
        data.add(new OrderClass(R.drawable.bg_test, "mỹ phẩm", format.parse("2019/01/02 02:15:25"), 1000000, 3));
        data.add(new OrderClass(R.drawable.bg_test, "mỹ phẩm", format.parse("2019/01/02 02:15:25"), 1000000, 3));
        data.add(new OrderClass(R.drawable.bg_test, "mỹ phẩm", format.parse("2019/02/02 02:15:25"), 1000000, 3));
        data.add(new OrderClass(R.drawable.bg_test, "mỹ phẩm", format.parse("2019/02/02 02:15:25"), 1000000, 3));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public List<OrderClass> getData() {
        return data;
    }
}
