package com.onecric.live.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.onecric.live.R;
import com.onecric.live.model.JsonBean;
import com.onecric.live.presenter.theme.CreatorApplyPresenter;
import com.onecric.live.util.WordUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.theme.CreatorApplyView;

public class CreatorApplyActivity extends MvpActivity<CreatorApplyPresenter> implements CreatorApplyView, View.OnClickListener {

    public static void forward(Context context) {
        Intent intent = new Intent(context, CreatorApplyActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected CreatorApplyPresenter createPresenter() {
        return new CreatorApplyPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_creator_apply;
    }

    @Override
    protected void initView() {
        setTitleText(WordUtil.getString(this, R.string.title_creator_apply));

    }

    @Override
    protected void initData() {
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
