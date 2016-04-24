package com.mouks.rosie.mycoffeeshop.repositories.Impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mouks.rosie.mycoffeeshop.conf.DBConstants;
import com.mouks.rosie.mycoffeeshop.domain.ProductCategory;
import com.mouks.rosie.mycoffeeshop.repositories.ProductCategoryRepository;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Rosie on 2016/04/23.
 */
public class ProductCategoryRepoImpl extends SQLiteOpenHelper implements ProductCategoryRepository {

    public static final String TABLE_NAME = "category";
    private SQLiteDatabase db;

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "category_name";
    public static final String COLUMN_DESCRIPTION = "category_description";


    // Database creation sql statement
    private static final String DATABASE_CREATE = " CREATE TABLE "
            + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT NOT NULL , "
            + COLUMN_DESCRIPTION + " TEXT NOT NULL );";


    public ProductCategoryRepoImpl(Context context) {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }

    public void open() throws SQLException {
        db = this.getWritableDatabase();
    }

    public void close() {
        this.close();
    }

    @Override
    public ProductCategory findById(Long aLong) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{
                        COLUMN_ID,
                        COLUMN_NAME,
                        COLUMN_DESCRIPTION},
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(aLong)},
                null,
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            final ProductCategory category = new ProductCategory.Builder()
                    .catid(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)))
                    .catName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)))
                    .catDescr(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)))
                    .build();

            return category;
        } else {
            return null;
        }
    }

    @Override
    public ProductCategory save(ProductCategory entity) {
        open();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, entity.getCatId());
        values.put(COLUMN_NAME, entity.getCatname());
        values.put(COLUMN_DESCRIPTION, entity.getCatdescription());

        long id = db.insertOrThrow(TABLE_NAME, null, values);
        ProductCategory insertedEntity = new ProductCategory.Builder()
                .copy(entity)
                .catid(new Long(id))
                .build();
        return insertedEntity;
    }

    @Override
    public ProductCategory update(ProductCategory entity) {
        open();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, entity.getCatId());
        values.put(COLUMN_NAME, entity.getCatname());
        values.put(COLUMN_DESCRIPTION, entity.getCatdescription());
        db.update(
                TABLE_NAME,
                values,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(entity.getCatId())}
        );
        return entity;
    }

    @Override
    public ProductCategory delete(ProductCategory entity) {
        open();
        db.delete(
                TABLE_NAME,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(entity.getCatId())});
        return entity;
    }

    @Override
    public Set<ProductCategory> findAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Set<ProductCategory> productCategories = new HashSet<>();
        open();
        Cursor cursor = db.query(TABLE_NAME, null,null,null,null,null,null);
        if (cursor.moveToFirst()) {
            do {
                final ProductCategory productCategory = new ProductCategory.Builder()
                        .catid(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)))
                        .catName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)))
                        .catDescr(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)))
                        .build();
                productCategories.add(productCategory);
            } while (cursor.moveToNext());
        }
        return productCategories;
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
