package com.example.kepler.lesssmarteditor.navermap;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.kepler.lesssmarteditor.R;
import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapActivity extends NMapActivity {
    @BindView(R.id.nmapview)
    NMapView mMapView;
    @BindView(R.id.address)
    EditText et_address;
    @BindView(R.id.search)
    Button searchButton;
    @BindView(R.id.listview)
    ListView listView;

    private final String clientId = "sxWkb7RugVl3VTuLLacs";
    private final String clientSecret = "WpdcBouYSB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nmap);

        ButterKnife.bind(this);
        init();

    }
    private void init(){
        mMapView.setClientId(clientId);
        mMapView.setClickable(true);
        mMapView.setEnabled(true);
        mMapView.setFocusable(true);
        mMapView.setFocusableInTouchMode(true);
        mMapView.requestFocus();
        mMapView.setBuiltInZoomControls(true, null);
        //

    }
}
