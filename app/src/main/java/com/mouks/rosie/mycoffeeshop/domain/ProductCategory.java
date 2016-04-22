package com.mouks.rosie.mycoffeeshop.domain;

import java.io.Serializable;

/**
 * Created by Rosie on 2016/04/22.
 */
public class ProductCategory implements Serializable {
    private Long catId;
    private String catName;
    private String catDescription;

    public ProductCategory() {
    }

    public Long getCatId() {
        return catId;
    }

    public String getCatname() {
        return catName;
    }

    public String getCatdescription() {
        return catDescription;
    }

    public ProductCategory(Builder builder) {
        this.catId = builder.catId;
        this.catDescription = builder.catDescription;
        this.catName = builder.catName;
    }

    public static class Builder{
        private Long catId;
        private String catName;
        private String catDescription;

        public Builder catid(Long value){
            this.catId=value;
            return this;
        }

        public Builder catName(String value){
            this.catName=value;
            return this;
        }

        public Builder catDescr(String value){
            this.catDescription=value;
            return this;
        }

        public Builder copy(ProductCategory value){
            this.catId=value.catId;
            this.catName= value.catName;
            this.catDescription=value.catDescription;


            return this;
        }

        public ProductCategory build(){
            return new ProductCategory(this);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductCategory that = (ProductCategory) o;

        return catId.equals(that.catId);

    }

    @Override
    public int hashCode() {
        return catId.hashCode();
    }
}
