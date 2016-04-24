package com.mouks.rosie.mycoffeeshop.repositories.Impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mouks.rosie.mycoffeeshop.conf.DBConstants;
import com.mouks.rosie.mycoffeeshop.domain.Supplier;
import com.mouks.rosie.mycoffeeshop.repositories.SupplierRepository;


import java.util.HashSet;
import java.util.Set;

/**
 * Created by Rosie on 2016/04/23.
 */
public class SupplierRepoImpl extends SQLiteOpenHelper implements SupplierRepository{

    public static final String TABLE_NAME = "supplier";
    private SQLiteDatabase db;

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_COMPANY = "candidateId";
    public static final String COLUMN_FIRSTNAME = "firstName";
    public static final String COLUMN_LASTNAME = "lastName";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_STREET = "street";
    public static final String COLUMN_SUBURB = "suburb";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_COUNTRY = "country";
    public static final String COLUMN_POSTALCODE = "postal_code";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_FAX = "fax";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_GOODTYPE = "goodType";


    // Database creation sql statement
    private static final String DATABASE_CREATE = " CREATE TABLE "
            + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_COMPANY + " TEXT UNIQUE NOT NULL , "
            + COLUMN_TITLE + " TEXT NOT NULL , "
            + COLUMN_FIRSTNAME+ " TEXT NOT NULL , "
            + COLUMN_LASTNAME + " TEXT NOT NULL , "
            + COLUMN_STREET + " TEXT NOT NULL , "
            + COLUMN_SUBURB + " TEXT NOT NULL , "
            + COLUMN_CITY + " TEXT NOT NULL , "
            + COLUMN_COUNTRY + " TEXT NOT NULL , "
            + COLUMN_POSTALCODE + " TEXT NOT NULL , "
            + COLUMN_FAX + " TEXT NOT NULL , "
            + COLUMN_EMAIL + " BLOB , "
            + COLUMN_GOODTYPE + " TEXT NOT NULL );";


    public SupplierRepoImpl(Context context) {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }

    public void open() throws SQLException {
        db = this.getWritableDatabase();
    }

    public void close() {
        this.close();
    }

    @Override
    public Supplier findById(Long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{
                        COLUMN_ID,
                        COLUMN_COMPANY,
                        COLUMN_TITLE,
                        COLUMN_FIRSTNAME,
                        COLUMN_LASTNAME,
                        COLUMN_STREET,
                        COLUMN_SUBURB,
                        COLUMN_CITY,
                        COLUMN_COUNTRY,
                        COLUMN_POSTALCODE,
                        COLUMN_PHONE,
                        COLUMN_FAX,
                        COLUMN_EMAIL,
                        COLUMN_GOODTYPE},
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            final Supplier supplier = new Supplier.Builder()
                    .supplierid(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)))
                    .compName(cursor.getString(cursor.getColumnIndex(COLUMN_COMPANY)))
                    .title(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)))
                    .contact(cursor.getString(cursor.getColumnIndex(COLUMN_FIRSTNAME)))
                    .surname(cursor.getString(cursor.getColumnIndex(COLUMN_LASTNAME)))
                    .street(cursor.getString(cursor.getColumnIndex(COLUMN_STREET)))
                    .sub(cursor.getString(cursor.getColumnIndex(COLUMN_SUBURB)))
                    .country(cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY)))
                    .postalCode(cursor.getString(cursor.getColumnIndex(COLUMN_POSTALCODE)))
                    .phone(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE)))
                    .fax(cursor.getString(cursor.getColumnIndex(COLUMN_FAX)))
                    .email(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)))
                    .goodType(cursor.getString(cursor.getColumnIndex(COLUMN_GOODTYPE)))
                    .build();

            return supplier;
        } else {
            return null;
        }
    }

    @Override
    public Supplier save(Supplier entity) {
        open();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, entity.getSupplierId());
        values.put(COLUMN_COMPANY, entity.getCompanyName());
        values.put(COLUMN_TITLE, entity.getContactTitle());
        values.put(COLUMN_FIRSTNAME, entity.getContactName());
        values.put(COLUMN_LASTNAME, entity.getContactSurname());
        values.put(COLUMN_STREET, entity.getStreetAddress());
        values.put(COLUMN_SUBURB, entity.getSuburb());
        values.put(COLUMN_CITY, entity.getCity());
        values.put(COLUMN_COUNTRY, entity.getCountry());
        values.put(COLUMN_POSTALCODE, entity.getPostalCode());
        values.put(COLUMN_PHONE, entity.getPhone());
        values.put(COLUMN_FAX, entity.getFax());
        values.put(COLUMN_EMAIL, entity.getEmail());
        values.put(COLUMN_GOODTYPE, entity.getGoodType());

        long id = db.insertOrThrow(TABLE_NAME, null, values);
        Supplier insertedEntity = new Supplier.Builder()
                .copy(entity)
                .supplierid(new Long(id))
                .build();
        return insertedEntity;
    }

    @Override
    public Supplier update(Supplier entity) {
        open();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, entity.getSupplierId());
        values.put(COLUMN_COMPANY, entity.getCompanyName());
        values.put(COLUMN_TITLE, entity.getContactTitle());
        values.put(COLUMN_FIRSTNAME, entity.getContactName());
        values.put(COLUMN_LASTNAME, entity.getContactSurname());
        values.put(COLUMN_STREET, entity.getStreetAddress());
        values.put(COLUMN_SUBURB, entity.getSuburb());
        values.put(COLUMN_CITY, entity.getCity());
        values.put(COLUMN_COUNTRY, entity.getCountry());
        values.put(COLUMN_POSTALCODE, entity.getPostalCode());
        values.put(COLUMN_PHONE, entity.getPhone());
        values.put(COLUMN_FAX, entity.getFax());
        values.put(COLUMN_EMAIL, entity.getEmail());
        values.put(COLUMN_GOODTYPE, entity.getGoodType());
        db.update(
                TABLE_NAME,
                values,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(entity.getSupplierId())}
        );
        return entity;
    }

    @Override
    public Supplier delete(Supplier entity) {
        open();
        db.delete(
                TABLE_NAME,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(entity.getSupplierId())});
        return entity;
    }

    @Override
    public Set<Supplier> findAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Set<Supplier> suppliers = new HashSet<>();
        open();
        Cursor cursor = db.query(TABLE_NAME, null,null,null,null,null,null);
        if (cursor.moveToFirst()) {
            do {
                final Supplier supplier = new Supplier.Builder()
                        .supplierid(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)))
                        .compName(cursor.getString(cursor.getColumnIndex(COLUMN_COMPANY)))
                        .title(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)))
                        .contact(cursor.getString(cursor.getColumnIndex(COLUMN_FIRSTNAME)))
                        .surname(cursor.getString(cursor.getColumnIndex(COLUMN_LASTNAME)))
                        .street(cursor.getString(cursor.getColumnIndex(COLUMN_STREET)))
                        .sub(cursor.getString(cursor.getColumnIndex(COLUMN_SUBURB)))
                        .country(cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY)))
                        .postalCode(cursor.getString(cursor.getColumnIndex(COLUMN_POSTALCODE)))
                        .phone(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE)))
                        .fax(cursor.getString(cursor.getColumnIndex(COLUMN_FAX)))
                        .email(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)))
                        .goodType(cursor.getString(cursor.getColumnIndex(COLUMN_GOODTYPE)))
                        .build();

                suppliers.add(supplier);
            } while (cursor.moveToNext());
        }
        return suppliers;
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
