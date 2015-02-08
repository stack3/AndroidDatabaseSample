package net.stack3.and.dbsample.databasesample.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Hideaki on 15/02/08.
 */
public class BaseDAO {
    protected SQLiteDatabase db;

    public BaseDAO(SQLiteDatabase db) {
        this.db = db;
    }
}
