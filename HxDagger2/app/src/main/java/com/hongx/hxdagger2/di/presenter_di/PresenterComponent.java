package com.hongx.hxdagger2.di.presenter_di;

import com.hongx.hxdagger2.MainActivity;
import com.hongx.hxdagger2.di.scope.UserScope;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author: fuchenming
 * @create: 2019-09-16 09:15
 */
//@Singleton
@UserScope
@Component(modules = {PresenterModule.class})
public interface PresenterComponent {
//    void injectMainActivity(MainActivity mainActivity);
    Presenter providePresenter();
}
