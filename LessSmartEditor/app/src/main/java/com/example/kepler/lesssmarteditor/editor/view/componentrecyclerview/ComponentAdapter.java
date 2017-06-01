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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Kepler on 2017-05-25.
 */

public class ComponentAdapter extends RecyclerView.Adapter<ComponentViewHolder>
        implements ItemTouchHelperCallback.ItemTouchHelperListener {
    private List<BaseComponent> mList;
    private int mMemoId;
    private boolean mMemoIsNew;

    public ComponentAdapter() {
        this.mMemoIsNew = true;
        this.mList = new ArrayList<>();
    }

    public ComponentAdapter(int id, List<BaseComponent> list) {
        this.mMemoIsNew = false;
        this.mList = list;
        this.mMemoId = id;
    }

    @Override
    public ComponentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ComponentViewHolder vh = null;
        Type type = Type.getType(viewType);
        switch (type) {
            case TEXT:
                View text = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_text, parent, false);
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
        holder.bindView(mList.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getType().getValue();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public boolean onItemMove(int before, int after) {
        if (before < 0 || before >= mList.size() || after < 0 || after >= mList.size()) {
            return false;
        }

        BaseComponent target = mList.get(before);
        mList.remove(before);
        mList.add(after, target);

        notifyItemMoved(before, after);
        return true;
    }

    @Override
    public void onItemRemove(int position) {
        if (!mList.isEmpty())
            mList.remove(position);
        notifyItemRemoved(position);
    }

    public void addComponent(BaseComponent component) {
        mList.add(component);
    }

    public List<BaseComponent> getList() {
        return mList;
    }

    public int getId() {
        return mMemoId;
    }

    public boolean getIsNew() {
        return mMemoIsNew;
    }

    public void setmMemoId(int mMemoId) {
        this.mMemoId = mMemoId;
    }

    public void setmMemoIsNew(boolean mMemoIsNew) {
        this.mMemoIsNew = mMemoIsNew;
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
            if (mList.get(position) instanceof TextComponent)
                ((TextComponent) mList.get(position)).setContents(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }
}