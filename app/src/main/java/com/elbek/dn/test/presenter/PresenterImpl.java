package com.elbek.dn.test.presenter;

import com.elbek.dn.test.api.ApiService;
import com.elbek.dn.test.model.Article;
import com.elbek.dn.test.model.NewsResponse;
import com.elbek.dn.test.view.IMainView;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PresenterImpl implements IBasePresenter {

    private IMainView view;
    private ApiService apiService;

    @Inject
    public PresenterImpl(IMainView view, ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
    }

    @Override
    public void loadFirstPage() {
        Call<NewsResponse> call = apiService.getArticles("android", "2019-04-00", "publishedAt",
                "26eddb253e7840f988aec61f2ece2907", 1);

        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) {
                    List<Article> articles = response.body().getArticles();
                    view.setRecyclerAdapter(articles);
                    view.showLogger(1);
                    view.hideLoading();
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                System.out.println(t.getMessage());
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
                    view.setData(articles);
                    view.showLogger(page);
                } else {
                    view.showLogger(5);
                }
                view.hideLoading();
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                System.out.println(t.getMessage());
                view.hideLoading();
                view.showError(t.getMessage());
            }
        });
    }
}
