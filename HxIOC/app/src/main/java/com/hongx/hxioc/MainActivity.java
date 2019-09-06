package com.hongx.hxioc;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewInject(R.id.btn_click)
    private Button button;

    @OnClick({R.id.btn_click2, R.id.btn_click3})
    public boolean click(View view) {
        if (view.getId() == R.id.btn_click2) {
            Log.i("hongxue", " MainActivity btn 2 click");
            Toast.makeText(this, "点击了button2", Toast.LENGTH_SHORT).show();
        }
        else if (view.getId() == R.id.btn_click3) {
            Log.i("hongxue", " MainActivity btn 3 click");
            Toast.makeText(this, "点击了button3", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    @OnLongClick({R.id.btn_click2,R.id.btn_click3})
    public boolean longClick(View view) {
        if (view.getId() == R.id.btn_click2) {
            Log.i("hongxue", " MainActivity btn 2 longlick");
            Toast.makeText(this, "长按了button2", Toast.LENGTH_SHORT).show();
        }
        else if (view.getId() == R.id.btn_click3) {
            Log.i("hongxue", " MainActivity btn 3 longClick");
            Toast.makeText(this, "长按了button3", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        Log.i("hongxue button string", button.toString());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "点击按钮", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
