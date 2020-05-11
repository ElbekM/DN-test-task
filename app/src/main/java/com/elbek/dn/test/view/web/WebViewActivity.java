package com.elbek.dn.test.view.web;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.elbek.dn.test.R;

public class WebViewActivity extends AppCompatActivity {

    private String url;
    private String article;

    private WebView webView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        url = getIntent().getStringExtra("url");
        article = getIntent().getStringExtra("article");
        setTitle(article);

        progressBar = findViewById(R.id.progress_bar_web);
        webView = findViewById (R.id.web_view);

        webView.loadUrl(url);
        progressBar.setVisibility(View.GONE);
        webView.setWebViewClient(new MyWebViewClient());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack())
            webView.goBack();
        else finish();
    }

}
