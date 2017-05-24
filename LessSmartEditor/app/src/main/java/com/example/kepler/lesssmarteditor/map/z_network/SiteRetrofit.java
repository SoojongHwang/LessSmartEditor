package com.example.kepler.lesssmarteditor.map.z_network;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Kepler on 2017-05-24.
 */

public class SiteRetrofit {
    static Retrofit client = new Retrofit.Builder().baseUrl("https://openapi.naver.com/").addConverterFactory(GsonConverterFactory.create()).build();

    public List<Item> callAPIfromServer(String str){
        final List[] result = new List[1];

        APIRequest service = client.create(APIRequest.class);
        Call<SearchResult> call= service.getSearch(str,20);

        call.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                if(response.isSuccessful()){
                    result[0] = response.body().items;
                }
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {

            }
        });
        return result[0];
    }
}