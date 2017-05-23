package com.example.kepler.lesssmarteditor.navermap.api;

import java.util.List;

/**
 * Created by Kepler on 2017-05-23.
 */

public class SearchResult {
    public int total;
    public List<Item> items;
    public class Item{
        public String title;
        public String roadAddress;
        public float mapx;
        public float mapy;
    }
}
