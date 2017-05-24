package com.example.kepler.maptest.map.presenter;

import com.example.kepler.maptest.map.model.Item;
import com.example.kepler.maptest.map.model.MapService;
import com.example.kepler.maptest.map.view.MapView;

import java.util.List;

/**
 * Created by Kepler on 2017-05-24.
 */

public class MapPresenterImpl implements MapPresenter {
    private MapView mapView;
    private MapService mapService;

    public MapPresenterImpl(MapView mapView) {
        this.mapView = mapView;
    }

    @Override
    public void requestSite(String str) {
        mapService = new MapService(this);
        mapService.callSearchAPI(str);
    }

    @Override
    public void notifyAPIResult(List<Item> list) {
        mapView.showRecyclerView(list);
    }

    @Override
    public void notifyAPIFailed(String str) {
        mapView.makeToast(str);
    }

    @Override
    public void onSiteClicked(int position) {
        mapView.makeToast(""+position);
    }

    @Override
    public void onSiteAddClicked(int position) {
        mapView.makeToast(""+position);
    }


}
