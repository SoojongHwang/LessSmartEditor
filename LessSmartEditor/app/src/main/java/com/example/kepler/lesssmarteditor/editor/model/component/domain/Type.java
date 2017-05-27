package com.example.kepler.lesssmarteditor.editor.model.component.domain;

/**
 * Created by Kepler on 2017-05-20.
 */
public enum Type {
    TEXT(0),IMAGE(1),MAP(2);

    private final int typeValue;

    Type(int type) {
        this.typeValue = type;
    }

    public int getValue() {
        return typeValue;
    }

    public static Type getType(int value){
        for(Type type:Type.values()){
            if(type.getValue() == value)
                return type;
        }
        return null;
    }
}
