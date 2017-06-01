package com.example.kepler.lesssmarteditor.editor.view.componentrecyclerview.viewholder;

import android.view.View;
import android.widget.EditText;

import com.example.kepler.lesssmarteditor.R;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.BaseComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.TitleComponent;
import com.example.kepler.lesssmarteditor.editor.view.componentrecyclerview.ComponentViewHolder;

import butterknife.BindView;

/**
 * Created by Kepler on 2017-06-01.
 */

public class TitleViewHolder extends ComponentViewHolder<TitleComponent>{
    private EditText mEditText;

    public TitleViewHolder(View itemView) {
        super(itemView);
        mEditText = (EditText)itemView.findViewById(R.id.view_title_tv_compo);
    }

    @Override
    public void bindView(TitleComponent item) {
        mEditText.setText(item.getTitle());
    }
}
