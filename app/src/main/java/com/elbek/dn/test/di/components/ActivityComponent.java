package com.elbek.dn.test.di.components;

import com.elbek.dn.test.MainActivity;
import com.elbek.dn.test.di.modules.ActivityModule;
import com.elbek.dn.test.di.scope.ActivityScope;

import dagger.Component;

@Component(modules = ActivityModule.class, dependencies = AppComponent.class)
@ActivityScope
public interface ActivityComponent {
    void injectActivity(MainActivity mainActivity);
}
