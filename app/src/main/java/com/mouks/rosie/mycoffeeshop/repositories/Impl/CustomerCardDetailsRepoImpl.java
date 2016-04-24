package com.mouks.rosie.mycoffeeshop.repositories.Impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mouks.rosie.mycoffeeshop.conf.DBConstants;
import com.mouks.rosie.mycoffeeshop.domain.CustomerCardDetails;
import com.mouks.rosie.mycoffeeshop.repositories.CustomerCardDetailsRepository;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Rosie on 2016/04/23.
 */
public class CustomerCardDetailsRepoImpl extends SQLiteOpenHelper implements CustomerCardDetailsRepository {

    public static final String TABLE_NAME = "customerCard";
    private SQLiteDatabase db;

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CUSTOMERID = "customer_id";
    public static final String COLUMN_CARDTYPE = "card_type";
    public static final String COLUMN_EXPIRYMONTH = "expiry_month";
    public static final String COLUMN_EXPIRYEAR = "expiry_year";
    public static final String COLUMN_BANKNAME= "bank_name";


    // Database creation sql statement
    private static final String DATABASE_CREATE = " CREATE TABLE "
            + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_CUSTOMERID + " INTEGER FOREIGN KEY , "
            + COLUMN_CARDTYPE + " TEXT UNIQUE NOT NULL , "
            + COLUMN_EXPIRYMONTH + " INT NOT NULL , "
            + COLUMN_EXPIRYEAR + "NUMERIC UNIQUE NOT NULL , "
            + COLUMN_BANKNAME + " TEXT NOT NULL );";


    public CustomerCardDetailsRepoImpl(Context context) {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }
    public void open() throws SQLException {
        db = this.getWritableDatabase();
    }

    public void close() {
        this.close();
    }

    @Override
    public CustomerCardDetails findById(Long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{
                        COLUMN_ID,
                        COLUMN_CUSTOMERID,
                        COLUMN_CARDTYPE,
                        COLUMN_EXPIRYMONTH,
                        COLUMN_EXPIRYEAR,
                        COLUMN_BANKNAME},
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            final CustomerCardDetails card = new CustomerCardDetails.Builder()
                    .cardid(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)))
                    .type(cursor.getString(cursor.getColumnIndex(COLUMN_CARDTYPE)))
                    .expMonth(cursor.getInt(cursor.getColumnIndex(COLUMN_EXPIRYMONTH)))
                    .expYear(cursor.getInt(cursor.getColumnIndex(COLUMN_EXPIRYEAR)))
                    .name(cursor.getString(cursor.getColumnIndex(COLUMN_BANKNAME)))
                    .build();

            return card;
        } else {
            return null;
        }
    }

    @Override
    public CustomerCardDetails save(CustomerCardDetails entity) {
        open();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, entity.getCardId());
        values.put(COLUMN_CARDTYPE, entity.getCardType());
        values.put(COLUMN_EXPIRYMONTH, entity.getExpirationMonnth());
        values.put(COLUMN_EXPIRYEAR, entity.getExpirationYear());
        values.put(COLUMN_BANKNAME, entity.getBankName());

        long id = db.insertOrThrow(TABLE_NAME, null, values);
        CustomerCardDetails insertedEntity = new CustomerCardDetails.Builder()
                .copy(entity)
                .cardid(new Long(id))
                .build();
        return insertedEntity;
    }

    @Override
    public CustomerCardDetails update(CustomerCardDetails entity) {
        open();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, entity.getCardId());
        values.put(COLUMN_CARDTYPE, entity.getCardType());
        values.put(COLUMN_EXPIRYMONTH, entity.getExpirationMonnth());
        values.put(COLUMN_EXPIRYEAR, entity.getExpirationYear());
        values.put(COLUMN_BANKNAME, entity.getBankName());
        db.update(
                TABLE_NAME,
                values,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(entity.getCardId())}
        );
        return entity;
    }

    @Override
    public CustomerCardDetails delete(CustomerCardDetails entity) {
        open();
        db.delete(
                TABLE_NAME,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(entity.getCardId())});
        return entity;
    }

    @Override
    public Set<CustomerCardDetails> findAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Set<CustomerCardDetails> cards = new HashSet<>();
        open();
        Cursor cursor = db.query(TABLE_NAME, null,null,null,null,null,null);
        if (cursor.moveToFirst()) {
            do {
                final CustomerCardDetails card = new CustomerCardDetails.Builder()
                        .cardid(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)))
                        .type(cursor.getString(cursor.getColumnIndex(COLUMN_CARDTYPE)))
                        .expMonth(cursor.getInt(cursor.getColumnIndex(COLUMN_EXPIRYMONTH)))
                        .expYear(cursor.getInt(cursor.getColumnIndex(COLUMN_EXPIRYEAR)))
                        .name(cursor.getString(cursor.getColumnIndex(COLUMN_BANKNAME)))
                        .build();
                cards.add(card);
            } while (cursor.moveToNext());
        }
        return cards;
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
