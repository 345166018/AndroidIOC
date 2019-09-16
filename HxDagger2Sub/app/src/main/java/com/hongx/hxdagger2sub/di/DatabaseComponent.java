package com.hongx.hxdagger2sub.di;


import com.hongx.hxdagger2sub.MainActivity;

import dagger.Subcomponent;

/**
 * 这就是一个子组件
 */
@Subcomponent(modules = {DatabaseModule.class})
public interface DatabaseComponent {
    void injectMainActivity(MainActivity mainActivity);
}
