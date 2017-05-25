package com.example.kepler.lesssmarteditor.map.presenter;

import com.example.kepler.lesssmarteditor.map.model.Item;

import java.util.List;

/**
 * Created by Kepler on 2017-05-24.
 */

public interface MapPresenter {
    //from View/Activity
    void requestSite(String str);

    //from View/Adapter
    void onSiteClicked(Item item);
    void onSiteAddClicked(Item item);

    //from Model/MapService
    void notifyAPIResult(List<Item> list);
    void notifyAPIFailed(String str);


}
