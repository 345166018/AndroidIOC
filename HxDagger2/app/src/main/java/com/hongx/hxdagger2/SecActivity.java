package com.hongx.hxdagger2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hongx.hxdagger2.object.HttpObject;

import javax.inject.Inject;

/**
 * @author: fuchenming
 * @create: 2019-09-12 14:31
 */
public class SecActivity extends AppCompatActivity {

    @Inject
    HttpObject httpObject;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);
//        DaggerMyComponent.create().injectSecActivity(this);

        ((MyApplication) getApplication()).getAppComponent().injectSecActivity(this);

        Log.i("hongxue","sec="+httpObject.hashCode()+"");



    }
}
