package com.elbek.dn.test.data.api;

import com.elbek.dn.test.model.NewsResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("everything")
    Observable<NewsResponse> getArticles(@Query("q") String query,
                                         @Query("from") String date,
                                         @Query("sortBy") String sortBy,
                                         @Query("apiKey") String apiKey,
                                         @Query("page") int page);
}
