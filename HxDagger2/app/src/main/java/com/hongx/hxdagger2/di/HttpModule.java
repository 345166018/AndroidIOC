package com.hongx.hxdagger2.di;

import com.hongx.hxdagger2.di.scope.AppScope;
import com.hongx.hxdagger2.object.HttpObject;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author: fuchenming
 * @create: 2019-09-11 07:59
 */
//@Singleton
@AppScope
@Module
public class HttpModule {

//    @Singleton
    @AppScope
    @Provides
    public HttpObject providerHttpObject(){
        return new HttpObject();
    }

}
