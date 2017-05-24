package com.example.kepler.lesssmarteditor.map.z_presenter;

import com.example.kepler.lesssmarteditor.map.api.Item;

import java.util.List;

/**
 * Created by Kepler on 2017-05-24.
 */

public interface MapPresenter {

    List<com.example.kepler.lesssmarteditor.map.z_network.Item> loadSite(String str);
    void onSiteItemClick(int position);


    interface MapView{
        void requestRefresh();
    }
}
