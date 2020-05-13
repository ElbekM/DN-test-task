package com.elbek.dn.test.di.modules;

import com.elbek.dn.test.data.api.ApiService;
import com.elbek.dn.test.data.database.ArticleDao;
import com.elbek.dn.test.presenter.PresenterImpl;
import com.elbek.dn.test.view.interfaces.MainView;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    @Inject
    ApiService apiService;

    @Inject
    ArticleDao db;

    private MainView view;

    public ActivityModule(MainView view) {
        this.view = view;
    }

    @Provides
    public MainView provideView() {
        return view;
    }

    @Provides
    public PresenterImpl provideMainActivityPresenterImpl (ApiService apiService, ArticleDao db) {
        return new PresenterImpl(apiService, db);
    }
}
