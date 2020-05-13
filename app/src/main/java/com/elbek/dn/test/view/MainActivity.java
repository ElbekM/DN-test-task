package com.elbek.dn.test.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.elbek.dn.test.R;
import com.elbek.dn.test.di.BaseApp;
import com.elbek.dn.test.di.components.ActivityComponent;
import com.elbek.dn.test.di.components.DaggerActivityComponent;
import com.elbek.dn.test.di.modules.ActivityModule;
import com.elbek.dn.test.model.Article;
import com.elbek.dn.test.presenter.PresenterImpl;
import com.elbek.dn.test.view.adapter.ArticleAdapter;
import com.elbek.dn.test.view.interfaces.PageLoading;
import com.elbek.dn.test.view.interfaces.MainView;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainView {

    @Inject
    PresenterImpl presenter;

    private ProgressBar progressBar;
    private LinearLayout errorLayout;
    private SwipeRefreshLayout swipeRefreshLayout;

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private ArticleAdapter adapter;

    private ActivityComponent activityComponent;

    private boolean isLoading = false;
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initButtons();

        // Dagger init
        activityComponent = DaggerActivityComponent.builder()
                .appComponent(BaseApp.get(this).getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();

        activityComponent.inject(this);

        onScrollListener();
        presenter.attachView(this);
        presenter.loadFirstPage();
    }

    private void initViews() {
        progressBar = findViewById(R.id.main_progress);
        errorLayout = findViewById(R.id.error_layout);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setInitialPrefetchItemCount(5);

        recyclerView = findViewById(R.id.recycler_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new ArticleAdapter(new PageLoading() {
            @Override
            public void retryLoadingPage() {
                presenter.loadData(currentPage);
            }
        }, MainActivity.this);

        recyclerView.setAdapter(adapter);
    }

    private void initButtons() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                presenter.loadFirstPage();
            }
        });

        findViewById(R.id.btn_loading_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loadFirstPage();
            }
        });
    }

    private void onScrollListener() {
        // Pagination logic
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {  //isScrolling
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

                if (!isLoading) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        isLoading = true;
                        currentPage += 1;
                        presenter.loadData(currentPage);
                    }
                }
            }
        });
    }

    @Override
    public void setData(List<Article> articles) {
        adapter.removeLoadingElement();
        isLoading = false;
        adapter.addAll(articles);
        adapter.addLoadingElement();
    }

    @Override
    public void showLoading() {
        recyclerView.setVisibility(View.VISIBLE);
        errorLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        recyclerView.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayErrorElement(boolean show, String message) {
        adapter.displayRetryElement(show);
    }

    @Override
    public void displayToast() {
        adapter.removeLoadingElement();
        Toast.makeText(MainActivity.this, "Last page :(", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clear() {
        adapter.clear();
        adapter.displayRetryElement(false);
        currentPage = 1;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}