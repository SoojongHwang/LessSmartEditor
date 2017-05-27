package com.example.kepler.lesssmarteditor.editor.model.component.domain;

import android.net.Uri;

/**
 * Created by Kepler on 2017-05-20.
 */

public class ImageComponent extends BaseComponent {
    private Uri imageUrl;

    public ImageComponent(Type type, Uri imageUrl) {
        super(type);
        this.imageUrl = imageUrl;
    }

    public Uri getImageUrl() {
        return imageUrl;
    }
}
