package com.mouks.rosie.mycoffeeshop.domain;

import java.io.Serializable;

/**
 * Created by Rosie on 2016/04/22.
 */
public class CustomerCardDetails implements Serializable {
    private Long cardId;
    private String cardType;
    private int expirationMonth;
    private int expirationYear;
    private String bankName;

    public CustomerCardDetails() {
    }

    public Long getCardId() {
        return cardId;
    }

    public String getCardType() {
        return cardType;
    }

    public int getExpirationMonnth() {
        return expirationMonth;
    }

    public int getExpirationYear() {
        return expirationYear;
    }

    public String getBankName() {
        return bankName;
    }

    public CustomerCardDetails(Builder builder) {
        this.cardId = builder.cardId;
        this.cardType = builder.cardType;
        this.expirationMonth = builder.expirationMonth;
        this.expirationYear = builder.expirationYear;
        this.bankName = builder.bankName;
    }

    public static class Builder{
        private Long cardId;
        private String cardType;
        private int expirationMonth;
        private int expirationYear;
        private String bankName;

        public Builder cardid(Long value){
            this.cardId=value;
            return this;
        }

        public Builder type(String value){
            this.cardType=value;
            return this;
        }

        public Builder expMonth(int value){
            this.expirationMonth=value;
            return this;
        }

        public Builder expYear(int value){
            this.expirationYear=value;
            return this;
        }

        public Builder name(String value){
            this.bankName=value;
            return this;
        }

        public Builder copy(CustomerCardDetails value){
            this.cardId=value.cardId;
            this.cardType= value.cardType;
            this.expirationMonth=value.expirationMonth;
            this.expirationYear = value.expirationYear;
            this.bankName= value.bankName;

            return this;
        }

        public CustomerCardDetails build(){
            return new CustomerCardDetails(this);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerCardDetails that = (CustomerCardDetails) o;

        return cardId.equals(that.cardId);

    }

    @Override
    public int hashCode() {
        return cardId.hashCode();
    }
}
