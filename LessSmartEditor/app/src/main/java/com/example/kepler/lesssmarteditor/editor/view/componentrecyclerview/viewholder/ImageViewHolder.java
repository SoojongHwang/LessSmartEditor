package com.example.kepler.lesssmarteditor.editor.view.componentrecyclerview.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.kepler.lesssmarteditor.R;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.ImageComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.Type;
import com.example.kepler.lesssmarteditor.editor.view.componentrecyclerview.ComponentViewHolder;


/**
 * Created by Kepler on 2017-05-20.
 */

public class ImageViewHolder extends ComponentViewHolder<ImageComponent> {
    private ImageView mImageView;
    private Context mContext;

    public ImageViewHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        mImageView = (ImageView)itemView.findViewById(R.id.view_image_imageView);
    }

    @Override
    public void bindView(ImageComponent imageComponent) {
        Glide.with(mContext).load(imageComponent.getImageUrl()).error(R.mipmap.ic_launcher)
                .into(mImageView);
    }
}
