package net.stack3.and.dbsample.databasesample.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.stack3.and.dbsample.databasesample.model.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EIMEI on 15/02/08.
 */
public class CategoryDAO extends BaseDAO {
    private static final String TABLE_NAME = "categories";

    public static void createTable(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABLE_NAME + " (" +
                        "id              INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name            TEXT NOT NULL" +
                        ");"
        );
    }

    public CategoryDAO(SQLiteDatabase db) {
        super(db);
    }

    public Category findById(long id) {
        Cursor cursor = db.query(TABLE_NAME, null, "id = ?", new String[]{ String.valueOf(id) }, null, null, null);
        Category category = null;
        try {
            if (cursor.moveToNext()) {
                category = categoryFromCursor(cursor);
            }
        } finally {
            cursor.close();
        }
        return category;
    }

    public Category findByName(String name) {
        Cursor cursor = db.query(TABLE_NAME, null, "name = ?", new String[]{ name }, null, null, null);
        Category category = null;
        try {
            if (cursor.moveToNext()) {
                category = categoryFromCursor(cursor);
            }
        } finally {
            cursor.close();
        }
        return category;
    }

    public List<Category> findAll() {
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        List<Category> categoryList = new ArrayList<Category>();
        try {
            while (cursor.moveToNext()) {
                Category category = categoryFromCursor(cursor);
                categoryList.add(category);
            }
        } finally {
            cursor.close();
        }
        return categoryList;
    }

    private Category categoryFromCursor(Cursor cursor) {
        Category category = new Category();
        category.setId(cursor.getLong(cursor.getColumnIndex("id")));
        category.setName(cursor.getString(cursor.getColumnIndex("name")));
        return category;
    }

    public void save(Category category) {
        ContentValues values = new ContentValues();
        values.put("name", category.getName());
        if (category.getId() > 0) {
            db.update(TABLE_NAME, values, "id = ?", new String[]{String.valueOf(category.getId())});
        } else {
            long id = db.insert(TABLE_NAME, null, values);
            category.setId(id);
        }
    }

    public void deleteAll() {
        db.delete(TABLE_NAME, null, null);
    }
}
