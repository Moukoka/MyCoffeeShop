package com.mouks.rosie.mycoffeeshop.repositories.Impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mouks.rosie.mycoffeeshop.conf.DBConstants;
import com.mouks.rosie.mycoffeeshop.domain.Product;
import com.mouks.rosie.mycoffeeshop.domain.Size;
import com.mouks.rosie.mycoffeeshop.repositories.ProductRepository;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Rosie on 2016/04/23.
 */
public class ProductRepoImpl extends SQLiteOpenHelper implements ProductRepository {

    public static final String TABLE_NAME = "product";
    private SQLiteDatabase db;

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CATEGORYID = "categoryId";
    public static final String COLUMN_PRODUCTNAME = "product_name";
    public static final String COLUMN_DESCRIPTION = "product_description";
    public static final String COLUMN_UNITPRICE = "unit_price";
    public static final String COLUMN_QUANTITY = "quantity_in_stock";
    public static final String COLUMN_SIZE = "size";
    public static final String COLUMN_INGREDIENTS = "ingredients";
    public static final String COLUMN_SUPPLIER = "supplierId";


    // Database creation sql statement
    private static final String DATABASE_CREATE = " CREATE TABLE "
            + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_CATEGORYID + " TEXT UNIQUE FOREIGN KEY NOT NULL , "
            + COLUMN_SUPPLIER + " TEXT UNIQUE FOREIGN KEY NOT NULL , "
            + COLUMN_PRODUCTNAME + " TEXT NOT NULL , "
            + COLUMN_DESCRIPTION + " TEXT NOT NULL , "
            + COLUMN_UNITPRICE + " INTEGER , "
            + COLUMN_QUANTITY + " INTEGER , "
            + COLUMN_SIZE + " TEXT  , "
            + COLUMN_INGREDIENTS+ " TEXT  );";


    public ProductRepoImpl(Context context) {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }

    public void open() throws SQLException {
        db = this.getWritableDatabase();
    }

    public void close() {
        this.close();
    }
    @Override
    public Product findById(Long aLong) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{
                        COLUMN_ID,

                                COLUMN_CATEGORYID,
                                COLUMN_SUPPLIER ,
                                COLUMN_PRODUCTNAME,
                                COLUMN_DESCRIPTION,
                                COLUMN_UNITPRICE,
                                COLUMN_QUANTITY,
                                COLUMN_SIZE ,
                                COLUMN_INGREDIENTS},
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(aLong)},
                null,
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            final Product product = new Product.Builder()
                    .productid(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)))
                    .prodName(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCTNAME)))
                    .descr(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)))
                    .unitP(cursor.getDouble(cursor.getColumnIndex(COLUMN_UNITPRICE)))
                    .avQty(cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY)))
                    //.size(Size.valueOf(cursor.getColumnIndex(COLUMN_SIZE)))
                    //.ingr(cursor.getString(cursor.getColumnIndex(COLUMN_INGREDIENTS)))
                    .build();

            return product;
        } else {
            return null;
        }
    }

    @Override
    public Product save(Product entity) {
        open();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, entity.getProductId());
        values.put(COLUMN_PRODUCTNAME, entity.getProductName());
        values.put(COLUMN_DESCRIPTION, entity.getDescription());
        values.put(COLUMN_UNITPRICE, entity.getUnitPrice());
        values.put(COLUMN_QUANTITY, entity.getAvailableQty());
        values.put(COLUMN_SIZE, String.valueOf(entity.getSize()));
        values.put(COLUMN_INGREDIENTS, String.valueOf(entity.getIngredientsList()));
        long id = db.insertOrThrow(TABLE_NAME, null, values);
        Product insertedEntity = new Product.Builder()
                .copy(entity)
                .productid(new Long(id))
                .build();
        return insertedEntity;
    }

    @Override
    public Product update(Product entity) {
        open();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, entity.getProductId());
        values.put(COLUMN_PRODUCTNAME, entity.getProductName());
        values.put(COLUMN_DESCRIPTION, entity.getDescription());
        values.put(COLUMN_UNITPRICE, entity.getUnitPrice());
        values.put(COLUMN_QUANTITY, entity.getAvailableQty());
        values.put(COLUMN_SIZE, String.valueOf(entity.getSize()));
        values.put(COLUMN_INGREDIENTS, String.valueOf(entity.getIngredientsList()));
        db.update(
                TABLE_NAME,
                values,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(entity.getProductId())}
        );
        return entity;
    }

    @Override
    public Product delete(Product entity) {
        open();
        db.delete(
                TABLE_NAME,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(entity.getProductId())});
        return entity;
    }

    @Override
    public Set<Product> findAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Set<Product> products = new HashSet<>();
        open();
        Cursor cursor = db.query(TABLE_NAME, null,null,null,null,null,null);
        if (cursor.moveToFirst()) {
            do {
                final Product product = new Product.Builder()
                        .productid(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)))
                        .prodName(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCTNAME)))
                        .descr(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)))
                        .unitP(cursor.getDouble(cursor.getColumnIndex(COLUMN_UNITPRICE)))
                        .avQty(cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY)))
                                //.size(Size.valueOf(cursor.getColumnIndex(COLUMN_SIZE)))
                                //.ingr(cursor.getString(cursor.getColumnIndex(COLUMN_INGREDIENTS)))
                        .build();
                products.add(product);
            } while (cursor.moveToNext());
        }
        return products;
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
