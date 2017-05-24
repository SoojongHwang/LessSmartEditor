package com.example.kepler.lesssmarteditor.map.z_view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.kepler.lesssmarteditor.R;
import com.example.kepler.lesssmarteditor.map.recyclerview.SiteAdapter;
import com.example.kepler.lesssmarteditor.map.z_network.Item;
import com.example.kepler.lesssmarteditor.map.z_presenter.MapPresenter;
import com.example.kepler.lesssmarteditor.map.z_presenter.MapPresenterImpl;
import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Z_MapActivity extends NMapActivity implements MapPresenter.MapView{
    InputMethodManager imm;

    @BindView(R.id.nmapview)
    NMapView mMapView;
    @BindView(R.id.address)
    EditText et_address;
    @BindView(R.id.search)
    Button searchButton;
    @BindView(R.id.map_recyclerView)

    RecyclerView view;
    SiteAdapter adapter;
    LinearLayoutManager manager;

    MapPresenterImpl mapPresenterImpl;

    private final String clientId = "sxWkb7RugVl3VTuLLacs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_z__map);

        ButterKnife.bind(this);
        initNmap();

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        mapPresenterImpl = new MapPresenterImpl();
    }
    private void initNmap(){
        mMapView.setClientId(clientId);
        mMapView.setClickable(true);
        mMapView.setEnabled(true);
        mMapView.setFocusable(true);
        mMapView.setFocusableInTouchMode(true);
        mMapView.requestFocus();
        mMapView.setBuiltInZoomControls(true, null);
        //
    }

    @OnClick(R.id.search)
    public void onClickedSearch(View v){
        List<Item> list = mapPresenterImpl.loadSite("찜닭");
        Log.d("###",""+list.size());
    }

    @Override
    public void requestRefresh() {

    }
}
