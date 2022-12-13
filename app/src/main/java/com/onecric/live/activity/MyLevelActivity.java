package com.onecric.live.activity;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.adapter.MyLevelAdapter;
import com.onecric.live.model.JsonBean;
import com.onecric.live.presenter.user.MyLevelPresenter;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.user.MyLevelView;

import java.util.Arrays;

public class MyLevelActivity extends MvpActivity<MyLevelPresenter> implements MyLevelView, View.OnClickListener {

    public static void forward(Context context) {
        Intent intent = new Intent(context, MyLevelActivity.class);
        context.startActivity(intent);
    }

    private ImageView iv_avatar;
    private TextView tv_name;
    private TextView tv_desc_one;
    private ImageView iv_level;
    private RecyclerView recyclerview;
    private MyLevelAdapter mAdapter;

    @Override
    protected MyLevelPresenter createPresenter() {
        return new MyLevelPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_level;
    }

    @Override
    protected void initView() {
        setTitleText(getString(R.string.user_my_level));
        iv_avatar = findViewById(R.id.iv_avatar);
        tv_name = findViewById(R.id.tv_name);
        tv_desc_one = findViewById(R.id.tv_desc_one);
        iv_level = findViewById(R.id.iv_level);
        recyclerview = findViewById(R.id.recyclerview);
    }

    @Override
    protected void initData() {
        if (CommonAppConfig.getInstance().getUserBean() != null) {
            GlideUtil.loadUserImageDefault(this, CommonAppConfig.getInstance().getUserBean().getAvatar(), iv_avatar);
            GlideUtil.loadUserImageDefault(this, CommonAppConfig.getInstance().getUserBean().getExp_icon(), iv_level);
            if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getUserBean().getUser_nickname())) {
                tv_name.setText(CommonAppConfig.getInstance().getUserBean().getUser_nickname());
            }
            String one = "您目前财富经验值为";
            String two = String.valueOf(CommonAppConfig.getInstance().getUserBean().getCurrent_exp());
            String three = "，再有";
            String four = String.valueOf(CommonAppConfig.getInstance().getUserBean().getDifference());
            String five = "财富经验值就能获得";
            SpannableStringBuilder stringBuilder = new SpannableStringBuilder(one+two+three+four+five);
            stringBuilder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.c_DC7841)), one.length(), one.length()+two.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            stringBuilder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.c_DC7841)), one.length()+two.length()+three.length(), one.length()+two.length()+three.length()+four.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            tv_desc_one.setText(stringBuilder);
        }
        mAdapter = new MyLevelAdapter(R.layout.item_my_level, Arrays.asList("", "", "", "", "", ""));
        recyclerview.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerview.setAdapter(mAdapter);
    }

    @Override
    public void getDataSuccess(JsonBean model) {

    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
