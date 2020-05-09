package com.elbek.dn.test.di;

import android.app.Activity;
import android.app.Application;

import com.elbek.dn.test.di.components.AppComponent;
import com.elbek.dn.test.di.components.DaggerAppComponent;
import com.elbek.dn.test.di.modules.ContextModule;

public class BaseApp extends Application {

    private AppComponent appComponent;

    public static BaseApp get(Activity activity) {
        return (BaseApp) activity.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
    }

    public AppComponent getAppComponent(){
        return appComponent;
    }
}
