package com.example.kepler.lesssmarteditor.editor.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kepler on 2017-05-26.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "data2";
    public static final String TABLE_NAME = "comments_table";
    public static final String C_ID = "_id";
    public static final String TITLE = "title";
    public static final String TYPE = "type";
    public static final String DETAIL = "description";
    public static final String TIME = "time";
    public static final String DATE = "date";
    public static final int VERSION = 2;

    private final String createDB = "create table if not exists " + TABLE_NAME + " ( "
            + C_ID + " integer primary key autoincrement, "
            + TITLE + " text, "
            + DETAIL + " text, "
            + TYPE + " text, "
            + TIME + " text, "
            + DATE + " text)";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createDB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + TABLE_NAME);
    }
}
