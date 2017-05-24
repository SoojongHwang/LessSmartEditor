package com.example.kepler.lesssmarteditor.map.z_adapter;

import com.example.kepler.lesssmarteditor.map.api.Item;

import java.util.List;

/**
 * Created by Kepler on 2017-05-24.
 */

public interface Z_SiteAdapterModel {
    void setSiteList(List<Item> list);
    int getListSize();
    Item getItem(int position);
}
