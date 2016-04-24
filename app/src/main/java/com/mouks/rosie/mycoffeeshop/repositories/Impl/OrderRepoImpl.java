package com.mouks.rosie.mycoffeeshop.repositories.Impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mouks.rosie.mycoffeeshop.conf.DBConstants;
import com.mouks.rosie.mycoffeeshop.domain.Order;
import com.mouks.rosie.mycoffeeshop.repositories.OrderRepository;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Rosie on 2016/04/23.
 */
public class OrderRepoImpl extends SQLiteOpenHelper implements OrderRepository {

    public static final String TABLE_NAME = "order";
    private SQLiteDatabase db;

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PAYMENTID = "payment_id";
    public static final String COLUMN_PRODUCTID = "product_id";
    public static final String COLUMN_CUSTOMERID = "customer_id";
    public static final String COLUMN_QUANTITY = "quantity_ordered";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_ORDERDATE = "order_date";

    // Database creation sql statement
    private static final String DATABASE_CREATE = " CREATE TABLE "
            + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_CUSTOMERID + " TEXT UNIQUE FOREIGN KEY NOT NULL , "
            + COLUMN_PRODUCTID + " TEXT UNIQUE FOREIGN KEY NOT NULL , "
            + COLUMN_QUANTITY + " INTEGER NOT NULL , "
            + COLUMN_ORDERDATE + " DATE  , "
            + COLUMN_PAYMENTID + " TEXT UNIQUE FOREIGN KEY NOT NULL , "
            + COLUMN_STATUS + " TEXT NOT NULL );";


    public OrderRepoImpl(Context context) {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }

    public void open() throws SQLException {
        db = this.getWritableDatabase();
    }

    public void close() {
        this.close();
    }
    @Override
    public Order findById(Long aLong) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{
                        COLUMN_ID,
                        COLUMN_CUSTOMERID ,
                                 COLUMN_PRODUCTID ,
                                 COLUMN_QUANTITY ,
                                 COLUMN_ORDERDATE ,
                                 COLUMN_PAYMENTID ,
                                 COLUMN_STATUS},
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(aLong)},
                null,
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            final Order order = new Order.Builder()
                    .orderid(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)))
                    .qty(cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY)))
                    //.orderDate(cursor.getString(cursor.getColumnIndex(COLUMN_ORDERDATE)))
                    .status(cursor.getString(cursor.getColumnIndex(COLUMN_STATUS)))

                    .build();

            return order;
        } else {
            return null;
        }
    }

    @Override
    public Order save(Order entity) {
        open();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, entity.getOrderId());
        values.put(COLUMN_QUANTITY, entity.getQuantity());
        values.put(COLUMN_ORDERDATE, String.valueOf(entity.getOrderDate()));
        values.put(COLUMN_STATUS, entity.getStatus());

        long id = db.insertOrThrow(TABLE_NAME, null, values);
        Order insertedEntity = new Order.Builder()
                .copy(entity)
                .orderid(new Long(id))
                .build();
        return insertedEntity;
    }

    @Override
    public Order update(Order entity) {
        open();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, entity.getOrderId());
        values.put(COLUMN_QUANTITY, entity.getQuantity());
        values.put(COLUMN_ORDERDATE, String.valueOf(entity.getOrderDate()));
        values.put(COLUMN_STATUS, entity.getStatus());
        db.update(
                TABLE_NAME,
                values,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(entity.getOrderId())}
        );
        return entity;
    }

    @Override
    public Order delete(Order entity) {
        open();
        db.delete(
                TABLE_NAME,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(entity.getOrderId())});
        return entity;
    }

    @Override
    public Set<Order> findAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Set<Order> orders = new HashSet<>();
        open();
        Cursor cursor = db.query(TABLE_NAME, null,null,null,null,null,null);
        if (cursor.moveToFirst()) {
            do {
                final Order order = new Order.Builder()
                        .orderid(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)))
                        .qty(cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY)))
                                //.orderDate(cursor.getString(cursor.getColumnIndex(COLUMN_ORDERDATE)))
                        .status(cursor.getString(cursor.getColumnIndex(COLUMN_STATUS)))

                        .build();
                orders.add(order);
            } while (cursor.moveToNext());
        }
        return orders;
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
