package com.example.kepler.lesssmarteditor.editor.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewGroup;

import com.example.kepler.lesssmarteditor.component.BaseComponent;
import com.example.kepler.lesssmarteditor.component.ImageComponent;
import com.example.kepler.lesssmarteditor.component.TextComponent;
import com.example.kepler.lesssmarteditor.component.Type;
import com.example.kepler.lesssmarteditor.editor.recyclerview.viewholder.ImageViewHolder;
import com.example.kepler.lesssmarteditor.editor.recyclerview.viewholder.TextViewHolder;

import java.util.ArrayList;

/**
 * Created by Kepler on 2017-05-20.
 */

public class ComponentAdapter<T extends BaseComponent> extends RecyclerView.Adapter<BaseViewHolder> implements ItemTouchHelperCallback.ItemTouchHelperListener {
    public ArrayList<T> list;

    public ComponentAdapter() {
        list = new ArrayList<>();
    }

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
        } else if (holder instanceof ImageViewHolder) {
            ((ImageViewHolder) holder).bindView((ImageComponent) list.get(position));
        } else {
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

    @Override
    public boolean onItemMove(int before, int after) {
        if (before < 0 || before >= list.size() || after < 0 || after >= list.size()) {
            return false;
        }

        T target = list.get(before);
        list.remove(before);
        list.add(after, target);

        notifyItemMoved(before, after);
        return true;
    }

    @Override
    public void onItemRemove(int position) {
        if (!list.isEmpty())
            list.remove(position);
        notifyDataSetChanged();
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
            ((TextComponent) list.get(position)).setContents(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }
}
