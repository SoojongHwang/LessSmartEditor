package com.example.kepler.lesssmarteditor.editor.model.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kepler.lesssmarteditor.editor.model.component.domain.BaseComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.TitleComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kepler on 2017-05-25.
 */

public class DatabaseManager {
    private SQLiteDatabase mDb;
    private JsonHelper mJsonHelper;
    private DBHelper mDbHelper;

    public DatabaseManager(Context context) {
        mDbHelper = new DBHelper(context);
        mDb = mDbHelper.getWritableDatabase();
        mJsonHelper = new JsonHelper();
    }

    public int saveToDatabase(List<BaseComponent> list) {
        String title = ((TitleComponent)(list.get(0))).getTitle();
        String content = mJsonHelper.List2Json(list);

        String query;
            query = "INSERT INTO row VALUES(null, '" + title + "', '" + content + "', DATETIME('now'));";
//            query = "UPDATE row SET _title = '" + "hi2" + "', _content = '" + content + "', _timestamp = DATETIME('now') WHERE _id = " + id + ";";
        mDb.execSQL(query);

        Cursor cursor = mDb.rawQuery("SELECT _id FROM row",null);
        cursor.moveToLast();
        int lastNum = cursor.getInt(0);
        Log.d("database", "inserted at "+lastNum);
        return lastNum;
    }

    public List<Title> getTitleList() {
        String query = "SELECT * FROM row order by DATETIME(_timestamp) DESC";
        Cursor cursor = mDb.rawQuery(query, null);

        List<Title> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            Title twi = new Title(cursor.getInt(0), cursor.getString(1));
            list.add(twi);
        }

        return list;
    }

    public List<BaseComponent> getDocumentFromDatabase(int id) {
        String query = "SELECT * FROM row where _id = " + id + ";";
        Cursor cursor = mDb.rawQuery(query, null);

        cursor.moveToFirst();

        String content = cursor.getString(2);
        List<BaseComponent> list = mJsonHelper.Json2List(content);

        return list;
    }

    public void deleteFromDatabase(int id) {
        String query = "DELETE FROM row WHERE _id =" + id + ";";
        mDb.execSQL(query);
    }
}