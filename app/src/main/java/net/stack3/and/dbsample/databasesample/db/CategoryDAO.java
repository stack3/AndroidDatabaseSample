package net.stack3.and.dbsample.databasesample.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by EIMEI on 15/02/08.
 */
public class CategoryDAO {
    private static final String TABLE_NAME = "categories";

    public static void createTable(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABLE_NAME + " (" +
                        "id              INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name            TEXT NOT NULL" +
                        ");"
        );
    }
}
