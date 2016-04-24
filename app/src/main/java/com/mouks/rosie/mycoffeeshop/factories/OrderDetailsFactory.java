package com.mouks.rosie.mycoffeeshop.factories;

import com.mouks.rosie.mycoffeeshop.domain.Ingredients;
import com.mouks.rosie.mycoffeeshop.domain.OrderDetails;
import com.mouks.rosie.mycoffeeshop.domain.Size;

import java.util.Date;

/**
 * Created by Rosie on 2016/04/23.
 */
public class OrderDetailsFactory {
    public static OrderDetails getOrderDetails(int qty, double total, Date date,String stat, Size size, Ingredients ingr){
        return new OrderDetails.Builder()
                .qty(qty).total(total).orderDate(date).status(stat).size(size).ingr(ingr).build();

    }
}
