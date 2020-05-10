package com.elbek.dn.test.data.api;

import com.elbek.dn.test.model.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("everything")
    Call<NewsResponse> getArticles(@Query("q") String query,
                                   @Query("from") String date,
                                   @Query("sortBy") String sortBy,
                                   @Query("apiKey") String apiKey,
                                   @Query("page") int page);
}
