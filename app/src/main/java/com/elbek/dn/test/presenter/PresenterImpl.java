package com.elbek.dn.test.presenter;

import com.elbek.dn.test.data.api.ApiService;
import com.elbek.dn.test.data.database.ArticleDao;
import com.elbek.dn.test.model.Article;
import com.elbek.dn.test.model.NewsResponse;
import com.elbek.dn.test.view.interfaces.IMainView;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PresenterImpl implements IBasePresenter {

    private IMainView view;
    private ApiService apiService;
    private ArticleDao db;

    @Inject
    public PresenterImpl(IMainView view, ApiService apiService, ArticleDao db) {
        this.view = view;
        this.apiService = apiService;
        this.db = db;
    }

    @Override
    public void loadFirstPage() {
        view.clear();
        view.showLoading();
        Call<NewsResponse> call = apiService.getArticles("android", "2019-04-00", "publishedAt",
                "26eddb253e7840f988aec61f2ece2907", 1);

        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) {
                    List<Article> articles = response.body().getArticles();
                    db.insertAll(articles);
                    view.setData(articles);
                    view.hideLoading();
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                view.hideLoading();
                view.showError(t.getMessage());
            }
        });
    }

    @Override
    public void loadData(final int page) {
        Call<NewsResponse> call = apiService.getArticles("android", "2019-04-00", "publishedAt",
                "26eddb253e7840f988aec61f2ece2907", page);

        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) {
                    List<Article> articles = response.body().getArticles();
                    db.insertAll(articles);
                    view.setData(articles);
                } else {
                    view.displayToast();
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                view.displayErrorElement(true, t.getMessage());
            }
        });
    }

    @Override
    public void detachView() {
        view = null;
    }
}
