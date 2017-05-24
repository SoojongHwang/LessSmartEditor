package com.example.kepler.lesssmarteditor.map.z_network;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by Kepler on 2017-05-24.
 */

public interface APIRequest {
    @Headers({"X-Naver-Client-Id:sxWkb7RugVl3VTuLLacs", "X-Naver-Client-Secret:WpdcBouYSB"})
    @GET("v1/search/local.json")
    Call<SearchResult> getSearch(@Query("query") String text, @Query("display") int count);
}
