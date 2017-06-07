package com.example.kepler.lesssmarteditor.editor.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Spannable;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by Kepler on 2017-06-07.
 */

@SuppressLint("AppCompatCustomView")
public class MyEditText extends EditText {
    MySpanListener mListener;

    public interface MySpanListener {
        void onDetected(String bit);
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
    public void clearSpanListener(){
        this.mListener = null;
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        String[] bit = {"0", "0", "0"};
        Spannable eSpan = this.getText();
        Object[] sArr = eSpan.getSpans(selStart, selEnd, Object.class);
        for (Object o : sArr) {
            if (o instanceof StyleSpan) {
                StyleSpan ss = (StyleSpan) o;
                if(eSpan.getSpanStart(ss) == eSpan.getSpanEnd(ss)){
                    eSpan.removeSpan(ss);
                }
                else if (selStart != eSpan.getSpanStart(ss) || selEnd>selStart) {
                    if (ss.getStyle() == 1) {
                        bit[0] = "1";
                    } else if (ss.getStyle() == 2) {
                        bit[1] = "1";
                    }
                }
                Log.d("StyleSpan Info",eSpan.getSpanStart(ss)+", "+eSpan.getSpanEnd(ss)+", "+ss.getStyle());
            }
            else if (o instanceof UnderlineSpan) {
                UnderlineSpan us = (UnderlineSpan) o;
                if(eSpan.getSpanStart(us) == eSpan.getSpanEnd(us)){
                    eSpan.removeSpan(us);
                }
                else if (selStart != eSpan.getSpanStart(us) || selEnd>selStart) {
                    bit[2] = "1";
                }

                Log.d("UnderlineSpan Info",eSpan.getSpanStart(us)+", "+eSpan.getSpanEnd(us));
            }
            Log.d("bit",bit[0]+""+bit[1]+""+bit[2]);

        }
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < 3; i++) {
            sb.append(bit[i]);
        }
        if (mListener != null)
            mListener.onDetected(sb.toString());
        super.onSelectionChanged(selStart, selEnd);
    }
}