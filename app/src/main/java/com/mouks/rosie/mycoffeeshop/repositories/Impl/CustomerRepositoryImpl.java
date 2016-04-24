package com.mouks.rosie.mycoffeeshop.repositories.Impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;
import android.util.Log;

import com.mouks.rosie.mycoffeeshop.conf.DBConstants;
import com.mouks.rosie.mycoffeeshop.domain.Customer;
import com.mouks.rosie.mycoffeeshop.repositories.CustomerRepository;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.Date;

/**
 * Created by Rosie on 2016/04/22.
 */
public class CustomerRepositoryImpl extends SQLiteOpenHelper implements CustomerRepository {

    DateFormat df = new DateFormat();
    //SimpleDateFormat("dd-MMM-yyyy");

    public static final String TABLE_NAME = "customer";
    private SQLiteDatabase db;

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FIRSTNAME = "firstname";
    public static final String COLUMN_SURNAME = "surname";
    public static final String COLUMN_EMAIL = "email_address";
    public static final String COLUMN_CELLNUMBER = "cellphone_number";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_DATEOFBIRTH = "date_of_birth";


    // Database creation sql statement
    private static final String DATABASE_CREATE = " CREATE TABLE "
            + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_FIRSTNAME + " TEXT UNIQUE NOT NULL , "
            + COLUMN_SURNAME + " TEXT NOT NULL , "
            + COLUMN_EMAIL + " TEXT UNIQUE NOT NULL , "
            + COLUMN_CELLNUMBER + " TEXT NOT NULL , "
            + COLUMN_GENDER + " TEXT NOT NULL , "
            + COLUMN_DATEOFBIRTH + " DATE );";

    public CustomerRepositoryImpl(Context context) {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }
    public void open() throws SQLException {
        db = this.getWritableDatabase();
    }

    public void close() {
        this.close();
    }

    @Override
    public Customer findById(Long anId) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{
                        COLUMN_ID,
                        COLUMN_FIRSTNAME,
                        COLUMN_SURNAME,
                        COLUMN_EMAIL,
                        COLUMN_CELLNUMBER,
                        COLUMN_GENDER,
                        COLUMN_DATEOFBIRTH},
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(anId)},
                null,
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            final Customer customer = new Customer.Builder()
                    .customerid(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)))
                    .first(cursor.getString(cursor.getColumnIndex(COLUMN_FIRSTNAME)))
                    .surname(cursor.getString(cursor.getColumnIndex(COLUMN_SURNAME)))
                    .email(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)))
                    .cell(cursor.getString(cursor.getColumnIndex(COLUMN_CELLNUMBER)))
                    .gender(cursor.getString(cursor.getColumnIndex(COLUMN_GENDER)))
                    //.dob(cursor.getString(cursor.getColumnIndex(COLUMN_DATEOFBIRTH)))
                    .build();

            return customer;
        } else {
            return null;
        }
    }

    @Override
    public Customer save(Customer entity) {
        open();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, entity.getCustomerId());
        values.put(COLUMN_FIRSTNAME, entity.getFirstName());
        values.put(COLUMN_SURNAME, entity.getSurname());
        values.put(COLUMN_EMAIL, entity.getEmailAddress());
        values.put(COLUMN_CELLNUMBER, entity.getCellNumber());
        values.put(COLUMN_GENDER, entity.getGender());
        values.put(COLUMN_DATEOFBIRTH, String.valueOf(entity.getDob()));
        long id = db.insertOrThrow(TABLE_NAME, null, values);
        Customer insertedEntity = new Customer.Builder()
                .copy(entity)
                .customerid(new Long(id))
                .build();
        return insertedEntity;
    }

    @Override
    public Customer update(Customer entity) {
        open();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, entity.getCustomerId());
        values.put(COLUMN_FIRSTNAME, entity.getFirstName());
        values.put(COLUMN_SURNAME, entity.getSurname());
        values.put(COLUMN_EMAIL, entity.getEmailAddress());
        values.put(COLUMN_CELLNUMBER, entity.getCellNumber());
        values.put(COLUMN_GENDER, entity.getGender());
        values.put(COLUMN_DATEOFBIRTH, String.valueOf(entity.getDob()));

        db.update(
                TABLE_NAME,
                values,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(entity.getCustomerId())}
        );
        return entity;
    }

    @Override
    public Customer delete(Customer entity) {
        open();
        db.delete(
                TABLE_NAME,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(entity.getCustomerId())});
        return entity;
    }

    @Override
    public Set<Customer> findAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Set<Customer> customers = new HashSet<>();
        open();
        Cursor cursor = db.query(TABLE_NAME, null,null,null,null,null,null);
        if (cursor.moveToFirst()) {
            do {
                 final Customer customer = new Customer.Builder()
                    .customerid(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)))
                    .first(cursor.getString(cursor.getColumnIndex(COLUMN_FIRSTNAME)))
                    .surname(cursor.getString(cursor.getColumnIndex(COLUMN_SURNAME)))
                    .email(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)))
                    .cell(cursor.getString(cursor.getColumnIndex(COLUMN_CELLNUMBER)))
                    .gender(cursor.getString(cursor.getColumnIndex(COLUMN_GENDER)))
                    //.dob(cursor.getString(cursor.getColumnIndex(COLUMN_DATEOFBIRTH)))
                    .build();

                customers.add(customer);
            } while (cursor.moveToNext());
        }
        return customers;
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
