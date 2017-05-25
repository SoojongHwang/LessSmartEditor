package com.example.kepler.lesssmarteditor.editor.presenter;

import android.net.Uri;

import com.example.kepler.lesssmarteditor.map.model.Item;

/**
 * Created by Kepler on 2017-05-25.
 */

public interface EditorPresenter {
    //from activity
    void selectText();
    void addImage(Uri uri);
    void addMap(Item item);
}
