package com.example.kepler.lesssmarteditor.navermap;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kepler.lesssmarteditor.R;
import com.example.kepler.lesssmarteditor.navermap.api.NaverAPI;
import com.example.kepler.lesssmarteditor.navermap.api.SearchResult;
import com.example.kepler.lesssmarteditor.navermap.recyclerview.SiteAdapter;
import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MapActivity extends NMapActivity {
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
    private final String clientId = "sxWkb7RugVl3VTuLLacs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nmap);

        ButterKnife.bind(this);
        initNmap();

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
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
    public void onClickedSearch(){
        hideKeyboard();

        view.setAdapter(null);
        Retrofit client = new Retrofit.Builder().baseUrl("https://openapi.naver.com/").addConverterFactory(GsonConverterFactory.create()).build();
        NaverAPI service = client.create(NaverAPI.class);
        String address = et_address.getText().toString();
        Call<SearchResult> call = service.getSearch(address,100);

        call.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                if(response.isSuccessful()){
                    List<SearchResult.Item> list = response.body().items;
                    adapter = new SiteAdapter(list);
                    initRecyclerView();
                    view.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                Log.d("Retrofit/onFailure called",t.toString());
            }
        });
    }
    private void hideKeyboard(){
        imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),0);
    }
    private void initRecyclerView(){
        view = (RecyclerView)findViewById(R.id.map_recyclerView);
        manager = new LinearLayoutManager(this);
        view.setLayoutManager(manager);
        view.addItemDecoration(new DividerItemDecoration(view.getContext(),manager.getOrientation()));
    }

}
