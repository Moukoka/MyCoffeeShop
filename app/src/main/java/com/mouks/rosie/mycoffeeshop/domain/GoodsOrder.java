package com.mouks.rosie.mycoffeeshop.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Rosie on 2016/04/22.
 */
public class GoodsOrder implements Serializable {
    private Long id;
    private  int qtyOrdered;
    private Date date;
    private String name;

    public GoodsOrder() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public int getQtyOrdered() {
        return qtyOrdered;
    }

    public GoodsOrder(Builder builder) {
        this.id = builder.id;
        this.qtyOrdered = builder.qtyOrdered;
        this.date = builder.date;
        this.name = builder.name;
    }

    public static class Builder{
        private Long id;
        private  int qtyOrdered;
        private Date date;
        private String name;

        public Builder id(Long value){
            this.id=value;
            return this;
        }

        public Builder qty(int value){
            this.qtyOrdered=value;
            return this;
        }

        public Builder date(Date value){
            this.date=value;
            return this;
        }
        public Builder name(String value){
            this.name=value;
            return this;
        }

        public Builder copy(GoodsOrder value){
            this.id=value.id;
            this.qtyOrdered=value.qtyOrdered;
            this.date=value.date;
            this.name= value.name;

            return this;
        }

        public GoodsOrder build(){
            return new GoodsOrder(this);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GoodsOrder that = (GoodsOrder) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
