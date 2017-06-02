package com.example.kepler.lesssmarteditor.editor.model.component.domain;

/**
 * Created by Kepler on 2017-05-20.
 */

public class ImageComponent extends BaseComponent {
    private String filePath;

    public ImageComponent(Type type, String imageUrl) {
        super(type);
        this.filePath = imageUrl;
    }

    public String getImageUrl() {
        return filePath;
    }
}
