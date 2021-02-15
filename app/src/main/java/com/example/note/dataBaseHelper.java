package com.example.note;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class dataBaseHelper extends SQLiteOpenHelper {
    public static final String NOTES_TABLE = "NOTES_TABLE";
    public static final String COLUMN_NOTE_TITLE = "NOTE_TITLE";
    public static final String COLUMN_NOTE_DESC = "NOTE_DESC";
    public static final String COLUMN_ID = "ID";

    public dataBaseHelper(@Nullable Context context) {
        super(context, "notes.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String statement = "CREATE TABLE " + NOTES_TABLE + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NOTE_TITLE + " TEXT, " + COLUMN_NOTE_DESC + " TEXT)";

        db.execSQL(statement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean addOne(Notes note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NOTE_TITLE,note.getTitle());
        cv.put(COLUMN_NOTE_DESC,note.getDescription());
        long insert = db.insert(NOTES_TABLE, null, cv);
        if(insert == -1){
            return false;
        }
        else {
            return true;
        }
    }
    public boolean deleteOne(Notes note){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + NOTES_TABLE + " WHERE " + COLUMN_ID + " = " + note.getID();
        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst()){
            return true;
        }
        else {
            return false;
        }
    }
    public List<Notes> getEveryone(){
        List<Notes> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + NOTES_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do {
                int noteID = cursor.getInt(0);
                String noteTitle = cursor.getString(1);
                String noteDesc = cursor.getString(2);
                Notes newNote = new Notes(noteTitle,noteDesc,noteID);
                returnList.add(newNote);
            } while (cursor.moveToNext());
        }
        else{

        }
        cursor.close();
        db.close();
        return returnList;
    }
}
