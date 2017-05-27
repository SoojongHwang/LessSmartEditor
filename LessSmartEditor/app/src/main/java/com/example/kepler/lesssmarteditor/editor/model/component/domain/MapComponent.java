package com.example.kepler.lesssmarteditor.editor.model.component.domain;

/**
 * Created by Kepler on 2017-05-20.
 */

public class MapComponent extends BaseComponent{
    private String name;
    private String address;
    private String url;

    public MapComponent(Type type, String name, String address, String url) {
        super(type);
        this.name = name;
        this.address = address;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getUrl() {
        return url;
    }
}
