package com.elbek.dn.test.di.components;

import com.elbek.dn.test.api.ApiService;
import com.elbek.dn.test.di.BaseApp;
import com.elbek.dn.test.di.modules.NetworkModule;
import com.elbek.dn.test.di.modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {

    void inject(BaseApp app);
    ApiService getApiService();

}
