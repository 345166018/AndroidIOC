package com.hongx.hxdagger2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hongx.hxdagger2.di.DaggerMyComponent;
import com.hongx.hxdagger2.di.DatabaseModule;
import com.hongx.hxdagger2.di.HttpModule;
import com.hongx.hxdagger2.di.presenter_di.Presenter;
import com.hongx.hxdagger2.object.HttpObject;
import com.hongx.hxdagger2.object.DatabaseObject;
import com.hongx.hxdagger2.object.HttpObject;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    HttpObject httpObject;
    @Inject
    HttpObject httpObject2;

    @Inject
    DatabaseObject databaseObject;

    @Inject
    Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        DaggerMyComponent.create().injectMainActivity(this);

//        DaggerMyComponent.builder().httpModule(new HttpModule()).build().injectMainActivity(this);

        ((MyApplication) getApplication()).getAppComponent().injectMainActivity(this);

        Log.i("hongxue", httpObject.hashCode() + "");
        Log.i("hongxue", httpObject2.hashCode() + "");
        Log.i("hongxue", databaseObject.hashCode() + "");
        Log.i("hongxue", "presenter " + presenter.hashCode() + "");


    }

    public void toNext(View view) {

        Intent intent = new Intent(MainActivity.this, SecActivity.class);
        startActivity(intent);

    }
}
