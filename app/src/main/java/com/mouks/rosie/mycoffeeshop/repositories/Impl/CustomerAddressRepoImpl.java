package com.mouks.rosie.mycoffeeshop.repositories.Impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mouks.rosie.mycoffeeshop.conf.DBConstants;
import com.mouks.rosie.mycoffeeshop.domain.Customer;
import com.mouks.rosie.mycoffeeshop.domain.CustomerAddress;
import com.mouks.rosie.mycoffeeshop.repositories.CustomerAddressRepository;

import java.util.Set;

/**
 * Created by Rosie on 2016/04/23.
 */
public class CustomerAddressRepoImpl extends SQLiteOpenHelper implements CustomerAddressRepository {

    public static final String TABLE_NAME = "customerAddress";
    private SQLiteDatabase db;

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CUSTOMERID = "customer_id";
    public static final String COLUMN_STREETADDRESS = "street_Address";
    public static final String COLUMN_SUBURB = "suburb";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_COUNTRY = "country";
    public static final String COLUMN_POSTALCODE = "postal_code";
    public static final String COLUMN_ADRESSNAME = "address_name";

    // Database creation sql statement
    private static final String DATABASE_CREATE = " CREATE TABLE "
            + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_CUSTOMERID + " INTEGER FOREIGN KEY , "
            + COLUMN_STREETADDRESS + " TEXT UNIQUE NOT NULL , "
            + COLUMN_SUBURB + " TEXT NOT NULL , "
            + COLUMN_CITY + " TEXT UNIQUE NOT NULL , "
            + COLUMN_COUNTRY + " TEXT NOT NULL , "
            + COLUMN_POSTALCODE + " TEXT NOT NULL , "
            + COLUMN_ADRESSNAME + " TEXT  );";

    public CustomerAddressRepoImpl(Context context) {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }
    public void open() throws SQLException {
        db = this.getWritableDatabase();
    }

    public void close() {
        this.close();
    }

    @Override
    public CustomerAddress findById(Long aLong) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{
                        COLUMN_ID,
                        COLUMN_CUSTOMERID,
                        COLUMN_STREETADDRESS,
                        COLUMN_SUBURB,
                        COLUMN_CITY,
                        COLUMN_COUNTRY,
                        COLUMN_POSTALCODE,
                        COLUMN_ADRESSNAME},
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(aLong)},
                null,
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            final CustomerAddress customerAd = new CustomerAddress.Builder()
                    .id(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)))
                    .street(cursor.getString(cursor.getColumnIndex(COLUMN_STREETADDRESS)))
                    .suburb(cursor.getString(cursor.getColumnIndex(COLUMN_SUBURB)))
                    .city(cursor.getString(cursor.getColumnIndex(COLUMN_CITY)))
                    .country(cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY)))
                    .postalCode(cursor.getString(cursor.getColumnIndex(COLUMN_POSTALCODE)))
                    .addressName(cursor.getString(cursor.getColumnIndex(COLUMN_ADRESSNAME)))
                    .build();
            /*final Customer cust = new Customer.Builder()
                    .customerid(cursor.getLong(cursor.getColumnIndex(COLUMN_CUSTOMERID))).build();*/
            return customerAd;
        } else {
            return null;
        }
    }

    @Override
    public CustomerAddress save(CustomerAddress entity/*, Customer ent*/) {
        open();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, entity.getId());
        //values.put(COLUMN_CUSTOMERID, ent.getCustomerId());
        values.put(COLUMN_STREETADDRESS, entity.getStreetAddress());
        values.put(COLUMN_SUBURB, entity.getSuburb());
        values.put(COLUMN_CITY, entity.getCity());
        values.put(COLUMN_COUNTRY, entity.getCountry());
        values.put(COLUMN_POSTALCODE, entity.getPostalCode());
        values.put(COLUMN_ADRESSNAME, String.valueOf(entity.isAddressName()));
        long id = db.insertOrThrow(TABLE_NAME, null, values);
        CustomerAddress insertedEntity = new CustomerAddress.Builder()
                .copy(entity)
                .id(new Long(id))
                .build();
        return insertedEntity;
    }

    @Override
    public CustomerAddress update(CustomerAddress entity) {
        open();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, entity.getId());
        //values.put(COLUMN_CUSTOMERID, ent.getCustomerId());
        values.put(COLUMN_STREETADDRESS, entity.getStreetAddress());
        values.put(COLUMN_SUBURB, entity.getSuburb());
        values.put(COLUMN_CITY, entity.getCity());
        values.put(COLUMN_COUNTRY, entity.getCountry());
        values.put(COLUMN_POSTALCODE, entity.getPostalCode());
        values.put(COLUMN_ADRESSNAME, String.valueOf(entity.isAddressName()));

        db.update(
                TABLE_NAME,
                values,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(entity.getId())}
        );
        return entity;
    }

    @Override
    public CustomerAddress delete(CustomerAddress entity) {
        open();
        db.delete(
                TABLE_NAME,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(entity.getId())});
        return entity;
    }

    @Override
    public Set<CustomerAddress> findAll() {
        return null;
    }

    @Override
    public int deleteAll() {
        open();
        int rowsDeleted = db.delete(TABLE_NAME,null,null);
        close();
        return rowsDeleted;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(this.getClass().getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
