package com.mouks.rosie.mycoffeeshop.factories;

import com.mouks.rosie.mycoffeeshop.domain.ProductCategory;

/**
 * Created by Rosie on 2016/04/23.
 */
public class ProductCategoryFactory {
    public static ProductCategory getCategory(String name, String descr){
        return new ProductCategory.Builder().catName(name).catDescr(descr).build();
    }
}
