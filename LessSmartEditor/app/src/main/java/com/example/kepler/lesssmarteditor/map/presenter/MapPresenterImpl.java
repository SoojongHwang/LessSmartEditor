package com.example.kepler.lesssmarteditor.map.presenter;

import com.example.kepler.lesssmarteditor.map.model.Item;
import com.example.kepler.lesssmarteditor.map.model.MapService;
import com.example.kepler.lesssmarteditor.map.view.MapView;

import java.util.List;

/**
 * Created by Kepler on 2017-05-24.
 */

public class MapPresenterImpl implements MapPresenter {
    private MapView mMapView;
    private MapService mMapService;

    public MapPresenterImpl(MapView mapView) {
        this.mMapView = mapView;
    }

    @Override
    public void onClickedSearchButton(String str) {
        mMapView.showProgressDialog();
        mMapService = new MapService(this);
        mMapService.callSearchAPI(str);
    }

    @Override
    public void onClickedSite(Item item) {
        mMapView.makeToast(item.toString());
        mMapView.setMapCenter((int)item.mapx, (int)item.mapy);
    }

    @Override
    public void onClickedSiteAddButton(Item item) {
        mMapView.addMap(item);
    }

    @Override
    public void notifyAPIResult(List<Item> list) {
        mMapView.dismissProgressDialog();
        mMapView.showRecyclerView(list);
    }

    @Override
    public void notifyAPIFailed(String str) {
        mMapView.dismissProgressDialog();
        mMapView.makeToast(str);
    }



}
