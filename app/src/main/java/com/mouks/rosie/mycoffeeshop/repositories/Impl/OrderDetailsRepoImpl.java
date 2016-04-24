package com.mouks.rosie.mycoffeeshop.repositories.Impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mouks.rosie.mycoffeeshop.conf.DBConstants;
import com.mouks.rosie.mycoffeeshop.domain.OrderDetails;
import com.mouks.rosie.mycoffeeshop.domain.Size;
import com.mouks.rosie.mycoffeeshop.repositories.OrderDetailsRepository;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Rosie on 2016/04/23.
 */
public class OrderDetailsRepoImpl extends SQLiteOpenHelper implements OrderDetailsRepository {

    public static final String TABLE_NAME = "candidate";
    private SQLiteDatabase db;

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ORDERID = "orderId";
    public static final String COLUMN_PRODUCTID = "productId";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_TOTALAMOUNT = "total_amount";
    public static final String COLUMN_ORDERDATE = "order_date";
    public static final String COLUMN_STATUS= "status";
    public static final String COLUMN_SIZE= "size";
    public static final String COLUMN_INGREDIENTS= "ingredients";


    // Database creation sql statement
    private static final String DATABASE_CREATE = " CREATE TABLE "
            + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_ORDERID + " TEXT UNIQUE FOREIGN KEY , "
            + COLUMN_PRODUCTID + " TEXT UNIQUE FOREIGN KEY , "
            + COLUMN_ORDERDATE + " DATE NOT NULL , "
            + COLUMN_QUANTITY + " INTEGER , "
            + COLUMN_SIZE + " TEXT NOT NULL , "
            + COLUMN_INGREDIENTS + " TEXT NOT NULL , "
            + COLUMN_STATUS + " TEXT NOT NULL );";


    public OrderDetailsRepoImpl(Context context) {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }

    public void open() throws SQLException {
        db = this.getWritableDatabase();
    }

    public void close() {
        this.close();
    }
    @Override
    public OrderDetails findById(Long aLong) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{
                        COLUMN_ID,
                        COLUMN_ORDERID,
                        COLUMN_PRODUCTID,
                        COLUMN_ORDERDATE,
                        COLUMN_QUANTITY,
                        COLUMN_SIZE,
                        COLUMN_INGREDIENTS,
                        COLUMN_STATUS},
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(aLong)},
                null,
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            final OrderDetails orderDetails = new OrderDetails.Builder()
                    //.orderDate(cursor.getInt(cursor.getColumnIndex(COLUMN_ORDERDATE)))
                    .qty(cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY)))
                    //.size(Size.valueOf(cursor.getColumnIndex(COLUMN_SIZE)))
                    //.ingr(cursor.getString(cursor.getColumnIndex(COLUMN_INGREDIENTS)))
                    .status(cursor.getString(cursor.getColumnIndex(COLUMN_STATUS)))

                    .build();

            return orderDetails;
        } else {
            return null;
        }
    }

    @Override
    public OrderDetails save(OrderDetails entity) {
        open();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, entity.getOrderDetId());
        //values.put(COLUMN_ORDERID, entity.getCandidateId());
        //values.put(COLUMN_PRODUCTID, entity.getFirstname());
        values.put(COLUMN_ORDERDATE, String.valueOf(entity.getOrderDate()));
        values.put(COLUMN_QUANTITY, entity.getQuantity());
        values.put(COLUMN_SIZE, String.valueOf(entity.getSize()));
        values.put(COLUMN_INGREDIENTS, String.valueOf(entity.getIngredients()));
        values.put(COLUMN_STATUS, entity.getStatus());
        long id = db.insertOrThrow(TABLE_NAME, null, values);
        OrderDetails insertedEntity = new OrderDetails.Builder()
                .copy(entity)
                .orderDetid(new Long(id))
                .build();
        return insertedEntity;
    }

    @Override
    public OrderDetails update(OrderDetails entity) {
        open();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, entity.getOrderDetId());
        //values.put(COLUMN_ORDERID, entity.getCandidateId());
        //values.put(COLUMN_PRODUCTID, entity.getFirstname());
        values.put(COLUMN_ORDERDATE, String.valueOf(entity.getOrderDate()));
        values.put(COLUMN_QUANTITY, entity.getQuantity());
        values.put(COLUMN_SIZE, String.valueOf(entity.getSize()));
        values.put(COLUMN_INGREDIENTS, String.valueOf(entity.getIngredients()));
        values.put(COLUMN_STATUS, entity.getStatus());
        db.update(
                TABLE_NAME,
                values,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(entity.getOrderDetId())}
        );
        return entity;
    }

    @Override
    public OrderDetails delete(OrderDetails entity) {
        open();
        db.delete(
                TABLE_NAME,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(entity.getOrderDetId())});
        return entity;
    }

    @Override
    public Set<OrderDetails> findAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Set<OrderDetails> orderDetails = new HashSet<>();
        open();
        Cursor cursor = db.query(TABLE_NAME, null,null,null,null,null,null);
        if (cursor.moveToFirst()) {
            do {
                final OrderDetails orderDetail = new OrderDetails.Builder()
                        //.orderDate(cursor.getInt(cursor.getColumnIndex(COLUMN_ORDERDATE)))
                        .qty(cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY)))
                                //.size(Size.valueOf(cursor.getColumnIndex(COLUMN_SIZE)))
                                //.ingr(cursor.getString(cursor.getColumnIndex(COLUMN_INGREDIENTS)))
                        .status(cursor.getString(cursor.getColumnIndex(COLUMN_STATUS)))

                        .build();
                orderDetails.add(orderDetail);
            } while (cursor.moveToNext());
        }
        return orderDetails;
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
