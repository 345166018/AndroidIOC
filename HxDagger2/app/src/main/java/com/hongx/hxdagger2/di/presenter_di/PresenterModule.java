package com.hongx.hxdagger2.di.presenter_di;

import com.hongx.hxdagger2.di.scope.UserScope;

import dagger.Module;
import dagger.Provides;

@UserScope
@Module
public class PresenterModule {
    @UserScope
    @Provides
    public Presenter providePresenter() {
        return new Presenter();
    }
}
