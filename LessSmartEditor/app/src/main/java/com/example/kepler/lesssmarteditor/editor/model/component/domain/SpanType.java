package com.example.kepler.lesssmarteditor.editor.model.component.domain;

/**
 * Created by Kepler on 2017-06-07.
 */

public enum SpanType{
    BOLD(1),
    ITALIC(2),
    UNDERLINE(3);

    private int spanType;
    SpanType(int type) {
        this.spanType = type;
    }

    public int getTypeValue(){
        return this.spanType;
    }

    static SpanType getSpanType(int typeValue){
        for(SpanType type:SpanType.values()){
            if(type.getTypeValue()==typeValue)
                return type;
        }
        return null;
    }
}
