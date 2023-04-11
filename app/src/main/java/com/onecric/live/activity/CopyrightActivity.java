package com.onecric.live.activity;

import android.content.Context;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.onecric.live.R;
import com.onecric.live.view.BaseActivity;

public class CopyrightActivity extends BaseActivity {
    TextView tv_main;

    public static void forward(Context context) {
        Intent starter = new Intent(context, CopyrightActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_copyright;
    }

    @Override
    protected void initView() {
        tv_main = findViewById(R.id.tv_main);
        findViewById(R.id.btn_left).setOnClickListener(v->{
            finish();
        });
        tv_main.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    @Override
    protected void initData() {

    }
}
