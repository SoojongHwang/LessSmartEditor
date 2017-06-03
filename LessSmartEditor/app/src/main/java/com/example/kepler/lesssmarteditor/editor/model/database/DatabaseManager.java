package com.example.kepler.lesssmarteditor.editor.model.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kepler.lesssmarteditor.editor.model.EditorModel;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.BaseComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.TitleComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kepler on 2017-05-25.
 */

public class DatabaseManager {
    private SQLiteDatabase mDb;
    private DBHelper mDbHelper;
    private EditorModel mEditorModel;

    public DatabaseManager(Context context, EditorModel model) {
        mDbHelper = new DBHelper(context);
        mDb = mDbHelper.getWritableDatabase();
        this.mEditorModel = model;
    }

    public void saveDocument(int docId, List<BaseComponent> list){
        String title = ((TitleComponent) (list.get(0))).getTitle();
        String content = JsonHelper.List2Json(list);

        if(title.length() ==0 ){
            title = "(제목 없는 글)";
        }

        String query;
        if(docId == -1){
            query = "INSERT INTO row VALUES(null, '" + title + "', '" + content + "', DATETIME('now'));";
        }
        else{
            query = "UPDATE row SET _title = '" + title + "', _content = '" + content + "', _timestamp = DATETIME('now') WHERE _id = " + docId + ";";
        }
        mDb.execSQL(query);

        if(docId==-1) {
            mEditorModel.setDocumentId(getLastDocumentId());
        }
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
        List<BaseComponent> list = JsonHelper.Json2List(content);

        return list;
    }

    public void deleteFromDatabase(int id) {
        String query = "DELETE FROM row WHERE _id =" + id + ";";
        mDb.execSQL(query);
    }

    private int getLastDocumentId() {
        Cursor cursor = mDb.rawQuery("SELECT _id FROM row", null);
        cursor.moveToLast();
        return cursor.getInt(0);
    }
}