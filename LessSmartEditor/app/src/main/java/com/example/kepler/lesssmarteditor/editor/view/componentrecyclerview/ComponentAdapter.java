package com.example.kepler.lesssmarteditor.editor.view.componentrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kepler.lesssmarteditor.R;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.BaseComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.ImageComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.MapComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.TextComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.Type;
import com.example.kepler.lesssmarteditor.editor.view.componentrecyclerview.viewholder.ImageViewHolder;
import com.example.kepler.lesssmarteditor.editor.view.componentrecyclerview.viewholder.MapViewHolder;
import com.example.kepler.lesssmarteditor.editor.view.componentrecyclerview.viewholder.TextViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kepler on 2017-05-25.
 */

public class ComponentAdapter extends RecyclerView.Adapter<ComponentViewHolder>
        implements ItemTouchHelperCallback.ItemTouchHelperListener {
    public List<BaseComponent> cList;

    public ComponentAdapter() {
        cList = new ArrayList<>();
    }

    public ComponentAdapter(List<BaseComponent> list) {
        this.cList = list;
        notifyDataSetChanged();
    }

    @Override
    public ComponentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ComponentViewHolder vh = null;
        Type type = Type.getType(viewType);
        switch (type) {
            case TEXT:
                View text = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_text,parent, false);
                vh = new TextViewHolder(text, new EditTextChangeListener());
                break;
            case IMAGE:
                View image = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_image, parent, false);
                vh = new ImageViewHolder(image);
                break;
            case MAP:
                View map = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_map, parent, false);
                vh = new MapViewHolder(map);
                break;
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(ComponentViewHolder holder, int position) {
        if (holder instanceof TextViewHolder) {
            ((TextViewHolder) holder).mListener.updatePosition(holder.getAdapterPosition());
            ((TextViewHolder) holder).bindView((TextComponent) cList.get(position));
        } else if (holder instanceof ImageViewHolder) {
            ((ImageViewHolder) holder).bindView((ImageComponent) cList.get(position));
        } else {
            ((MapViewHolder) holder).bindView((MapComponent) cList.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return cList.get(position).getType().getValue();
    }


    @Override
    public int getItemCount() {
        return cList.size();
    }

    @Override
    public boolean onItemMove(int before, int after) {
        if (before < 0 || before >= cList.size() || after < 0 || after >= cList.size()) {
            return false;
        }

        BaseComponent target = cList.get(before);
        cList.remove(before);
        cList.add(after, target);

        notifyItemMoved(before, after);
        return true;
    }

    @Override
    public void onItemRemove(int position) {
        if (!cList.isEmpty())
            cList.remove(position);
        notifyDataSetChanged();
    }

    public void addComponent(BaseComponent component) {
        cList.add(component);
    }

    public List<BaseComponent> getList() {
        return cList;
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
            if (cList.get(position) instanceof TextComponent)
                ((TextComponent) cList.get(position)).setContents(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }
}