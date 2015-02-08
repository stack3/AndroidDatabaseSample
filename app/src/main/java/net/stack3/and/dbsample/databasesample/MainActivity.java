package net.stack3.and.dbsample.databasesample;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;

import net.stack3.and.dbsample.databasesample.db.CategoryDAO;
import net.stack3.and.dbsample.databasesample.db.NoteDAO;
import net.stack3.and.dbsample.databasesample.db.Transaction;
import net.stack3.and.dbsample.databasesample.model.Category;
import net.stack3.and.dbsample.databasesample.model.Note;

import java.util.List;


public class MainActivity extends ActionBarActivity {
    private static final String DATABASE_FILENAME = "application.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        run();
    }

    private void run() {
        deleteAll();
        write();
        read();
    }

    private void deleteAll() {
        Transaction<Boolean> tx = new Transaction<>();
        tx.run(this, DATABASE_FILENAME, new Transaction.Runner<Boolean>() {
            @Override
            public Boolean run(SQLiteDatabase db) {
                NoteDAO noteDAO = new NoteDAO(db);
                CategoryDAO categoryDAO = new CategoryDAO(db);

                noteDAO.deleteAll();
                categoryDAO.deleteAll();

                return true;
            }
        });
    }

    private void write() {
        Transaction<Boolean> tx = new Transaction<>();
        tx.run(this, DATABASE_FILENAME, new Transaction.Runner<Boolean>() {
            @Override
            public Boolean run(SQLiteDatabase db) {
                CategoryDAO categoryDAO = new CategoryDAO(db);
                NoteDAO noteDAO = new NoteDAO(db);

                //
                // Save categories.
                //
                Category categoryInternet = new Category();
                categoryInternet.setName("Internet");
                categoryDAO.save(categoryInternet);

                Category categoryFood = new Category();
                categoryFood.setName("Food");
                categoryDAO.save(categoryFood);

                Category categoryLifestyle = new Category();
                categoryLifestyle.setName("Lifestyle");
                categoryDAO.save(categoryLifestyle);
                //
                // Save notes.
                //
                Note note;

                note = new Note();
                note.setTitle("Review Nexus 7 2015.");
                note.setComments("Comments01");
                note.setCategory(categoryInternet);
                noteDAO.save(note);

                note = new Note();
                note.setTitle("Review iPhone 6 Plus.");
                note.setComments("Comments02");
                note.setCategory(categoryInternet);
                noteDAO.save(note);

                note = new Note();
                note.setTitle("About Wine.");
                note.setComments("Comments03");
                note.setCategory(categoryFood);
                noteDAO.save(note);

                return true;
            }
        });
    }

    private void read() {
        Transaction<Boolean> tx = new Transaction<>();
        tx.run(this, DATABASE_FILENAME, new Transaction.Runner<Boolean>() {
            @Override
            public Boolean run(SQLiteDatabase db) {
                CategoryDAO categoryDAO = new CategoryDAO(db);
                NoteDAO noteDAO = new NoteDAO(db);
                StringBuffer log = new StringBuffer();
                //
                // Read categories.
                //
                List<Category> categoryList = categoryDAO.findAll();
                log.append("=== CategoryDAO#findAll ===\n");
                for (Category category : categoryList) {
                    log.append("name:" + category.getName() + "\n");
                }

                Category category = categoryDAO.findByName("Food");
                log.append("=== CategoryDAO#findByName ===\n");
                log.append("name:" + category.getName() + "\n");
                //
                // Read notes.
                //
                List<Note> noteList = noteDAO.findAll();
                log.append("=== NoteDAO#findAll ===\n");
                for (Note note : noteList) {
                    log.append("title:" + note.getTitle() + "\n");
                    log.append("comments:" + note.getComments() + "\n");
                    category = note.getCategory();
                    if (category != null) {
                        log.append("category:" + category.getName() + "\n");
                    }
                    log.append("\n");
                }

                Note note = noteDAO.findByTitle("Review iPhone 6 Plus.");
                log.append("=== NoteDAO#findByName ===\n");
                log.append("title:" + note.getTitle() + "\n");
                log.append("comments:" + note.getComments() + "\n");
                category = note.getCategory();
                if (category != null) {
                    log.append("category:" + category.getName() + "\n");
                }

                TextView logView = (TextView)findViewById(R.id.log_view);
                logView.setText(log);
                return true;
            }
        });
    }
}
