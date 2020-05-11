package com.elbek.dn.test.view.interfaces;

import com.elbek.dn.test.model.Article;

import java.util.List;

public interface IMainView {

    void setData(List<Article> articles);

    void showLoading();

    void hideLoading();

    void showError(String message);

    void displayErrorElement(boolean show, String message);

    void displayToast();

    void clear();
}
