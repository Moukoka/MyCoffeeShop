package com.mouks.rosie.mycoffeeshop.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Rosie on 2016/04/22.
 */
public class Payment implements Serializable {
    private Long paymentId;
    private double amount;
    private String status;
    private Date paymentDate;

    public Payment() {
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public Payment(Builder builder) {
        this.paymentId = builder.paymentId;
        this.amount = builder.amount;
        this.status = builder.status;
        this.paymentDate = builder.paymentDate;
    }

    public static class Builder{
        private Long paymentId;
        private double amount;
        private String status;
        private Date paymentDate;

        public Builder paymentid(Long value){
            this.paymentId=value;
            return this;
        }

        public Builder amount(double value){
            this.amount=value;
            return this;
        }

        public Builder status(String value){
            this.status=value;
            return this;
        }

        public Builder pDate(Date value){
            this.paymentDate=value;
            return this;
        }


        public Builder copy(Payment value){
            this.paymentId=value.paymentId;
            this.paymentDate= value.paymentDate;
            this.amount=value.amount;
            this.status = value.status;

            return this;
        }

        public Payment build(){
            return new Payment(this);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;

        return paymentId.equals(payment.paymentId);

    }

    @Override
    public int hashCode() {
        return paymentId.hashCode();
    }
}
