package net.stack3.and.dbsample.databasesample.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.stack3.and.dbsample.databasesample.model.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EIMEI on 15/02/08.
 */
public class NoteDAO extends BaseDAO {
    private static final String TABLE_NAME = "notes";

    private CategoryDAO categoryDAO;

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

    public NoteDAO(SQLiteDatabase db) {
        super(db);
        categoryDAO = new CategoryDAO(db);
    }

    public Note findByTitle(String title) {
        Cursor cursor = db.query(TABLE_NAME, null, "title = ?", new String[]{ title }, null, null, null);
        Note note = null;
        try {
            if (cursor.moveToNext()) {
                note = noteFromCursor(cursor);
            }
        } finally {
            cursor.close();
        }
        return note;
    }

    public List<Note> findAll() {
        CategoryDAO categoryDAO = new CategoryDAO(db);
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        List<Note> noteList = new ArrayList<Note>();
        try {
            while (cursor.moveToNext()) {
                Note note = noteFromCursor(cursor);
                noteList.add(note);
            }
        } finally {
            cursor.close();
        }
        return noteList;
    }

    private Note noteFromCursor(Cursor cursor) {
        Note note = new Note();
        note.setId(cursor.getLong(cursor.getColumnIndex("id")));
        note.setTitle(cursor.getString(cursor.getColumnIndex("title")));
        note.setComments(cursor.getString(cursor.getColumnIndex("comments")));

        long categoryId = cursor.getLong(cursor.getColumnIndex("category_id"));
        if (categoryId > 0) {
            note.setCategory(categoryDAO.findById(categoryId));
        }
        return note;
    }

    public void save(Note note) {
        ContentValues values = new ContentValues();
        values.put("title", note.getTitle());
        values.put("comments", note.getComments());
        if (note.getCategory() != null) {
            values.put("category_id", note.getCategory().getId());
        } else {
            values.put("category_id", (Long)null);
        }
        if (note.getId() > 0) {
            db.update(TABLE_NAME, values, "id = ?", new String[]{String.valueOf(note.getId())});
        } else {
            long id = db.insert(TABLE_NAME, null, values);
            note.setId(id);
        }
    }

    public void deleteAll() {
        db.delete(TABLE_NAME, null, null);
    }
}
