package com.example.kepler.lesssmarteditor.editor.recyclerview.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.kepler.lesssmarteditor.R;
import com.example.kepler.lesssmarteditor.component.ImageComponent;
import com.example.kepler.lesssmarteditor.editor.recyclerview.ComponentViewHolder;

/**
 * Created by Kepler on 2017-05-20.
 */

public class ImageViewHolder extends ComponentViewHolder<ImageComponent> {
    private ImageView mImageView;

    public static ImageViewHolder getInstance(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_image, parent, false);
        return new ImageViewHolder(view);
    }

    private ImageViewHolder(View itemView) {
        super(itemView);
        mImageView = (ImageView)itemView.findViewById(R.id.view_image_imageView);
    }

    @Override
    public void bindView(ImageComponent imageComponent) {
        mImageView.setImageResource(imageComponent.getImageUrl());
    }
}
