package com.example.kepler.lesssmarteditor.map.view;

import com.example.kepler.lesssmarteditor.map.model.Item;

import java.util.List;

/**
 * Created by Kepler on 2017-05-24.
 */

public interface MapView {
    void showRecyclerView(List<Item> list);
    void makeToast(String str);

    void showDialog();
    void dismissDialog();

    void addMap(Item item);


    //for NMapView
//    void setMapCenter();
}
