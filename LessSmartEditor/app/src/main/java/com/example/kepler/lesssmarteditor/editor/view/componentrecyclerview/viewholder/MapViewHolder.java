package com.example.kepler.lesssmarteditor.editor.view.componentrecyclerview.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kepler.lesssmarteditor.R;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.MapComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.Type;
import com.example.kepler.lesssmarteditor.editor.view.componentrecyclerview.ComponentViewHolder;

/**
 * Created by Kepler on 2017-05-20.
 */

public class MapViewHolder extends ComponentViewHolder<MapComponent> {
    private ImageView mImageView;
    private TextView tv_name;
    private TextView tv_address;
    private Context mContext;


    public MapViewHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        mImageView = (ImageView) itemView.findViewById(R.id.view_map_iv_imageView);
        tv_name = (TextView) itemView.findViewById(R.id.view_map_tv_title);
        tv_address = (TextView) itemView.findViewById(R.id.view_map_tv_address);
    }

    @Override
    public void bindView(MapComponent mapComponent) {
        tv_name.setText(mapComponent.getName());
        tv_address.setText(mapComponent.getAddress());
        Glide.with(mContext).load(mapComponent.getUrl()).placeholder(R.drawable.d1).error(R.mipmap.ic_launcher).into(mImageView);
    }
}
