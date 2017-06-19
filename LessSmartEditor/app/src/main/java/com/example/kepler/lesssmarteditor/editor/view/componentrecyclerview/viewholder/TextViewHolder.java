package com.example.kepler.lesssmarteditor.editor.view.componentrecyclerview.viewholder;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;

import com.example.kepler.lesssmarteditor.R;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.SpanInfo;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.SpanType;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.TextComponent;
import com.example.kepler.lesssmarteditor.editor.view.EditorActivity;
import com.example.kepler.lesssmarteditor.editor.view.MyEditText;
import com.example.kepler.lesssmarteditor.editor.view.MyUnderlineSpan;
import com.example.kepler.lesssmarteditor.editor.view.componentrecyclerview.ComponentAdapter;
import com.example.kepler.lesssmarteditor.editor.view.componentrecyclerview.ComponentViewHolder;

import java.util.List;


/**
 * Created by Kepler on 2017-05-20.
 */

public class TextViewHolder extends ComponentViewHolder<TextComponent> {
    public MyEditText mEditText;
    private ComponentAdapter.EditTextChangeListener mListener;
    private MyEditText.MySpanDetectListener mSpanListener;
    private MyEditText.MyEditTextFocusListener mFocusListener;

    public TextViewHolder(View itemView) {
        super(itemView);
        mEditText = (MyEditText) itemView.findViewById(R.id.view_text_editText);
    }

    @Override
    public void bindView(final TextComponent textComponent) {
        mListener.updatePosition(getAdapterPosition());
        mEditText.setText(textComponent.getContents());
        applySpanList(textComponent.getSpanInfoList());
    }
    public void setEditTextChangeListener(ComponentAdapter.EditTextChangeListener listener){
        this.mListener = listener;
        this.mEditText.addTextChangedListener(mListener);
    }
    public void setSpanDetector(MyEditText.MySpanDetectListener mySpanListener){
        this.mSpanListener = mySpanListener;
        this.mEditText.setOnSpanListener(mSpanListener);
    }
    public void setSpanSaver(MyEditText.MyEditTextFocusListener myFocusListener){
        this.mFocusListener = myFocusListener;
        this.mEditText.setOnMyFocusListener(mFocusListener);
    }
    private void applySpanList(List<SpanInfo> spanList){
        Spannable eSpan = mEditText.getText();

        if(spanList == null)
            return;

        for(SpanInfo si:spanList){
            SpanType sType = si.spanType;
            int spanStart = si.start;
            int spanEnd = si.end;

            switch (sType){
                case BOLD:
                    eSpan.setSpan(new StyleSpan(Typeface.BOLD),spanStart,spanEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    break;
                case ITALIC:
                    eSpan.setSpan(new StyleSpan(Typeface.ITALIC),spanStart,spanEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    break;
                case UNDERLINE:
                    eSpan.setSpan(new MyUnderlineSpan(),spanStart,spanEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    break;
            }
        }
    }
}