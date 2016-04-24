package com.mouks.rosie.mycoffeeshop.repositories.Impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.DatePicker;

import com.mouks.rosie.mycoffeeshop.conf.DBConstants;
import com.mouks.rosie.mycoffeeshop.domain.GoodsOrder;
import com.mouks.rosie.mycoffeeshop.repositories.GoodsOrderRepository;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Rosie on 2016/04/23.
 */
public class GoodsOrderRepoImpl extends SQLiteOpenHelper implements GoodsOrderRepository{

    public static final String TABLE_NAME = "candidate";
    private SQLiteDatabase db;

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_SUPPLIER = "supplierId";
    public static final String COLUMN_QUANTITY = "quantity_ordered";
    public static final String COLUMN_NAME = "product_name";
    public static final String COLUMN_ORDERDATE = "order_date";


    // Database creation sql statement
    private static final String DATABASE_CREATE = " CREATE TABLE "
            + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_SUPPLIER + " INTEGER FOREIGN KEY UNIQUE  , "
            + COLUMN_NAME+ " TEXT NOT NULL , "
            + COLUMN_QUANTITY + " INTEGER NOT NULL , "
            + COLUMN_ORDERDATE + " TEXT NOT NULL );";


    public GoodsOrderRepoImpl(Context context) {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }

    public void open() throws SQLException {
        db = this.getWritableDatabase();
    }

    public void close() {
        this.close();
    }

    @Override
    public GoodsOrder findById(Long aLong) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{
                        COLUMN_ID,
                        COLUMN_SUPPLIER,
                        COLUMN_NAME,
                        COLUMN_QUANTITY,
                        COLUMN_ORDERDATE,},
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(aLong)},
                null,
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            final GoodsOrder goodsOrder = new GoodsOrder.Builder()
                    .id(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)))
                    .name(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)))
                    .qty(cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY)))
                    //.date(parse(cursor.getString(cursor.getColumnIndex(COLUMN_ORDERDATE))))
                    .build();
            return goodsOrder;
        } else {
            return null;
        }
    }

    @Override
    public GoodsOrder save(GoodsOrder entity) {
        open();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, entity.getId());
        //values.put(COLUMN_SUPPLIER, entity.getCandidateId());
        values.put(COLUMN_NAME, entity.getName());
        values.put(COLUMN_QUANTITY, entity.getQtyOrdered());
        //values.put(COLUMN_ORDERDATE,entity.getDate());

        long id = db.insertOrThrow(TABLE_NAME, null, values);
        GoodsOrder insertedEntity = new GoodsOrder.Builder()
                .copy(entity)
                .id(new Long(id))
                .build();
        return insertedEntity;
    }

    @Override
    public GoodsOrder update(GoodsOrder entity) {
        open();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, entity.getId());
        //values.put(COLUMN_SUPPLIER, entity.getCandidateId());
        values.put(COLUMN_NAME, entity.getName());
        values.put(COLUMN_QUANTITY, entity.getQtyOrdered());
        //values.put(COLUMN_ORDERDATE,entity.getDate());
        db.update(
                TABLE_NAME,
                values,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(entity.getId())}
        );
        return entity;
    }

    @Override
    public GoodsOrder delete(GoodsOrder entity) {
        open();
        db.delete(
                TABLE_NAME,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(entity.getId())});
        return entity;
    }

    @Override
    public Set<GoodsOrder> findAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Set<GoodsOrder> goodsOrders = new HashSet<>();
        open();
        Cursor cursor = db.query(TABLE_NAME, null,null,null,null,null,null);
        if (cursor.moveToFirst()) {
            do {
                final GoodsOrder good = new GoodsOrder.Builder()
                        .id(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)))
                        .name(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)))
                        .qty(cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY)))
                                //.date(parse(cursor.getString(cursor.getColumnIndex(COLUMN_ORDERDATE))))
                        .build();
                goodsOrders.add(good);
            } while (cursor.moveToNext());
        }
        return goodsOrders;
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
