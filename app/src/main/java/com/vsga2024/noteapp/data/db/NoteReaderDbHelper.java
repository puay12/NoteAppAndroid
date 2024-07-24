package com.vsga2024.noteapp.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.HashMap;

public class NoteReaderDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Notes.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + NoteReaderContract.NoteEntry.TABLE_NAME + " (" +
                    NoteReaderContract.NoteEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    NoteReaderContract.NoteEntry.COLUMN_NAME_TITLE + " TEXT," +
                    NoteReaderContract.NoteEntry.COLUMN_NAME_CONTENT + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + NoteReaderContract.NoteEntry.TABLE_NAME;

    private static final String SQL_INSERT_ROW =
            "INSERT INTO" + NoteReaderContract.NoteEntry.TABLE_NAME + " (title, content) "
            + "VALUES (?,?)";

    public NoteReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer) {
        onUpgrade(db, oldVer, newVer);
    }

    public ArrayList<HashMap<String, String>> getAllNotes() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + NoteReaderContract.NoteEntry.TABLE_NAME, null
        );
        ArrayList<HashMap<String, String>> noteLists = new ArrayList<HashMap<String, String>>();

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> newNote = new HashMap<String, String>();

                newNote.put(
                        NoteReaderContract.NoteEntry.COLUMN_NAME_ID,
                        cursor.getString(0)
                );
                newNote.put(
                        NoteReaderContract.NoteEntry.COLUMN_NAME_TITLE,
                        cursor.getString(1)
                );
                newNote.put(
                        NoteReaderContract.NoteEntry.COLUMN_NAME_CONTENT,
                        cursor.getString(2)
                );
                noteLists.add(newNote);
            } while (cursor.moveToNext());
        }

        db.close();
        return noteLists;
    }

    public boolean addNote(String title, String content) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NoteReaderContract.NoteEntry.COLUMN_NAME_TITLE, title);
        values.put(NoteReaderContract.NoteEntry.COLUMN_NAME_CONTENT, content);

        long newNoteId = db.insert(NoteReaderContract.NoteEntry.TABLE_NAME, null, values);

        db.close();
        return newNoteId != -1;
    }

    public boolean updateNote(int id, String title, String content) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NoteReaderContract.NoteEntry.COLUMN_NAME_TITLE, title);
        values.put(NoteReaderContract.NoteEntry.COLUMN_NAME_CONTENT, content);

        String selection = NoteReaderContract.NoteEntry.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { Integer.toString(id) };

        int count = db.update(
                NoteReaderContract.NoteEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        db.close();
        return count > 0;
    }

    public boolean deleteNote(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = NoteReaderContract.NoteEntry.COLUMN_NAME_ID + " = ?";

        String[] selectionArgs = { Integer.toString(id) };

        int deletedRows = db.delete(
                NoteReaderContract.NoteEntry.TABLE_NAME,
                selection,
                selectionArgs
        );

        db.close();
        return deletedRows > 0;
    }
}
