package com.mouks.rosie.mycoffeeshop.factories;

import com.mouks.rosie.mycoffeeshop.domain.CustomerCardDetails;

/**
 * Created by Rosie on 2016/04/23.
 */
public class CustomerCardDetailsFactory {
    public static CustomerCardDetails getCardDetails(String cardType, int month, int year,String bankname){
        return new CustomerCardDetails.Builder()
                .type(cardType).expMonth(month).expYear(year).name(bankname).build();
    }
}
