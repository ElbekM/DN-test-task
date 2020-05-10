package com.elbek.dn.test.di.modules;

import com.elbek.dn.test.api.ApiService;
import com.elbek.dn.test.presenter.PresenterImpl;
import com.elbek.dn.test.view.IMainView;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    @Inject
    ApiService apiService;

    private IMainView view;

    public ActivityModule(IMainView view) {
        this.view = view;
    }

    @Provides
    public IMainView provideView() {
        return view;
    }

    @Provides
    public PresenterImpl provideMainActivityPresenterImpl (IMainView view, ApiService apiService) {    //???
        return new PresenterImpl(view, apiService);
    }
}
