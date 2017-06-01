package com.example.kepler.lesssmarteditor.editor.model.component.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Kepler on 2017-05-20.
 */
public enum Type {
    @SerializedName("0")
    TEXT(0),
    @SerializedName("1")
    IMAGE(1),
    @SerializedName("2")
    MAP(2),
    @SerializedName("3")
    TITLE(3);

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
