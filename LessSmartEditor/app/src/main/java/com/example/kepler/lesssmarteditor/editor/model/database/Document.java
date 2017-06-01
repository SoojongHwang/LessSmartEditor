package com.example.kepler.lesssmarteditor.editor.model.database;

import com.example.kepler.lesssmarteditor.editor.model.component.domain.BaseComponent;

import java.util.List;

/**
 * Created by Kepler on 2017-05-29.
 */

public class Document {
    public String title;
    public List<BaseComponent> list;

    public Document(String title, List<BaseComponent> list) {
        this.title = title;
        this.list = list;
    }
}
