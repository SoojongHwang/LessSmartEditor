package com.example.kepler.lesssmarteditor.map.z_presenter;

import com.example.kepler.lesssmarteditor.map.z_network.Item;
import com.example.kepler.lesssmarteditor.map.z_network.SiteRetrofit;

import java.util.List;

/**
 * Created by Kepler on 2017-05-24.
 */

public class MapPresenterImpl implements MapPresenter {

    @Override
    public List<Item> loadSite(String str) {
        SiteRetrofit retrofit = new SiteRetrofit();
        List<Item> result = retrofit.callAPIfromServer(str);

        return result;
    }

    @Override
    public void onSiteItemClick(int position) {

    }
}
