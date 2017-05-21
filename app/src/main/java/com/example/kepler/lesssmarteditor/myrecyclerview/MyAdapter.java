package com.example.kepler.lesssmarteditor.myrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.kepler.lesssmarteditor.component.BaseComponent;
import com.example.kepler.lesssmarteditor.component.ImageComponent;
import com.example.kepler.lesssmarteditor.component.TextComponent;
import com.example.kepler.lesssmarteditor.myrecyclerview.viewholder.ImageViewHolder;
import com.example.kepler.lesssmarteditor.myrecyclerview.viewholder.TextViewHolder;

import java.util.ArrayList;

/**
 * Created by Kepler on 2017-05-20.
 */

public class MyAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    ArrayList<BaseComponent> list = new ArrayList<>();

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return TextViewHolder.getInstance(parent);
            case 1:
                return ImageViewHolder.getInstance(parent);
            case 2:
                // MapViewHolder
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (holder instanceof TextViewHolder) {
            ((TextViewHolder) holder).bindView((TextComponent) list.get(position));
        }
        else if (holder instanceof ImageViewHolder) {
            ((ImageViewHolder) holder).bindView((ImageComponent) list.get(position));
        }
        else{//MapViewHolder Implement

        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType().getIntType();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addComponent(BaseComponent component) {
        list.add(component);
    }
}
