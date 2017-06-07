package com.example.kepler.lesssmarteditor.editor.model.component.domain;

/**
 * Created by Kepler on 2017-06-07.
 */
public class SpanInfo {
    public SpanType spanType;
    public int start, end;

    public SpanInfo(SpanType spanType, int start, int end) {
        this.spanType = spanType;
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return spanType+", "+start+", "+end;
    }
}
