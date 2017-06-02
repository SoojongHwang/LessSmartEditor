package com.example.kepler.lesssmarteditor.editor.model.component.domain;

/**
 * Created by Kepler on 2017-06-02.
 */

public class TitleComponent extends BaseComponent {
    private String title;
    private String imageUri;

    public TitleComponent(Type type, String title) {
        super(type);
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
