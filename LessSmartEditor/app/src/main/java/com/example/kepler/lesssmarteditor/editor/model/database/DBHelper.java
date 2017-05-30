package com.example.kepler.lesssmarteditor.editor.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Kepler on 2017-05-26.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "editor";
    public static final String TABLE_NAME = "row";

    public static final String C_ID = "_id";
    public static final String TITLE = "_title";
    public static final String CONTENT = "_content";
    public static final String TIMESTAMP = "_timestamp";
    public static final int VERSION = 2;

    private final String createDB = "create table if not exists " + TABLE_NAME + " ("
            + C_ID + " integer primary key autoincrement, "
            + TITLE + " text, "
            + CONTENT + " text, "
            + TIMESTAMP + " date); ";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createDB);
        Log.d("Database/onCreate##",createDB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + TABLE_NAME);
    }
}
