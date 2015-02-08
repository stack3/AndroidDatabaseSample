package net.stack3.and.dbsample.databasesample.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by EIMEI on 15/02/08.
 */
public class Transaction<T> {
    public static interface Runner<T> {
        public T run(SQLiteDatabase db);
    }

    public T run(Context context, String filename, Runner<T> runner) {
        SQLiteOpenHelper helper = new OpenHelper(context, filename);
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            db.beginTransaction();
            try {
                T ret = runner.run(db);
                db.setTransactionSuccessful();
                return ret;
            } finally {
                db.endTransaction();
            }
        } finally {
            db.close();
        }
    }
}
