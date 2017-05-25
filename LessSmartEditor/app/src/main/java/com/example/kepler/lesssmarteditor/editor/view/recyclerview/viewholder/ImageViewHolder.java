package com.example.kepler.lesssmarteditor.editor.view.recyclerview.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.kepler.lesssmarteditor.R;
import com.example.kepler.lesssmarteditor.editor.model.domain.ImageComponent;
import com.example.kepler.lesssmarteditor.editor.view.recyclerview.ComponentViewHolder;


/**
 * Created by Kepler on 2017-05-20.
 */

public class ImageViewHolder extends ComponentViewHolder<ImageComponent> {
    private ImageView mImageView;
    private Context mContext;
    String url = "https://openapi.naver.com/v1/map/staticmap.bin?clientId=hOBAjjmz9dUkwoGrp6pS&url=http://naver.com&crs=EPSG:4326&center=127.1052133,37.3595316&level=11&w=600&h=600&&baselayer=default&level=11&markers=127.1052133,37.3595316";

    public static ImageViewHolder getInstance(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_image, parent, false);
        return new ImageViewHolder(view);
    }

    private ImageViewHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        mImageView = (ImageView)itemView.findViewById(R.id.view_image_imageView);
    }

    @Override
    public void bindView(ImageComponent imageComponent) {
//        mImageView.setImageURI(imageComponent.getImageUrl());
        Glide.with(mContext).load(imageComponent.getImageUrl()).into(mImageView);
    }
}
