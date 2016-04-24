package com.mouks.rosie.mycoffeeshop.factories;

import com.mouks.rosie.mycoffeeshop.domain.CustomerAddress;

/**
 * Created by Rosie on 2016/04/23.
 */
public class CustomerAddressFactory {
    public static CustomerAddress getAddress(String street,String suburb, String city, String country,String pcode,String adName){
        return new CustomerAddress.Builder()
                .street(street).suburb(suburb).city(city).country(country)
                 .postalCode(pcode).addressName(adName).build();
    }
}
