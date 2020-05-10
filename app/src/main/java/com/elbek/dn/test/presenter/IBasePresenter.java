package com.elbek.dn.test.presenter;

public interface IBasePresenter {

    void loadFirstPage();

    void loadData(int page);

    void attachView(Object mvpView);

    void detachView();

    void destroy();
}
