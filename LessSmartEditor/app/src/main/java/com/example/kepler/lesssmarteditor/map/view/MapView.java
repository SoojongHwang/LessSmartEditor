package com.example.kepler.lesssmarteditor.map.view;

import com.example.kepler.lesssmarteditor.map.model.Item;

import java.util.List;

/**
 * Created by Kepler on 2017-05-24.
 */

public interface MapView {
    void showRecyclerView(List<Item> list);

    void makeToast(String str);

    void showProgressDialog();

    void dismissProgressDialog();

    void addMap(Item item);
    void hideKeyBoard();

    //for NMapView
    void setMapCenter(int x, int y);
}
