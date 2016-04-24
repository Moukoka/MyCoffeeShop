package com.mouks.rosie.mycoffeeshop.factories;

import com.mouks.rosie.mycoffeeshop.domain.GoodsOrder;

import java.util.Date;

/**
 * Created by Rosie on 2016/04/23.
 */
public class GoodsOrderFactory {
    public static GoodsOrder getGoods(int qty, Date orderDate, String name){
        return new GoodsOrder.Builder().qty(qty).date(orderDate).name(name).build();

    }
}
