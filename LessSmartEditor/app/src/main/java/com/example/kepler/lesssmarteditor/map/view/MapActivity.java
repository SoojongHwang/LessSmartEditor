package com.example.kepler.lesssmarteditor.map.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
    private ProgressDialog mProgressDialog;
    private SiteAdapter mAdapter;
    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        ButterKnife.bind(this);
        initActivity();
        initNMap();
    }

    @OnClick(R.id.btn_search)
    public void onClickedSearch(View v) {
        mPresenter.onClickedSearchButton(et_address.getText().toString());
        hideKeyBoard();
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
    public void showProgressDialog() {
        mProgressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        mProgressDialog.dismiss();
    }

    @Override
    public void addMap(Item item) {
        Intent intent = new Intent();
        intent.putExtra("data", item);
        setResult(MapActivity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void hideKeyBoard() {
        imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),0);
    }

    @Override
    public void setMapCenter(int x, int y) {
        Point p = new Point(x,y);
        mMapView.mapPointToScreen(p);
    }

    private void initActivity(){
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("잠시만기다려주세요");
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