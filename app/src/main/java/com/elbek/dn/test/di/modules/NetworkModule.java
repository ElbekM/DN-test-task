package com.elbek.dn.test.di.modules;

import com.elbek.dn.test.api.ApiService;
import com.elbek.dn.test.di.scope.AppScope;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = OkHttpClientModule.class)
public class NetworkModule {

    @Provides
    public ApiService apiService(Retrofit retrofit){
        return retrofit.create(ApiService.class);
    }

    @AppScope
    @Provides
    public Retrofit retrofit(OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
