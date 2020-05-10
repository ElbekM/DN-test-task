package com.elbek.dn.test.di.components;

import com.elbek.dn.test.view.MainActivity;
import com.elbek.dn.test.di.modules.ActivityModule;
import com.elbek.dn.test.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = AppComponent.class,
        modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity mainActivity);
    //void inject(PresenterImpl presenter);
    //void inject(IMainView mainView);
}
