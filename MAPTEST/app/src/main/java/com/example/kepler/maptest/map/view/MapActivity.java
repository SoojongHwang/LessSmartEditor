package com.example.kepler.maptest.map.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kepler.maptest.R;
import com.example.kepler.maptest.map.model.Item;
import com.example.kepler.maptest.map.presenter.MapPresenterImpl;
import com.example.kepler.maptest.map.view.recyclerview.SiteAdapter;
import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapActivity extends NMapActivity implements MapView {
    @BindView(R.id.nmapview)
    NMapView mMapView;
    @BindView(R.id.map_recyclerView)
    RecyclerView rView;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.btn_search)
    Button btn_search;

    final private String clientId = "hOBAjjmz9dUkwoGrp6pS";
    final private MapPresenterImpl presenter = new MapPresenterImpl(this);
    final private LinearLayoutManager manager = new LinearLayoutManager(this);
    private SiteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initNMap();
    }

    @OnClick(R.id.btn_search)
    public void onClickedSearch(View v){
        presenter.requestSite(et_address.getText().toString());
    }

    @Override
    public void showRecyclerView(List<Item> list) {
        rView = (RecyclerView)findViewById(R.id.map_recyclerView);
        adapter = new SiteAdapter(presenter, list);
        rView.setLayoutManager(manager);
        rView.setAdapter(adapter);
    }

    @Override
    public void makeToast(String str) {
        Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();
    }

    private void initNMap(){
        mMapView.setClientId(clientId);
        mMapView.setClickable(true);
        mMapView.setEnabled(true);
        mMapView.setFocusable(true);
        mMapView.setFocusableInTouchMode(true);
        mMapView.requestFocus();
        mMapView.setBuiltInZoomControls(true, null);
    }
}