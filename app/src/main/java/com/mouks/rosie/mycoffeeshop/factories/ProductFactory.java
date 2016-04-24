package com.mouks.rosie.mycoffeeshop.factories;

import com.mouks.rosie.mycoffeeshop.domain.Ingredients;
import com.mouks.rosie.mycoffeeshop.domain.Product;
import com.mouks.rosie.mycoffeeshop.domain.Size;

import java.util.List;

/**
 * Created by Rosie on 2016/04/23.
 */
public class ProductFactory {
    public static Product getProduct (String name, String desc, double price,int qty,Size size,List<Ingredients> ingredients){
        return new Product.Builder().prodName(name)
                .descr(desc).unitP(price).avQty(qty).size(size).ingr(ingredients).build();
    }
}
