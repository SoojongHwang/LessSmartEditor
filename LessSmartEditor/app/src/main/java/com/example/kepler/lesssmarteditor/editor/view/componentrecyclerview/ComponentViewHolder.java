package com.example.kepler.lesssmarteditor.editor.view.componentrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.kepler.lesssmarteditor.editor.model.component.domain.BaseComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.Type;

/**
 * Created by Kepler on 2017-05-25.
 */

public abstract class ComponentViewHolder<T extends BaseComponent> extends RecyclerView.ViewHolder{

    public ComponentViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindView(T item);
}
