package com.example.kepler.lesssmarteditor.editor.presenter;

import android.net.Uri;

import com.example.kepler.lesssmarteditor.editor.model.component.domain.BaseComponent;
import com.example.kepler.lesssmarteditor.map.model.Item;

import java.util.List;

/**
 * Created by Kepler on 2017-05-25.
 */

public interface EditorPresenter {
    //from activity
    void selectText();
    void addImage(Uri uri);
    void addMap(Item item);

    //from DB
    void getTitleListFromDatabase();
    void getComponentListFromDatabase(int id);

    //to DB
    void saveComponentListToDatabase(String title, List<BaseComponent> list);
}
