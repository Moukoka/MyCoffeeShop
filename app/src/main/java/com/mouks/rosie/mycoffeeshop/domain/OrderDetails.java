package com.mouks.rosie.mycoffeeshop.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Rosie on 2016/04/22.
 */
public class OrderDetails implements Serializable {
    private Long orderDetId;
    private int quantity;
    private  double totalAmount;
    private Date orderDate;
    private String status;
    private Size size;
    private Ingredients ingredients;

    public OrderDetails() {
    }

    public Long getOrderDetId() {
        return orderDetId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public String getStatus() {
        return status;
    }

    public Size getSize() {
        return size;
    }

    public Ingredients getIngredients() {
        return ingredients;
    }

    public OrderDetails(Builder builder) {
        this.orderDetId = builder.orderDetId;
        this.quantity = builder.quantity;
        this.totalAmount = builder.totalAmount;
        this.orderDate = builder.orderDate;
        this.status = builder.status;
        this.size = builder.size;
        this.ingredients = builder.ingredients;
    }

    public static class Builder{
        private Long orderDetId;
        private int quantity;
        private  double totalAmount;
        private Date orderDate;
        private String status;
        private Size size;
        private Ingredients ingredients;

        public Builder orderDetid(Long value){
            this.orderDetId=value;
            return this;
        }

        public Builder qty(int value){
            this.quantity=value;
            return this;
        }

        public Builder total(double value){
            this.totalAmount=value;
            return this;
        }
        public Builder orderDate(Date value){
            this.orderDate=value;
            return this;
        }

        public Builder status(String value){
            this.status=value;
            return this;
        }

        public Builder size(Size value){
            this.size=value;
            return this;
        }
        public Builder ingr(Ingredients value){
            this.ingredients=value;
            return this;
        }


        public Builder copy(OrderDetails value){
            this.orderDetId=value.orderDetId;
            this.quantity= value.quantity;
            this.totalAmount=value.totalAmount;
            this.orderDate=value.orderDate;
            this.status=value.status;
            this.size = value.size;
            this.ingredients = value.ingredients;

            return this;
        }

        public OrderDetails build(){
            return new OrderDetails(this);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderDetails that = (OrderDetails) o;

        return orderDetId.equals(that.orderDetId);

    }

    @Override
    public int hashCode() {
        return orderDetId.hashCode();
    }
}
