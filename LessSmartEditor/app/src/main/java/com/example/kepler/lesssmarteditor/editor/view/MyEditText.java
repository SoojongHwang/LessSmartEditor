package com.example.kepler.lesssmarteditor.editor.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.text.Spannable;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import com.example.kepler.lesssmarteditor.editor.model.component.domain.SpanInfo;

import java.util.List;

/**
 * Created by Kepler on 2017-06-07.
 */

@SuppressLint("AppCompatCustomView")
public class MyEditText extends EditText {
    MySpanListener mListener;

    public interface MySpanListener {
        void onSpanDetected(String bit);
    }
    public interface MySpanSaveListener{
        void save(List<SpanInfo> spanInfoList);
    }

    public MyEditText(Context context) {
        super(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnSpanListener(MySpanListener listener) {
        this.mListener = listener;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        if(focused)
            Log.d("SOOJONG","get focus!");
        else
            Log.d("SOOJONG","lost focus");
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        String[] bit = {"0", "0", "0"};
        Spannable eSpan = this.getText();
        Object[] sArr = eSpan.getSpans(selStart, selEnd, Object.class);

        for (Object o : sArr) {
            if (o instanceof StyleSpan) {
                StyleSpan ss = (StyleSpan) o;
                int spanStart = eSpan.getSpanStart(ss);
                int spanEnd = eSpan.getSpanEnd(ss);

                if (spanStart == spanEnd) { //쓰레기 스팬정보
                    eSpan.removeSpan(ss);
                } else if (spanStart < selEnd) {
                    int spanStyle = ss.getStyle();
                    switch (spanStyle) {
                        case 1:
                            bit[0] = "1";
                            break;
                        case 2:
                            bit[1] = "1";
                            break;
                    }
                }
            } else if (o instanceof UnderlineSpan) {
                UnderlineSpan us = (UnderlineSpan) o;
                int spanStart = eSpan.getSpanStart(us);
                int spanEnd = eSpan.getSpanEnd(us);

                if (spanStart == spanEnd) { //쓰레기 스팬정보
                    eSpan.removeSpan(us);
                } else if (spanStart < selEnd) {
                    bit[2] = "1";
                }
            }
        }
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < 3; i++) {
            sb.append(bit[i]);
        }

        if (mListener != null) {
            mListener.onSpanDetected(sb.toString());
        }
        super.onSelectionChanged(selStart, selEnd);
    }
}