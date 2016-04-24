package com.mouks.rosie.mycoffeeshop.factories;

import com.mouks.rosie.mycoffeeshop.domain.Customer;

import java.util.Date;

/**
 * Created by Rosie on 2016/04/22.
 */
public class CustomerFactory {
    public static Customer getCustomer(String firstname, String surname,String email, String cell, Date dob, String gender){
        return new Customer.Builder().first(firstname).surname(surname).email(email).cell(cell).gender(gender).dob(dob).build();

    }
}
