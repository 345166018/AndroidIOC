package com.hongx.hxdagger2.di;

import com.hongx.hxdagger2.object.DatabaseObject;

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











