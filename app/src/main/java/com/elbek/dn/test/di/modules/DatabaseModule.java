package com.elbek.dn.test.di.modules;

import android.app.Application;

import androidx.room.Room;

import com.elbek.dn.test.data.database.AppDatabase;
import com.elbek.dn.test.data.database.ArticleDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Singleton
    @Provides
    AppDatabase provideAppDatabase(Application context) {
        return Room.databaseBuilder(context, AppDatabase.class, "articles-database")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    ArticleDao provideArticleDao(AppDatabase appDatabase) {
        return appDatabase.getArticleDao();
    }

}
