package com.elbek.dn.test.di.components;

import com.elbek.dn.test.data.api.ApiService;
import com.elbek.dn.test.data.database.ArticleDao;
import com.elbek.dn.test.di.BaseApp;
import com.elbek.dn.test.di.modules.DatabaseModule;
import com.elbek.dn.test.di.modules.NetworkModule;
import com.elbek.dn.test.di.modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class, DatabaseModule.class})
public interface AppComponent {

    void inject(BaseApp app);
    ApiService getApiService();
    ArticleDao getArticleDao();

}
