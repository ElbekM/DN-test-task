package com.elbek.dn.test.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.elbek.dn.test.R;
import com.elbek.dn.test.di.BaseApp;
import com.elbek.dn.test.di.components.ActivityComponent;
import com.elbek.dn.test.di.components.DaggerActivityComponent;
import com.elbek.dn.test.di.modules.ActivityModule;
import com.elbek.dn.test.model.Article;
import com.elbek.dn.test.presenter.PresenterImpl;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements IMainView {

    @Inject
    PresenterImpl presenter;

    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Dugger init
        activityComponent = DaggerActivityComponent.builder()
                .appComponent(BaseApp.get(this).getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();

        activityComponent.inject(this);
    }

    // VIEW
    @Override
    public void setData(List<Article> articles) {
    }

    @Override
    public void showLogger(int page) {
    }

    @Override
    public void setRecyclerAdapter(List<Article> articles) {
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String message) {
    }

    @Override
    public void showFooterError(boolean show, String errorMsg) {
    }

    @Override
    public void clear() {
    }
}
