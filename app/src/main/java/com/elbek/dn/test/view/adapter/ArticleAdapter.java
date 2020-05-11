package com.elbek.dn.test.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.elbek.dn.test.R;
import com.elbek.dn.test.model.Article;
import com.elbek.dn.test.view.interfaces.IPageLoading;
import com.elbek.dn.test.view.web.WebViewActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private static final int ARTICLES_ITEM_VIEW = 0;
    private static final int LOADING_ITEM_VIEW = 1;

    private Context context;
    private List<Article> articleList;
    private IPageLoading paginationWork;

    private boolean isLoadingAdded = false;
    private boolean retryPage = false;

    public ArticleAdapter(IPageLoading paginationWork, Context context) {
        articleList = new ArrayList<>();
        this.paginationWork = paginationWork;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // Choose what element need to display
        if (viewType == ARTICLES_ITEM_VIEW) {
            View articleView = inflater.inflate(R.layout.item_list, parent, false);
            viewHolder = new ArticlesViewHolder(articleView);
        } else {
            View loadingView = inflater.inflate(R.layout.item_loading, parent, false);
            viewHolder = new LoadingViewHolder(loadingView);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ARTICLES_ITEM_VIEW) {
            ((ArticlesViewHolder) holder).bind(articleList.get(position));
        } else {
            ((LoadingViewHolder) holder).bind();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == articleList.size() - 1 && isLoadingAdded)
            return LOADING_ITEM_VIEW;
        return ARTICLES_ITEM_VIEW;
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public void addAll(List<Article> news) {
        articleList.addAll(news);
        int size = articleList.size();
        notifyItemRangeInserted(size == 0 ? 0 : size - 1, news.size());
    }

    public void addLoadingElement() {
        isLoadingAdded = true;
        articleList.add(null);
        notifyItemInserted(articleList.size() - 1);
    }

    public void removeLoadingElement() {
        isLoadingAdded = false;
        if (getItemCount() > 0) {
            int position = articleList.size() - 1;
            if (articleList.get(position) == null) {
                articleList.remove(position);
                notifyItemRemoved(position);
            }
        }
    }

    public void displayRetryElement(boolean display) {
        retryPage = display;
        notifyItemChanged(articleList.size() - 1);
    }

    public void clear(){
        int size = articleList.size();
        articleList.clear();
        notifyItemRangeRemoved(0, size);
    }

     class ArticlesViewHolder extends RecyclerView.ViewHolder {

        ImageView newsImage;
        TextView newsDate;
        TextView newsTitle;
        TextView newsDesc;

        ArticlesViewHolder(@NonNull View itemView) {
            super(itemView);
            newsImage = itemView.findViewById(R.id.news_image);
            newsDate = itemView.findViewById(R.id.news_date);
            newsTitle = itemView.findViewById(R.id.news_title);
            newsDesc = itemView.findViewById(R.id.news_desc);
        }

        void bind(final Article news) {
            newsDate.setText(validDate(news.getPublishedAt()));
            newsTitle.setText(news.getTitle());
            newsDesc.setText(news.getDescription());

            // Image load
            Glide.with(context).load(news.getUrlToImage()).into(newsImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra("url", news.getUrl());
                    intent.putExtra("article", news.getTitle());
                    context.startActivity(intent);
                }
            });
        }
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {

        private ProgressBar progressBar;
        private LinearLayout errorLayout;

        LoadingViewHolder(@NonNull View itemView) {
            super(itemView);

            progressBar = itemView.findViewById(R.id.loading_progress);
            errorLayout = itemView.findViewById(R.id.loading_error_layout);

            errorLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    displayRetryElement(false);
                    paginationWork.retryLoadingPage();
                }
            });
        }

        void bind() {
            if (retryPage) {
                errorLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            } else {
                errorLayout.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
            }
        }
    }

    private static String validDate(String dateTime) {
        String resultDate = dateTime;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat output = new SimpleDateFormat("dd MMMM yyyy - HH:mm", Locale.ENGLISH);
        try {
            Date d = sdf.parse(dateTime);
            resultDate = output.format(d);
        } catch (ParseException e) {
            //e.getMessage();
        }
        return resultDate;
    }
}
