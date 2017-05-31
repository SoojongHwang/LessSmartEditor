package com.example.kepler.lesssmarteditor.editor.view.componentrecyclerview.viewholder;

import android.view.View;
import android.widget.EditText;

import com.example.kepler.lesssmarteditor.R;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.TextComponent;
import com.example.kepler.lesssmarteditor.editor.view.componentrecyclerview.ComponentAdapter;
import com.example.kepler.lesssmarteditor.editor.view.componentrecyclerview.ComponentViewHolder;


/**
 * Created by Kepler on 2017-05-20.
 */

public class TextViewHolder extends ComponentViewHolder<TextComponent> {
    private EditText mEditText;
    public ComponentAdapter.EditTextChangeListener mListener;

    public TextViewHolder(View itemView, ComponentAdapter.EditTextChangeListener listener) {
        super(itemView);
        this.mListener = listener;
        mEditText = (EditText)itemView.findViewById(R.id.view_text_editText);
        mEditText.addTextChangedListener(mListener);
    }

    @Override
    public void bindView(TextComponent textComponent) {
        mListener.updatePosition(getAdapterPosition());
        mEditText.setText(textComponent.getContents());
    }
}
