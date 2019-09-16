package com.hongx.hxdagger2sub.di;


import com.hongx.hxdagger2sub.object.HttpObject;

import java.util.ArrayList;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * @author: fuchenming
 * @create: 2019-09-11 07:59
 */
@Module
public class HttpModule {

    ArrayList<String> baseUrl;

    public HttpModule(ArrayList baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Named("base1")
    @Provides
    public HttpObject providerHttpObject1() {
        return new HttpObject(baseUrl.get(0));
    }

    @Named("base2")
    @Provides
    public HttpObject providerHttpObject2() {
        return new HttpObject(baseUrl.get(1));
    }


}
