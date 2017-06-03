package com.example.kepler.lesssmarteditor.editor.view;

import com.example.kepler.lesssmarteditor.editor.model.component.domain.BaseComponent;
import com.example.kepler.lesssmarteditor.editor.model.database.Title;

import java.util.List;

/**
 * Created by Kepler on 2017-05-25.
 */

public interface EditorView {
    void showToast(String str);
    void enableSelectMenu();
    void disableSelectMenu();

    void addComponentToAdapter(BaseComponent baseComponent);

    void showDocument(List<BaseComponent> document);
    void showTitles(List<Title> titles);
}
