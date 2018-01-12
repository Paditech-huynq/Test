package com.unza.wipro.main.models;

import com.unza.wipro.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by bangindong on 1/12/2018.
 */

public class OrderData {

    List<OrderClass> list = new ArrayList<>();

    public OrderData() {
        list.add(new OrderClass(R.drawable.background, "mỹ phẩm", new Date(2018,1,2,15,25), 1000000, 3));
        list.add(new OrderClass(R.drawable.background, "mỹ phẩm", new Date(2018,1,2,15,25), 1000000, 3));
        list.add(new OrderClass(R.drawable.background, "mỹ phẩm", new Date(2018,2,2,15,25), 1000000, 3));
        list.add(new OrderClass(R.drawable.background, "mỹ phẩm", new Date(2018,2,2,15,25), 1000000, 3));
        list.add(new OrderClass(R.drawable.background, "mỹ phẩm", new Date(2018,3,2,15,25), 1000000, 3));
        list.add(new OrderClass(R.drawable.background, "mỹ phẩm", new Date(2018,3,2,15,25), 1000000, 3));
        list.add(new OrderClass(R.drawable.background, "mỹ phẩm", new Date(2018,4,2,15,25), 1000000, 3));
        list.add(new OrderClass(R.drawable.background, "mỹ phẩm", new Date(2018,5,2,15,25), 1000000, 3));
        list.add(new OrderClass(R.drawable.background, "mỹ phẩm", new Date(2018,5,2,15,25), 1000000, 3));
        list.add(new OrderClass(R.drawable.background, "mỹ phẩm", new Date(2018,6,2,15,25), 1000000, 3));
        list.add(new OrderClass(R.drawable.background, "mỹ phẩm", new Date(2018,7,2,15,25), 1000000, 3));
        list.add(new OrderClass(R.drawable.background, "mỹ phẩm", new Date(2018,10,2,15,25), 1000000, 3));
        list.add(new OrderClass(R.drawable.background, "mỹ phẩm", new Date(2018,12,2,15,25), 1000000, 3));
        list.add(new OrderClass(R.drawable.background, "mỹ phẩm", new Date(2019,1,2,15,25), 1000000, 3));
        list.add(new OrderClass(R.drawable.background, "mỹ phẩm", new Date(2019,2,2,15,25), 1000000, 3));
        list.add(new OrderClass(R.drawable.background, "mỹ phẩm", new Date(2019,3,2,15,25), 1000000, 3));
    }

    public List<OrderClass> getList() {
        return list;
    }
}
