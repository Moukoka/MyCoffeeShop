package com.mouks.rosie.mycoffeeshop.factories;

import com.mouks.rosie.mycoffeeshop.domain.Supplier;

/**
 * Created by Rosie on 2016/04/23.
 */
public class SupplierFactory {
    public static Supplier getSupplier(String name, String cname, String sname, String title,String ad,String sub, String city, String country, String pcode, String phone, String fax, String email,String type){
        return new Supplier.Builder().compName(name).contact(name).surname(sname)
                .title(title).street(ad).sub(sub).city(city).country(country).postalCode(pcode)
                .phone(phone).fax(fax).email(email).goodType(type).build();
    }
}
