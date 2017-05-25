package com.example.kepler.lesssmarteditor.editor.view;

import com.example.kepler.lesssmarteditor.editor.model.domain.ImageComponent;
import com.example.kepler.lesssmarteditor.editor.model.domain.MapComponent;
import com.example.kepler.lesssmarteditor.editor.model.domain.TextComponent;

/**
 * Created by Kepler on 2017-05-25.
 */

public interface EditorView {
    void addTextToAdapter(TextComponent textComponent);
    void addImageToAdapter(ImageComponent imageComponent);
    void addMapToAdapter(MapComponent mapComponent);
}
