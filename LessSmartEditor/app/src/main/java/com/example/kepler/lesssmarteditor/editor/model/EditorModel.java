package com.example.kepler.lesssmarteditor.editor.model;

import com.example.kepler.lesssmarteditor.editor.model.component.domain.BaseComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.ImageComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.MapComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.TextComponent;
import com.example.kepler.lesssmarteditor.editor.model.database.Title;
import com.example.kepler.lesssmarteditor.map.model.Item;

import java.util.List;

/**
 * Created by Kepler on 2017-06-01.
 */

public interface EditorModel {
    TextComponent getTextComponent();
    ImageComponent getImageComponent(String path);
    MapComponent getMapComponent(Item item);

    List<Title> getTitles();
    List<BaseComponent> getDocument(int docId);

    void updateDocumentState();
    void updateDocumentForNew();

    void saveDocument(List<BaseComponent> list);
    void deleteDocument();

    void setDocumentId(int docId);
}
