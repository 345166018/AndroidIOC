package com.hongx.hxdagger2sub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hongx.hxdagger2sub.di.DaggerHttpComponent;
import com.hongx.hxdagger2sub.di.HttpComponent;
import com.hongx.hxdagger2sub.di.HttpModule;
import com.hongx.hxdagger2sub.object.DatabaseObject;
import com.hongx.hxdagger2sub.object.HttpObject;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Named;

public class MainActivity extends AppCompatActivity {

    String url1 = "www.sina.com.cn";
    String url2 = "www.163.com.cn";

    @Inject
    @Named("base1")
    HttpObject httpObject1;

    @Inject
    @Named("base2")
    HttpObject httpObject2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> url = new ArrayList<>();
        url.add(url1);
        url.add(url2);

        DaggerHttpComponent.builder().httpModule(new HttpModule(url)).build()
                .buildDatabaseComponent().injectMainActivity(this);

//        DaggerHttpComponent.create().buildDatabaseComponent().injectMainActivity(this);

        Log.i("hongxue", httpObject1.baseUrl + "");
        Log.i("hongxue", httpObject2.baseUrl + "");

    }
}
