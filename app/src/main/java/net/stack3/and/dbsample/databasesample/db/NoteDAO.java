package net.stack3.and.dbsample.databasesample.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by EIMEI on 15/02/08.
 */
public class NoteDAO {
    private static final String TABLE_NAME = "notes";

    public static void createTable(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABLE_NAME + " (" +
                        "id              INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "title           TEXT NOT NULL," +
                        "comments        TEXT NOT NULL," +
                        "category_id     INTEGER" +
                        ");"
        );
    }
}
