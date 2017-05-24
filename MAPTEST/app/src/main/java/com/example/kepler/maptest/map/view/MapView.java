package com.example.kepler.maptest.map.view;

import com.example.kepler.maptest.map.model.Item;

import java.util.List;

/**
 * Created by Kepler on 2017-05-24.
 */

public interface MapView {
    void showRecyclerView(List<Item> list);
    void makeToast(String str);

    //for NMapView
//    void setMapCenter();
}
