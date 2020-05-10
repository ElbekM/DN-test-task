package com.elbek.dn.test.di;

import android.app.Application;
import android.content.Context;

import com.elbek.dn.test.di.components.AppComponent;
import com.elbek.dn.test.di.components.DaggerAppComponent;
import com.elbek.dn.test.di.modules.AppModule;

public class BaseApp extends Application {

    private AppComponent appComponent;

    public static BaseApp get(Context context) {
        return (BaseApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        buildGraphAndInject();

    }

    private void buildGraphAndInject() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        appComponent.inject(this);
    }

    public AppComponent getAppComponent(){
        return appComponent;
    }
}
