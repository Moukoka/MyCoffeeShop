package com.mouks.rosie.mycoffeeshop.repositories.Impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mouks.rosie.mycoffeeshop.conf.DBConstants;
import com.mouks.rosie.mycoffeeshop.domain.Payment;
import com.mouks.rosie.mycoffeeshop.repositories.PaymentRepository;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Rosie on 2016/04/23.
 */
public class PaymentRepoImpl extends SQLiteOpenHelper implements PaymentRepository {

    public static final String TABLE_NAME = "payment";
    private SQLiteDatabase db;

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_AMOUNT = "amount_paid";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_PAYMENTDATE = "payment_date";


    // Database creation sql statement
    private static final String DATABASE_CREATE = " CREATE TABLE "
            + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_AMOUNT + " INTEGER NOT NULL , "
            + COLUMN_PAYMENTDATE + " DATE , "
            + COLUMN_STATUS + " TEXT NOT NULL );";


    public PaymentRepoImpl(Context context) {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }

    public void open() throws SQLException {
        db = this.getWritableDatabase();
    }

    public void close() {
        this.close();
    }

    @Override
    public Payment findById(Long aLong) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{
                        COLUMN_ID,
                        COLUMN_AMOUNT,
                        COLUMN_PAYMENTDATE,
                        COLUMN_STATUS},
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(aLong)},
                null,
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            final Payment payment = new Payment.Builder()
                    .paymentid(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)))
                    .amount(cursor.getDouble(cursor.getColumnIndex(COLUMN_AMOUNT)))
                    //.pDate(cursor.getString(cursor.getColumnIndex(COLUMN_PAYMENTDATE)))
                    .status(cursor.getString(cursor.getColumnIndex(COLUMN_STATUS)))
                    .build();

            return payment;
        } else {
            return null;
        }
    }

    @Override
    public Payment save(Payment entity) {
        open();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, entity.getPaymentId());
        values.put(COLUMN_AMOUNT, entity.getAmount());
        values.put(COLUMN_PAYMENTDATE, entity.getAmount());
        values.put(COLUMN_STATUS, entity.getStatus());

        long id = db.insertOrThrow(TABLE_NAME, null, values);
        Payment insertedEntity = new Payment.Builder()
                .copy(entity)
                .paymentid(new Long(id))
                .build();
        return insertedEntity;
    }

    @Override
    public Payment update(Payment entity) {
        open();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, entity.getPaymentId());
        values.put(COLUMN_AMOUNT, entity.getAmount());
        values.put(COLUMN_PAYMENTDATE, entity.getAmount());
        values.put(COLUMN_STATUS, entity.getStatus());
        db.update(
                TABLE_NAME,
                values,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(entity.getPaymentId())}
        );
        return entity;
    }

    @Override
    public Payment delete(Payment entity) {
        open();
        db.delete(
                TABLE_NAME,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(entity.getPaymentId())});
        return entity;
    }

    @Override
    public Set<Payment> findAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Set<Payment> payments = new HashSet<>();
        open();
        Cursor cursor = db.query(TABLE_NAME, null,null,null,null,null,null);
        if (cursor.moveToFirst()) {
            do {
                final Payment payment = new Payment.Builder()
                        .paymentid(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)))
                        .amount(cursor.getDouble(cursor.getColumnIndex(COLUMN_AMOUNT)))
                                //.pDate(cursor.getString(cursor.getColumnIndex(COLUMN_PAYMENTDATE)))
                        .status(cursor.getString(cursor.getColumnIndex(COLUMN_STATUS)))
                        .build();

                payments.add(payment);
            } while (cursor.moveToNext());
        }
        return payments;
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
