package com.elbek.dn.test.presenter;

import com.elbek.dn.test.view.MainActivity;

public interface BasePresenter {

    void loadFirstPage();

    void loadData(int page);

    void attachView(MainActivity view);

    void detachView();

}
