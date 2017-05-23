package com.example.kepler.lesssmarteditor.component;

/**
 * Created by Kepler on 2017-05-20.
 */

public class ImageComponent extends BaseComponent {
    private int imageUrl;

    public ImageComponent(Type type, int imageUrl) {
        super(type);
        this.imageUrl = imageUrl;
    }

    public int getImageUrl() {
        return imageUrl;
    }
}
