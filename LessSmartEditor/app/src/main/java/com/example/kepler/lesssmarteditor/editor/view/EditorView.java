package com.example.kepler.lesssmarteditor.editor.view;

import com.example.kepler.lesssmarteditor.editor.model.component.domain.BaseComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.ImageComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.MapComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.TextComponent;
import com.example.kepler.lesssmarteditor.editor.model.database.TitleWithId;

import java.util.List;

/**
 * Created by Kepler on 2017-05-25.
 */

public interface EditorView {
    void addTextToAdapter(TextComponent textComponent);
    void addImageToAdapter(ImageComponent imageComponent);
    void addMapToAdapter(MapComponent mapComponent);


    void showComponents(List<BaseComponent> cList);
    void showTitle(List<TitleWithId> tList);
}
