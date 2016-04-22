package com.mouks.rosie.mycoffeeshop.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Rosie on 2016/04/22.
 */
public class Order implements Serializable {
    private Long orderId;
    private int quantity;
    private String status;
    private Date orderDate;

    public Order() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public String getStatus() {
        return status;
    }

    public int getQuantity() {
        return quantity;
    }

    public Order(Builder builder) {
        this.orderId = builder.orderId;
        this.orderDate = builder.orderDate;
        this.status = builder.status;
        this.quantity = builder.quantity;
    }

    public static class Builder{
        private Long orderId;
        private int quantity;
        private String status;
        private Date orderDate;

        public Builder orderid(Long value){
            this.orderId=value;
            return this;
        }

        public Builder qty(int value){
            this.quantity=value;
            return this;
        }

        public Builder status(String value){
            this.status=value;
            return this;
        }

        public Builder orderDate(Date value){
            this.orderDate=value;
            return this;
        }


        public Builder copy(Order value){
            this.orderId=value.orderId;
            this.quantity= value.quantity;
            this.status=value.status;
            this.orderDate = value.orderDate;

            return this;
        }

        public Order build(){
            return new Order(this);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return orderId.equals(order.orderId);

    }

    @Override
    public int hashCode() {
        return orderId.hashCode();
    }
}
