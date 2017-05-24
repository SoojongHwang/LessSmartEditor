package com.example.kepler.maptest.map.presenter;

import com.example.kepler.maptest.map.model.Item;

import java.util.List;

/**
 * Created by Kepler on 2017-05-24.
 */

public interface MapPresenter {
    //from View/Activity
    void requestSite(String str);

    //from View/Adapter
    void onSiteClicked(int position);
    void onSiteAddClicked(int position);

    //from Model/MapService
    void notifyAPIResult(List<Item> list);
    void notifyAPIFailed(String str);


}
