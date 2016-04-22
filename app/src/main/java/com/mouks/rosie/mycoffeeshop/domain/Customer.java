package com.mouks.rosie.mycoffeeshop.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Rosie on 2016/04/22.
 */
public class Customer implements Serializable {
    private Long customerId;
    private String firstName;
    private String surname, emailAddress, cellNumber;
    private Date dob;
    private String gender;

    public Customer() {
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public Date getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    public Customer(Builder builder) {
        this.customerId = builder.customerId;
        this.firstName = builder.firstName;
        this.surname = builder.surname;
        this.emailAddress = builder.emailAddress;
        this.cellNumber = builder.cellNumber;
        this.dob = builder.dob;
        this.gender = builder.gender;
    }

    public static class Builder{
        private Long customerId;
        private String firstName;
        private String surname, emailAddress, cellNumber;
        private Date dob;
        private String gender;

        public Builder customerid(Long value){
            this.customerId=value;
            return this;
        }

        public Builder first(String value){
            this.firstName=value;
            return this;
        }

        public Builder surname(String value){
            this.surname=value;
            return this;
        }

        public Builder email(String value){
            this.emailAddress=value;
            return this;
        }

        public Builder cell(String value){
            this.cellNumber=value;
            return this;
        }
        public Builder dob(Date value){
            this.dob=value;
            return this;
        }
        public Builder gender(String value){
            this.gender=value;
            return this;
        }

        public Builder copy(Customer value){
            this.customerId=value.customerId;
            this.firstName= value.firstName;
            this.surname=value.surname;
            this.emailAddress = value.emailAddress;
            this.dob= value.dob;
            this.cellNumber = value.cellNumber;
            this.gender = value.gender;

            return this;
        }

        public Customer build(){
            return new Customer(this);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        return customerId.equals(customer.customerId);

    }

    @Override
    public int hashCode() {
        return customerId.hashCode();
    }
}
