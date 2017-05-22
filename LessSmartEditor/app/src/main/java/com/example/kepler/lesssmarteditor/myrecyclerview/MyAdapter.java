package com.example.kepler.lesssmarteditor.myrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewGroup;

import com.example.kepler.lesssmarteditor.component.BaseComponent;
import com.example.kepler.lesssmarteditor.component.ImageComponent;
import com.example.kepler.lesssmarteditor.component.TextComponent;
import com.example.kepler.lesssmarteditor.component.Type;
import com.example.kepler.lesssmarteditor.myrecyclerview.viewholder.ImageViewHolder;
import com.example.kepler.lesssmarteditor.myrecyclerview.viewholder.TextViewHolder;

import java.util.ArrayList;

/**
 * Created by Kepler on 2017-05-20.
 */

public class MyAdapter<T extends BaseComponent> extends RecyclerView.Adapter<BaseViewHolder> {
    public ArrayList<T> list = new ArrayList<>();

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder vh = null;

        Type type = Type.values()[viewType];
        switch (type) {
            case TEXT:
                vh = TextViewHolder.getInstance(parent, new EditTextChangeListener());
                break;
            case IMAGE:
                vh = ImageViewHolder.getInstance(parent);
                break;
            case MAP:
                // MapViewHolder
                break;
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (holder instanceof TextViewHolder) {
            ((TextViewHolder) holder).mListener.updatePosition(holder.getAdapterPosition());
            ((TextViewHolder) holder).bindView((TextComponent) list.get(position));
        }
        else if (holder instanceof ImageViewHolder) {
            ((ImageViewHolder) holder).bindView((ImageComponent) list.get(position));
        }
        else {
            //MapViewHolder Implement
        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType().getTypeValue();
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addComponent(T component) {
        list.add(component);
    }


    public class EditTextChangeListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (list.get(position) instanceof TextComponent) {
                ((TextComponent) list.get(position)).setContents(s.toString());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }
}
