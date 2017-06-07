package com.example.kepler.lesssmarteditor.editor.model.component.domain;

import android.text.Spannable;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kepler on 2017-05-20.
 */

public class TextComponent extends BaseComponent {
    private String contents;
    private List<SpanInfo> spanInfoList;

    public TextComponent(Type type, String contents) {
        super(type);
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public List<SpanInfo> getSpanInfoList() {
        return this.spanInfoList;
    }

    public void setSpanInfoList(List<SpanInfo> spanInfoList) {
        this.spanInfoList = spanInfoList;
    }

    @Override
    public String toString() {
        return "{" + this.contents + "}";
    }
}
