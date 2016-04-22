package com.mouks.rosie.mycoffeeshop.domain;

import java.io.Serializable;

/**
 * Created by Rosie on 2016/04/22.
 */
public class Supplier implements Serializable {
    private Long supplierId;
    private String companyName, contactName, contactSurname,contactTitle;
    private String streetAddress, suburb, city, country, postalCode;
    private String phone, fax, email, goodType;

    public Supplier() {
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactSurname() {
        return contactSurname;
    }

    public String getContactTitle() {
        return contactTitle;
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

    public String getPhone() {
        return phone;
    }

    public String getFax() {
        return fax;
    }

    public String getEmail() {
        return email;
    }

    public String getGoodType() {
        return goodType;
    }

    public Supplier(Builder builder) {
        this.supplierId = builder.supplierId;
        this.companyName = builder.companyName;
        this.contactName = builder.contactName;
        this.contactSurname = builder.contactSurname;
        this.contactTitle = builder.contactTitle;
        this.streetAddress = builder.streetAddress;
        this.suburb = builder.suburb;
        this.city = builder.city;
        this.country = builder.country;
        this.postalCode = builder.postalCode;
        this.phone = builder.phone;
        this.fax = builder.fax;
        this.email = builder.email;
        this.goodType = builder.goodType;
    }

    public static class Builder{
        private Long supplierId;
        private String companyName, contactName, contactSurname,contactTitle;
        private String streetAddress, suburb, city, country, postalCode;
        private String phone, fax, email, goodType;

        public Builder supplierid(Long value){
            this.supplierId=value;
            return this;
        }
        public Builder compName(String value){
            this.companyName=value;
            return this;
        }
        public Builder contact(String value){
            this.contactName=value;
            return this;
        }
        public Builder surname(String value){
            this.contactSurname=value;
            return this;
        }
        public Builder title(String value){
            this.contactTitle=value;
            return this;
        }
        public Builder street(String value){
            this.streetAddress=value;
            return this;
        }
        public Builder sub(String value){
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
        public Builder phone(String value){
            this.phone=value;
            return this;
        }

        public Builder fax(String value){
            this.fax=value;
            return this;
        }

        public Builder email(String value){
            this.email=value;
            return this;
        }
        public Builder goodType(String value){
            this.goodType=value;
            return this;
        }
        public Builder copy(Supplier value){
            this.supplierId=value.supplierId;
            this.contactName= value.contactName;
            this.contactSurname= value.contactSurname;
            this.contactTitle= value.contactTitle;
            this.streetAddress= value.streetAddress;
            this.suburb= value.suburb;
            this.city = value.city;
            this.country = value.country;
            this.postalCode=value.postalCode;
            this.phone=value.phone;
            this.postalCode=value.postalCode;
            this.fax=value.fax;
            this.email= value.email;
            this.goodType = value.goodType;
            return this;
        }

        public Supplier build(){
            return new Supplier(this);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Supplier supplier = (Supplier) o;

        return supplierId.equals(supplier.supplierId);

    }

    @Override
    public int hashCode() {
        return supplierId.hashCode();
    }
}
