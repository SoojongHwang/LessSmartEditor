package com.example.kepler.lesssmarteditor.component;

/**
 * Created by Kepler on 2017-05-20.
 */

public class TextComponent extends BaseComponent{
    private String contents;

    public TextComponent(int index, Type type, String contents) {
        super(index,type);
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }
}
