package com.onecric.live.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.onecric.live.R;
import com.onecric.live.view.BaseActivity;

public class NewsActivity extends BaseActivity {
    public static void forward(Context context) {
        Intent intent = new Intent(context, NewsActivity.class);
        context.startActivity(intent);
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_news;
    }

    @Override
    protected void initView() {
        findViewById(R.id.ll_title).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.tv_title)).setText(getString(R.string.more2));
        findViewById(R.id.iv_back).setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    protected void initData() {

    }
}
