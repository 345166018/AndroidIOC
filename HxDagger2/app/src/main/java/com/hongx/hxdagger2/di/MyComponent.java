package com.hongx.hxdagger2.di;

import com.hongx.hxdagger2.MainActivity;
import com.hongx.hxdagger2.SecActivity;
import com.hongx.hxdagger2.di.presenter_di.PresenterComponent;
import com.hongx.hxdagger2.di.scope.AppScope;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;

/**
 * @author: fuchenming
 * @create: 2019-09-11 08:51
 */

/**
 * 这就是一个组件
 *
 * @Singleton只是一个模板，我们需要scope都自定义 dependencies:组件依赖
 * 1.多个组件之间的scope不能相同
 * 2.没有scope的不能依赖有scope的组件
 */
//@Singleton
@AppScope
@Component(modules = {HttpModule.class, DatabaseModule.class},
        dependencies = {PresenterComponent.class})
public interface MyComponent {
    void injectMainActivity(MainActivity mainActivity);

    void injectSecActivity(SecActivity secActivity);
}
