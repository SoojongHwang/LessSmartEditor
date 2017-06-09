package com.example.kepler.lesssmarteditor.editor.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import com.example.kepler.lesssmarteditor.editor.model.component.domain.SpanInfo;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.SpanType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kepler on 2017-06-07.
 */

@SuppressLint("AppCompatCustomView")
public class MyEditText extends EditText {
    MySpanDetectListener mDetectListener;
    MyEditTextFocusListener mFocusListener;
    private int currentPos;

    public interface MySpanDetectListener {
        void onSpanDetected(boolean[] bit);
    }

    public interface MyEditTextFocusListener {
        void save(int pos,List<SpanInfo> spanInfoList);
    }

    public MyEditText(Context context) {
        super(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnSpanListener(MySpanDetectListener listener) {
        this.mDetectListener = listener;
    }
    public void setOnMyFocusListener(MyEditTextFocusListener listener){
        this.mFocusListener = listener;
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        if (focused) {
            Log.d("SOOJONG", "get focus!");
        }
        else {
            Log.d("SOOJONG", "lost focus");
            List<SpanInfo> spanInfoList = new ArrayList<>();
            Spannable eSpan = this.getText();
            Object[] oArr = eSpan.getSpans(0, eSpan.length(), Object.class);
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

            mFocusListener.save(currentPos, spanInfoList);
            Log.d("SOOJONG", spanInfoList.size()+"개의 span Saved!!");
        }
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        boolean[] bit = {false, false, false};
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
                        case Typeface.BOLD:
                            bit[0] = true;
                            break;
                        case Typeface.ITALIC:
                            bit[1] = true;
                            break;
                    }
                }
            } else if (o instanceof MyUnderlineSpan) {
                MyUnderlineSpan us = (MyUnderlineSpan) o;
                int spanStart = eSpan.getSpanStart(us);
                int spanEnd = eSpan.getSpanEnd(us);

                if (spanStart == spanEnd) { //쓰레기 스팬정보
                    eSpan.removeSpan(us);
                } else if (spanStart < selEnd) {
                    bit[2] = true;
                }
            }
        }

        if (mDetectListener != null) {
            mDetectListener.onSpanDetected(bit);
        }
        super.onSelectionChanged(selStart, selEnd);
    }
}