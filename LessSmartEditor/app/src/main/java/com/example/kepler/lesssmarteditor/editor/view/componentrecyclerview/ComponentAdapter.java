package com.example.kepler.lesssmarteditor.editor.view.componentrecyclerview;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kepler.lesssmarteditor.R;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.BaseComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.SpanInfo;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.SpanType;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.TextComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.TitleComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.Type;
import com.example.kepler.lesssmarteditor.editor.presenter.EditorPresenter;
import com.example.kepler.lesssmarteditor.editor.view.componentrecyclerview.listener.ItemTouchHelperListener;
import com.example.kepler.lesssmarteditor.editor.view.componentrecyclerview.viewholder.ImageViewHolder;
import com.example.kepler.lesssmarteditor.editor.view.componentrecyclerview.viewholder.MapViewHolder;
import com.example.kepler.lesssmarteditor.editor.view.componentrecyclerview.viewholder.TextViewHolder;
import com.example.kepler.lesssmarteditor.editor.view.componentrecyclerview.viewholder.TitleViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kepler on 2017-05-25.
 */

public class ComponentAdapter extends RecyclerView.Adapter<ComponentViewHolder>
        implements ItemTouchHelperListener {
    private EditorPresenter mPresenter;
    private List<BaseComponent> mList;
    private boolean isLoadedFromDB = false;

    public ComponentAdapter(EditorPresenter presenter) {
        this.mPresenter = presenter;
        this.mList = new ArrayList<>();
        mList.add(new TitleComponent(Type.TITLE, ""));
    }

    public ComponentAdapter(EditorPresenter presenter, List<BaseComponent> list) {
        this.mPresenter = presenter;
        this.mList = list;
        isLoadedFromDB = true;
    }

    @Override
    public ComponentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ComponentViewHolder vh = null;
        Type type = Type.getType(viewType);
        switch (type) {
            case TEXT:
                final View text = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_text, parent, false);
                vh = new TextViewHolder(text, new EditTextChangeListener());
                break;
            case IMAGE:
                final View image = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_image, parent, false);
                vh = new ImageViewHolder(image);
                break;
            case MAP:
                final View map = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_map, parent, false);
                vh = new MapViewHolder(map);
                break;
            case TITLE:
                final View title = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_ctitle, parent, false);
                vh = new TitleViewHolder(title, new EditTextChangeListener());
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
        mPresenter.notifyDocumentChanged();
        return true;
    }

    @Override
    public void onItemRemove(int position) {
        if (!mList.isEmpty())
            mList.remove(position);
        notifyItemRemoved(position);
        mPresenter.notifyDocumentChanged();
    }

    public void addComponent(BaseComponent component) {
        mList.add(component);
    }

    public List<BaseComponent> getList() {
        return mList;
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
            if (mList.get(position) instanceof TextComponent) {
                ((TextComponent) mList.get(position)).setContents(s.toString());
            }
            else if (mList.get(position) instanceof TitleComponent){
                ((TitleComponent) mList.get(position)).setTitle(s.toString());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 0) {
                mPresenter.notifyDocumentChanged();
            }
            if(!(mList.get(position) instanceof TextComponent))
                return;

            List<SpanInfo> spanInfoList;
            if(isLoadedFromDB)
                spanInfoList = ((TextComponent)mList.get(position)).getSpanInfoList();
            else
                spanInfoList = new ArrayList<>();
            isLoadedFromDB = false;
            Spannable eSpan = s;
            int start = 0;
            int end = s.length();

            Object[] oArr = eSpan.getSpans(start, end, Object.class);
            for (Object o : oArr) {
                if (o instanceof StyleSpan) {
                    StyleSpan ss = (StyleSpan) o;
                    int spanStart = eSpan.getSpanStart(ss);
                    int spanEnd = eSpan.getSpanEnd(ss);
                    SpanInfo current;
                    if (ss.getStyle() == Typeface.BOLD) {
                        current = new SpanInfo(SpanType.BOLD, spanStart, spanEnd);
                    } else {
                        current = new SpanInfo(SpanType.ITALIC, spanStart, spanEnd);
                    }
                    spanInfoList.add(current);
                }
                if (o instanceof UnderlineSpan) {
                    UnderlineSpan us = (UnderlineSpan) o;
                    int spanStart = eSpan.getSpanStart(us);
                    int spanEnd = eSpan.getSpanEnd(us);
                    SpanInfo current = new SpanInfo(SpanType.UNDERLINE, spanStart, spanEnd);
                    spanInfoList.add(current);
                }
            }
            ((TextComponent) mList.get(position)).setSpanInfoList(spanInfoList);
        }
    }
}