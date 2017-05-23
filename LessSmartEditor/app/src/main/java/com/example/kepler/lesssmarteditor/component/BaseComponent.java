package com.example.kepler.lesssmarteditor.component;

public class BaseComponent {
    private Type type;

    public BaseComponent(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
