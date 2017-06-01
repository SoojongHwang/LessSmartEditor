package com.example.kepler.lesssmarteditor.editor.presenter;

import com.example.kepler.lesssmarteditor.editor.model.component.domain.BaseComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.Type;
import com.example.kepler.lesssmarteditor.map.model.Item;

import java.util.List;

/**
 * Created by Kepler on 2017-05-25.
 */

public interface EditorPresenter {
    void addText();
    void addImage(String path);
    void addMap(Item item);

    void getTitles();
    void getDocument(int id);

    void saveDocumentsToDatabase(List<BaseComponent> list);
    void deleteDocumentsFromDatabase(int id);
}
