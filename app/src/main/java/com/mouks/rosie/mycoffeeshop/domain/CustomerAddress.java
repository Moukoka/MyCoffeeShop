package com.mouks.rosie.mycoffeeshop.domain;

import java.io.Serializable;

/**
 * Created by Rosie on 2016/04/22.
 */
public class CustomerAddress implements Serializable {
    private Long id;
    private String streetAddress;
    private String suburb;
    private String city;
    private  String country;
    private String postalCode;
    private boolean addressName;

    public CustomerAddress() {
    }

    public Long getId() {
        return id;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getSuburb() {
        return suburb;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public boolean isAddressName() {
        return addressName;
    }

    public CustomerAddress(Builder builder) {
        this.id = builder.id;
        this.streetAddress = builder.streetAddress;
        this.suburb = builder.suburb;
        this.city = builder.city;
        this.country = builder.country;
        this.postalCode = builder.postalCode;
        this.addressName = builder.addressName;
    }

    public static class Builder{
        private Long id;
        private String streetAddress;
        private String suburb;
        private String city;
        private  String country;
        private String postalCode;
        private boolean addressName;

        public Builder id(Long value){
            this.id=value;
            return this;
        }

        public Builder street(String value){
            this.streetAddress=value;
            return this;
        }

        public Builder suburb(String value){
            this.suburb=value;
            return this;
        }

        public Builder city(String value){
            this.city=value;
            return this;
        }

        public Builder country(String value){
            this.country=value;
            return this;
        }
        public Builder postalCode(String value){
            this.postalCode=value;
            return this;
        }
        public Builder addressName(boolean value){
            this.addressName=value;
            return this;
        }
        public Builder copy(CustomerAddress value){
            this.id=value.id;
            this.streetAddress= value.streetAddress;
            this.suburb=value.suburb;
            this.city = value.city;
            this.country = value.country;
            this.postalCode=value.postalCode;
            this.addressName = value.addressName;
            return this;
        }

        public CustomerAddress build(){
            return new CustomerAddress(this);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerAddress customer = (CustomerAddress) o;

        return id.equals(customer.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
