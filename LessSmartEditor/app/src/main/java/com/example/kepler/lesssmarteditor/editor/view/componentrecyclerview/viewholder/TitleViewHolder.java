package com.example.kepler.lesssmarteditor.editor.view.componentrecyclerview.viewholder;

import android.view.View;
import android.widget.EditText;

import com.example.kepler.lesssmarteditor.R;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.TitleComponent;
import com.example.kepler.lesssmarteditor.editor.view.EditorActivity;
import com.example.kepler.lesssmarteditor.editor.view.componentrecyclerview.ComponentAdapter;
import com.example.kepler.lesssmarteditor.editor.view.componentrecyclerview.ComponentViewHolder;

/**
 * Created by Kepler on 2017-06-01.
 */

public class TitleViewHolder extends ComponentViewHolder<TitleComponent> {
    private EditText mEditText;
    public ComponentAdapter.EditTextChangeListener mListener;

    public TitleViewHolder(final View itemView, ComponentAdapter.EditTextChangeListener listener) {
        super(itemView);
        this.mListener = listener;
        mEditText = (EditText) itemView.findViewById(R.id.view_title_tv_compo);
        mEditText.addTextChangedListener(mListener);
        mEditText.setFilters(EditorActivity.filters);
    }

    @Override
    public void bindView(TitleComponent item) {
        mListener.updatePosition(getAdapterPosition());
        mEditText.setText(item.getTitle());
    }
}