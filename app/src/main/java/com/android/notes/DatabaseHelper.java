package com.android.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "notes_db";

    private static int dbCorruptFlag = 0;

    public static int getDbCorruptFlag() {
        return dbCorruptFlag;
    }

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION, new DatabaseErrorHandler() {
            @Override
            public void onCorruption(SQLiteDatabase dbObj) {
                dbCorruptFlag = 1;
            }
        });


    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(NotesDB.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NotesDB.TABLE_NAME);

        onCreate(db);
    }

    public long insertNote(String note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NotesDB.COLUMN_NOTE, note);

        long id = db.insert(NotesDB.TABLE_NAME, null, values);

        db.close();

        return id;
    }

    public NotesDB getNote(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(NotesDB.TABLE_NAME,
                new String[]{NotesDB.COLUMN_ID, NotesDB.COLUMN_NOTE, NotesDB.COLUMN_TIMESTAMP},
                NotesDB.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        NotesDB note = new NotesDB(
                cursor.getInt(cursor.getColumnIndex(NotesDB.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(NotesDB.COLUMN_NOTE)),
                cursor.getString(cursor.getColumnIndex(NotesDB.COLUMN_TIMESTAMP)));

        cursor.close();

        return note;
    }

    public List<NotesDB> getAllNotes() {
        List<NotesDB> notes = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + NotesDB.TABLE_NAME + " ORDER BY " +
                NotesDB.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                NotesDB note = new NotesDB();
                note.setId(cursor.getInt(cursor.getColumnIndex(NotesDB.COLUMN_ID)));
                note.setNote(cursor.getString(cursor.getColumnIndex(NotesDB.COLUMN_NOTE)));
                note.setTimestamp(cursor.getString(cursor.getColumnIndex(NotesDB.COLUMN_TIMESTAMP)));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        db.close();
        return notes;
    }

    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + NotesDB.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int updateNote(NotesDB note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NotesDB.COLUMN_NOTE, note.getNote());
        return db.update(NotesDB.TABLE_NAME, values, NotesDB.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

    public void deleteNote(NotesDB note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(NotesDB.TABLE_NAME, NotesDB.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }
    
    
}
