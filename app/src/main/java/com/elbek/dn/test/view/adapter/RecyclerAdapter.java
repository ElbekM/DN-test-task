package com.elbek.dn.test.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.elbek.dn.test.R;
import com.elbek.dn.test.model.Article;
import com.elbek.dn.test.view.web.WebViewActivity;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>  {

    private List<Article> newsList;
    private Context context;

    public RecyclerAdapter(List<Article> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final Article news = newsList.get(position);
        holder.newsDate.setText(news.getPublishedAt());
        holder.newsTitle.setText(news.getTitle());
        holder.newsDesc.setText(news.getDescription());

        // Image load
        Glide.with(context).load(news.getUrlToImage()).into(holder.newsImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("url", news.getUrl());
                intent.putExtra("article", news.getTitle());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView newsImage;
        TextView newsDate;
        TextView newsTitle;
        TextView newsDesc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            newsImage = itemView.findViewById(R.id.news_image);
            newsDate = itemView.findViewById(R.id.news_date);
            newsTitle = itemView.findViewById(R.id.news_title);
            newsDesc = itemView.findViewById(R.id.news_desc);
        }
    }

    public void addNews(List<Article> news) {
        newsList.addAll(news);
        notifyDataSetChanged();
    }

    public List<Article> getNewsList() {
        return newsList;
    }
}
