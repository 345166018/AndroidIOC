package com.hongx.hxbutterknife;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.hongx.annotations.BindView;

/**
 * @author: fuchenming
 * @create: 2019-09-10 16:21
 */
public class SecondActivity extends AppCompatActivity {
    @BindView(R.id.tv_text3)
    TextView tvText3;

    @BindView(R.id.tv_text4)
    TextView tvText4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        HxButterKnife.bind(this);
        tvText3.setText("被设置了3");
        tvText4.setText("被设置了4");

    }
}
