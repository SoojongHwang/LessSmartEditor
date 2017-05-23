package com.example.kepler.lesssmarteditor.main.component;

/**
 * Created by Kepler on 2017-05-20.
 */
public enum Type {
    TEXT(0),IMAGE(1),MAP(2);

    private final int typeValue;

    Type(int type) {
        this.typeValue = type;
    }

    public int getTypeValue() {
        return typeValue;
    }
}
