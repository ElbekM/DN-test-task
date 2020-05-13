package com.elbek.dn.test.presenter;

import com.elbek.dn.test.view.MainActivity;

public interface IBasePresenter {

    void loadFirstPage();

    void loadData(int page);

    void attachView(MainActivity view);

    void detachView();

}
