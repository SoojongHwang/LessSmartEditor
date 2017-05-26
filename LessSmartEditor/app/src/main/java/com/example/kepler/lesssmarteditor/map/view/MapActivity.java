package com.example.kepler.lesssmarteditor.map.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kepler.lesssmarteditor.R;
import com.example.kepler.lesssmarteditor.map.model.Item;
import com.example.kepler.lesssmarteditor.map.presenter.MapPresenter;
import com.example.kepler.lesssmarteditor.map.presenter.MapPresenterImpl;
import com.example.kepler.lesssmarteditor.map.view.recyclerview.SiteAdapter;
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
    final private MapPresenter mPresenter = new MapPresenterImpl(this);
    final private LinearLayoutManager mManager = new LinearLayoutManager(this);
    ProgressDialog mDialog;
    private SiteAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        ButterKnife.bind(this);
        initNMap();
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("잠시만기다려주세요");
        Log.d("###","this is for test");
    }

    @OnClick(R.id.btn_search)
    public void onClickedSearch(View v) {
        mPresenter.requestSite(et_address.getText().toString());
    }

    @Override
    public void showRecyclerView(List<Item> list) {
        mAdapter = new SiteAdapter(mPresenter, list);
        rView.setLayoutManager(mManager);
        rView.setAdapter(mAdapter);
    }

    @Override
    public void makeToast(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDialog() {
        mDialog.show();
    }

    @Override
    public void dismissDialog() {
        mDialog.dismiss();
    }

    @Override
    public void addMap(Item item) {
        Intent intent = new Intent();
        intent.putExtra("data", item);
        setResult(MapActivity.RESULT_OK, intent);
        finish();
    }

    private void initNMap() {
        mMapView.setClientId(clientId);
        mMapView.setClickable(true);
        mMapView.setEnabled(true);
        mMapView.setFocusable(true);
        mMapView.setFocusableInTouchMode(true);
        mMapView.requestFocus();
        mMapView.setBuiltInZoomControls(true, null);
    }
}