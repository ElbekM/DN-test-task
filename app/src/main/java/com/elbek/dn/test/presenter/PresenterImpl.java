package com.elbek.dn.test.presenter;

import com.elbek.dn.test.api.ApiService;
import com.elbek.dn.test.view.IMainView;

import javax.inject.Inject;


public class PresenterImpl implements IBasePresenter {

    private IMainView view;
    private ApiService apiService;

    @Inject
    public PresenterImpl(IMainView view, ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
    }

    @Override
    public void getFirstPage() {

    }

    @Override
    public void loadMore(final int page) {

    }
}
