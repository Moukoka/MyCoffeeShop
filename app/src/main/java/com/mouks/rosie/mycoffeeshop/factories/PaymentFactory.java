package com.mouks.rosie.mycoffeeshop.factories;

import com.mouks.rosie.mycoffeeshop.domain.Payment;

import java.sql.Date;

/**
 * Created by Rosie on 2016/04/23.
 */
public class PaymentFactory {
    public static Payment getPayment(double amount, String stat, Date date){
        return new Payment.Builder().amount(amount).status(stat).pDate(date).build();

    }
}
