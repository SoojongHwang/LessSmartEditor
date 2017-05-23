package com.example.kepler.lesssmarteditor.main.component;

public class BaseComponent {
    Type type;
    int index;

    public BaseComponent(int index, Type type) {
        this.index = index;
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
