package com.example.kepler.lesssmarteditor.editor.presenter;

import com.example.kepler.lesssmarteditor.editor.model.component.domain.BaseComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.Type;
import com.example.kepler.lesssmarteditor.map.model.Item;

import java.util.List;

/**
 * Created by Kepler on 2017-05-25.
 */

public interface EditorPresenter {
    void notifyToView(String str);
    void enableMenu();
    void disableMenu();

    void addText();
    void addImage(String path);
    void addMap(Item item);

    void loadTitles();
    void loadDocument(int id);

    void saveDocumentsToDatabase(List<BaseComponent> list);
    void deleteDocumentFromDatabase(int id);

    void notifyDocumentChanged();
    void notifyNewDocument();
}
