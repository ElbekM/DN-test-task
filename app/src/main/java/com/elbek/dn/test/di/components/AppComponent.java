package com.elbek.dn.test.di.components;

import com.elbek.dn.test.api.ApiService;
import com.elbek.dn.test.di.modules.NetworkModule;
import com.elbek.dn.test.di.scope.AppScope;

import dagger.Component;

@AppScope
@Component(modules = NetworkModule.class)
public interface AppComponent {

    ApiService getApiService();

}
