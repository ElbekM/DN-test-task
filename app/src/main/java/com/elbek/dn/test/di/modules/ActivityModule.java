package com.elbek.dn.test.di.modules;


import com.elbek.dn.test.MainActivity;

import dagger.Module;

@Module
public class ActivityModule {
    private final MainActivity mainActivity;

    public ActivityModule(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
}
