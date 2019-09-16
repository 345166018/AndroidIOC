package com.hongx.hxdagger2sub.di;


import com.hongx.hxdagger2sub.object.DatabaseObject;

import dagger.Module;
import dagger.Provides;

/**
 * 用来提供对象
 */
@Module
public class DatabaseModule {

    @Provides
    public DatabaseObject providerDatabaseObject() {
        return new DatabaseObject();
    }
}











