package com.example.kepler.lesssmarteditor.editor.model.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kepler.lesssmarteditor.editor.model.component.domain.BaseComponent;

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

    public void saveToDatabase(int id, String title, List<BaseComponent> list, boolean isNew) {
        String content = mJsonHelper.List2Json(list);

        String query;
        if (isNew) {
            query = "INSERT INTO row VALUES(null, '" + title + "', '" + content + "');";
        } else {
            query = "UPDATE row SET _title = '" + title + "', _content = '" + content + "' WHERE _id = " + id + ";";
        }
        mDb.execSQL(query);
    }

    public List<TitleWithId> getTitleList() {
        String query = "SELECT * FROM row";
        Cursor cursor = mDb.rawQuery(query, null);

        List<TitleWithId> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            TitleWithId twi = new TitleWithId(cursor.getInt(0), cursor.getString(1));
            list.add(twi);
        }

        return list;
    }

    public ContentWithTitle getComponentList(int id) {
        String query = "SELECT * FROM row where _id = " + id + ";";
        Cursor cursor = mDb.rawQuery(query, null);

        cursor.moveToFirst();

        String title = cursor.getString(1);
        String content = cursor.getString(2);
        List<BaseComponent> list = mJsonHelper.Json2List(content);

        if (title.equals("제목없는 글"))
            title = "";
        ContentWithTitle cwt = new ContentWithTitle(title, list);
        return cwt;
    }

    public void deleteFromDatabase(int id) {
        String query = "DELETE FROM row WHERE _id =" + id + ";";
        mDb.execSQL(query);
    }
}