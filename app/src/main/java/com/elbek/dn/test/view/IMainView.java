package com.elbek.dn.test.view;

import com.elbek.dn.test.model.Article;

import java.util.List;

public interface IMainView {

    void showLogger(int page);

    void setRecyclerAdapter(List<Article> articles);

    void setData(List<Article> articles);

    void showLoading();

    void hideLoading();

    void showError(String message);

    void showFooterError(boolean show, String errorMsg);

    void clear();
}
