package com.hongx.hxdagger2;

import android.app.Application;

import com.hongx.hxdagger2.di.DaggerMyComponent;
import com.hongx.hxdagger2.di.HttpModule;
import com.hongx.hxdagger2.di.MyComponent;
import com.hongx.hxdagger2.di.presenter_di.DaggerPresenterComponent;

/**
 * @author: fuchenming
 * @create: 2019-09-16 08:28
 */
public class MyApplication extends Application {

    private MyComponent myComponent;

    @Override
    public void onCreate() {
        super.onCreate();
//        myComponent = DaggerMyComponent.create();
        myComponent = DaggerMyComponent
                .builder()
                .httpModule(new HttpModule())
                .presenterComponent(DaggerPresenterComponent.create())
                .build();
    }

    public MyComponent getAppComponent() {
        return myComponent;
    }

}
