package com.longya.live.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.longya.live.R;
import com.longya.live.model.JsonBean;
import com.longya.live.presenter.theme.CreatorApplyPresenter;
import com.longya.live.presenter.user.SettingPresenter;
import com.longya.live.util.DialogUtil;
import com.longya.live.util.WordUtil;
import com.longya.live.view.MvpActivity;
import com.longya.live.view.theme.CreatorApplyView;
import com.longya.live.view.user.SettingView;

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
