package com.example.kepler.lesssmarteditor.editor.view.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Kepler on 2017-05-25.
 */

public abstract class ComponentViewHolder<T> extends RecyclerView.ViewHolder{

    public ComponentViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindView(T item);
}
