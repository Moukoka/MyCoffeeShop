package com.mouks.rosie.mycoffeeshop.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Rosie on 2016/04/22.
 */
public class Product implements Serializable {
    private Long productId;
    private String productName, description;
    private double unitPrice;
    private int availableQty;
    private Size size;
    private List<Ingredients> ingredientsList;

    public Product() {
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getAvailableQty() {
        return availableQty;
    }

    public Size getSize() {
        return size;
    }

    public List<Ingredients> getIngredientsList() {
        return ingredientsList;
    }

    public Product(Builder builder) {
        this.productId = builder.productId;
        this.productName = builder.productName;
        this.description = builder.description;
        this.unitPrice = builder.unitPrice;
        this.availableQty = builder.availableQty;
        this.size = builder.size;
        this.ingredientsList = builder.ingredientsList;
    }

    public static class Builder{
        private Long productId;
        private String productName, description;
        private double unitPrice;
        private int availableQty;
        private Size size;
        private List<Ingredients> ingredientsList;

        public Builder productid(Long value){
            this.productId=value;
            return this;
        }

        public Builder prodName(String value){
            this.productName=value;
            return this;
        }

        public Builder descr(String value){
            this.description=value;
            return this;
        }
        public Builder unitP(double value){
            this.unitPrice=value;
            return this;
        }

        public Builder avQty(int value){
            this.availableQty=value;
            return this;
        }

        public Builder size(Size value){
            this.size=value;
            return this;
        }
        public Builder ingr(List<Ingredients> value){
            this.ingredientsList=value;
            return this;
        }


        public Builder copy(Product value){
            this.productId=value.productId;
            this.productName= value.productName;
            this.description=value.description;
            this.unitPrice=value.unitPrice;
            this.availableQty=value.availableQty;
            this.size = value.size;
            this.ingredientsList = value.ingredientsList;

            return this;
        }

        public Product build(){
            return new Product(this);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return productId.equals(product.productId);

    }

    @Override
    public int hashCode() {
        return productId.hashCode();
    }
}
