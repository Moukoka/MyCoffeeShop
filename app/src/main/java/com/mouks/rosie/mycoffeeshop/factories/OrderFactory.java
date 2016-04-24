package com.mouks.rosie.mycoffeeshop.factories;

import com.mouks.rosie.mycoffeeshop.domain.Order;

import java.sql.Date;

/**
 * Created by Rosie on 2016/04/23.
 */
public class OrderFactory {
    public static Order getOrder(int qty,String stat, Date date){
        return new Order.Builder().qty(qty).status(stat).orderDate(date).build();
    }
}
