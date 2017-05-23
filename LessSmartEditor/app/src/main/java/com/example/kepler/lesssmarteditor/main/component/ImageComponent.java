package com.example.kepler.lesssmarteditor.main.component;

/**
 * Created by Kepler on 2017-05-20.
 */

public class ImageComponent extends BaseComponent {
    private int imageUrl;

    public ImageComponent(int index, Type type, int imageUrl) {
        super(index, type);
        this.imageUrl = imageUrl;
    }

    public int getImageUrl() {
        return imageUrl;
    }
}
