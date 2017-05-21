package com.example.kepler.lesssmarteditor.component;

/**
 * Created by Kepler on 2017-05-20.
 */
public enum Type {
    TEXT(0),IMAGE(1),MAP(2);

    private final int type;

    Type(int type) {
        this.type = type;
    }

    public int getIntType() {
        return type;
    }
}
