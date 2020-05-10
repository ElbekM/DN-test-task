package com.elbek.dn.test.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.elbek.dn.test.model.Article;

import java.util.List;

@Dao
public interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Article article);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Article> items);

    @Delete
    void delete(Article article);

    @Query("SELECT * FROM articles")
    List<Article> getAllArticles();

}
