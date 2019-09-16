package com.hongx.hxdagger2sub.di;

import dagger.Component;

@Component(modules = {HttpModule.class})
public interface HttpComponent {
    DatabaseComponent buildDatabaseComponent();
}
