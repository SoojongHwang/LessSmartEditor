package com.example.kepler.lesssmarteditor.editor.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Kepler on 2017-05-25.
 */

public class DBManager {
    SQLiteDatabase db;
    DBHelper mDbHelper;

    public DBManager(Context context) {
        mDbHelper = new DBHelper(context);
        db = mDbHelper.getWritableDatabase();
    }


}
