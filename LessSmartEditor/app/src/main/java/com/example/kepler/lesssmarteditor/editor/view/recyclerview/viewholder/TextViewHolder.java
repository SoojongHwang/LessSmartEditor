package com.example.kepler.lesssmarteditor.editor.view.recyclerview.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.kepler.lesssmarteditor.R;
import com.example.kepler.lesssmarteditor.editor.model.domain.TextComponent;
import com.example.kepler.lesssmarteditor.editor.view.recyclerview.ComponentAdapter;
import com.example.kepler.lesssmarteditor.editor.view.recyclerview.ComponentViewHolder;


/**
 * Created by Kepler on 2017-05-20.
 */

public class TextViewHolder extends ComponentViewHolder<TextComponent> {
    private EditText mEditText;
    public ComponentAdapter.EditTextChangeListener mListener;

    public static TextViewHolder getInstance(ViewGroup parent, ComponentAdapter.EditTextChangeListener listener){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_text, parent, false);
        return new TextViewHolder(view, listener);
    }

    public TextViewHolder(View itemView, ComponentAdapter.EditTextChangeListener listener) {
        super(itemView);
        this.mListener = listener;
        mEditText = (EditText)itemView.findViewById(R.id.view_text_editText);
        mEditText.addTextChangedListener(mListener);
    }

    @Override
    public void bindView(TextComponent textComponent) {
        mEditText.setText(textComponent.getContents());
    }
}
