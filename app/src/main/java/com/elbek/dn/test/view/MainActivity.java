package com.elbek.dn.test.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
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
import com.elbek.dn.test.view.adapter.RecyclerAdapter;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements IMainView {

    @Inject
    PresenterImpl presenter;

    private ProgressBar progressBar;
    private LinearLayout errorLayout;
    private SwipeRefreshLayout swipeRefreshLayout;

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;

    private ActivityComponent activityComponent;

    private int pageNum = 1;
    Boolean isScrolling = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initButtons();
        // Dugger init
        activityComponent = DaggerActivityComponent.builder()
                .appComponent(BaseApp.get(this).getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();

        activityComponent.inject(this);

        onScrollListener();
        getFirstPage();
    }

    private void initViews() {
        progressBar = findViewById(R.id.main_progress);
        errorLayout = findViewById(R.id.error_layout);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        //swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimaryDark));

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setInitialPrefetchItemCount(5);

        recyclerView = findViewById(R.id.recycler_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void initButtons() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (adapter != null) {
                    adapter.getNewsList().clear();
                    adapter.notifyDataSetChanged();
                    getFirstPage();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        findViewById(R.id.btn_retry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter == null)
                    getFirstPage();
                else
                    performPagination();
            }
        });
    }

    private void onScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int currentItems = linearLayoutManager.getChildCount();
                int totalItems = linearLayoutManager.getItemCount();
                int scrollOutItems = linearLayoutManager.findFirstVisibleItemPosition();

                if(isScrolling && (currentItems + scrollOutItems == totalItems)) {
                    isScrolling = false;
                    pageNum += 1;
                    performPagination();
                }
            }
        });
    }

    private void getFirstPage() {
        showLoading();
        presenter.loadFirstPage();
    }

    private void performPagination() {
        showLoading();
        presenter.loadData(pageNum);
    }

    @Override
    public void setData(List<Article> articles) {
        adapter.addNews(articles);
    }

    @Override
    public void setRecyclerAdapter(List<Article> articles) {
        adapter = new RecyclerAdapter(articles, MainActivity.this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showLogger(int page) {
        if (page == 1)
            Toast.makeText(MainActivity.this, "FIRST PAGE IS LOADED", Toast.LENGTH_SHORT).show();
        else if (page == 5)
            Toast.makeText(MainActivity.this, "NO MORE IMAGES", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(MainActivity.this, "PAGE NUM " + pageNum, Toast.LENGTH_SHORT).show();
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
    public void clear() {
    }
}