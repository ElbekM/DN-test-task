package com.elbek.dn.test.di.modules;

import android.app.Application;

import com.elbek.dn.test.di.BaseApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final BaseApp app;

    public AppModule(BaseApp app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return app;
    }
}
