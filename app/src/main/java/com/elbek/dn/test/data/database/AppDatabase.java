package com.elbek.dn.test.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.elbek.dn.test.model.Article;

@Database(entities = Article.class, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ArticleDao getArticleDao();

}
