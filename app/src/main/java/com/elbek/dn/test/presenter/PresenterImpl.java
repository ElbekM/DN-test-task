package com.elbek.dn.test.presenter;

import com.elbek.dn.test.data.api.ApiService;
import com.elbek.dn.test.data.database.ArticleDao;
import com.elbek.dn.test.model.NewsResponse;
import com.elbek.dn.test.view.MainActivity;
import com.elbek.dn.test.view.interfaces.IMainView;

import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class PresenterImpl implements IBasePresenter {

    private final static String LAST_PAGE = "HTTP 426 Upgrade Required";

    private IMainView view;
    private ApiService apiService;
    private ArticleDao db;

    @Inject
    public PresenterImpl(ApiService apiService, ArticleDao db) {
        this.apiService = apiService;
        this.db = db;
    }

    @Override
    public void loadFirstPage() {
        view.clear();
        view.showLoading();
        Observable<NewsResponse> call = apiService.getArticles("android", "2019-04-00", "publishedAt",
                "26eddb253e7840f988aec61f2ece2907", 1);

        call.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewsResponse newsResponse) {
                        db.insertAll(newsResponse.getArticles());
                        view.setData(newsResponse.getArticles());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.hideLoading();
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        view.hideLoading();
                    }
                });
    }

    @Override
    public void loadData(final int page) {
        Observable<NewsResponse> call = apiService.getArticles("android", "2019-04-00", "publishedAt",
                "26eddb253e7840f988aec61f2ece2907", page);

        call.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewsResponse newsResponse) {
                        if (newsResponse == null) {
                            view.displayToast();
                        } else {
                            db.insertAll(newsResponse.getArticles());
                            view.setData(newsResponse.getArticles());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (Objects.equals(e.getMessage(), LAST_PAGE))
                            view.displayToast();
                        else
                            view.displayErrorElement(true, e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void attachView(MainActivity view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }
}
