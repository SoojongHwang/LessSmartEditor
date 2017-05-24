package com.example.kepler.maptest.map.model;

import com.example.kepler.maptest.map.presenter.MapPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Kepler on 2017-05-24.
 */

public class MapService {
    static final Retrofit client = new Retrofit.Builder().baseUrl("https://openapi.naver.com/").addConverterFactory(GsonConverterFactory.create()).build();
    public MapPresenter mPresenter;

    public MapService(MapPresenter presenter) {
        this.mPresenter = presenter;
    }

    public void callSearchAPI(String str) {
        final APIRequest service = client.create(APIRequest.class);
        Call<SearchResult> call = service.getSearch(str, 20);

        call.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                if (response.isSuccessful()) {
                    mPresenter.notifyAPIResult(response.body().items);
                }
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                mPresenter.notifyAPIFailed(t.toString());
            }
        });

    }


}
