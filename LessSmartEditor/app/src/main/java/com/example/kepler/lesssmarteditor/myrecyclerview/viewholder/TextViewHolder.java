package com.example.kepler.lesssmarteditor.myrecyclerview.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.kepler.lesssmarteditor.R;
import com.example.kepler.lesssmarteditor.component.TextComponent;
import com.example.kepler.lesssmarteditor.myrecyclerview.BaseViewHolder;

/**
 * Created by Kepler on 2017-05-20.
 */

public class TextViewHolder extends BaseViewHolder<TextComponent>{
    public EditText mEditText;

    public static TextViewHolder getInstance(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_text, parent, false);
        return new TextViewHolder(view);
    }

    public TextViewHolder(View itemView) {
        super(itemView);
        mEditText = (EditText)itemView.findViewById(R.id.view_text_editText);
    }

    @Override
    public void bindView(TextComponent textComponent) {
        mEditText.setText(textComponent.getContents());
    }
}
