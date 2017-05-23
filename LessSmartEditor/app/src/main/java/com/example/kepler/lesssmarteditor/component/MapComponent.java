package com.example.kepler.lesssmarteditor.component;

/**
 * Created by Kepler on 2017-05-20.
 */

public class MapComponent{
    private String title;
    private String address;
    private float x;
    private float y;

    public MapComponent(String title, String address, float x, float y) {
        this.title = title;
        this.address = address;
        this.x = x;
        this.y = y;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
