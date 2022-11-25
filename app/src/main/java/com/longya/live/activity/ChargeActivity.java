package com.longya.live.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.longya.live.CommonAppConfig;
import com.longya.live.R;
import com.longya.live.adapter.ChargeAdapter;
import com.longya.live.model.CoinBean;
import com.longya.live.model.JsonBean;
import com.longya.live.presenter.user.ChargePresenter;
import com.longya.live.util.ToastUtil;
import com.longya.live.view.MvpActivity;
import com.longya.live.view.user.ChargeView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChargeActivity extends MvpActivity<ChargePresenter> implements ChargeView, View.OnClickListener {

    public static void forward(Context context) {
        Intent intent = new Intent(context, ChargeActivity.class);
        context.startActivity(intent);
    }

    private TextView tv_diamond_count;
    private LinearLayout ll_ali;
    private TextView tv_ali;
    private LinearLayout ll_wechat;
    private TextView tv_wechat;
    private RecyclerView recyclerview;
    private ChargeAdapter mAdapter;
    private int mSelectPosition;

    @Override
    protected ChargePresenter createPresenter() {
        return new ChargePresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_charge;
    }

    @Override
    protected void initView() {
        setTitleText(getString(R.string.user_charge));

        TextView right_title = findViewById(R.id.right_title);
        right_title.setText(getString(R.string.charge_detail));
        right_title.setTextSize(17);
        right_title.setTextColor(getResources().getColor(R.color.c_E3AC72));

        tv_diamond_count = findViewById(R.id.tv_diamond_count);
        ll_ali = findViewById(R.id.ll_ali);
        tv_ali = findViewById(R.id.tv_ali);
        ll_wechat = findViewById(R.id.ll_wechat);
        tv_wechat = findViewById(R.id.tv_wechat);
        recyclerview = findViewById(R.id.recyclerview);

        right_title.setOnClickListener(this);
        ll_ali.setOnClickListener(this);
        ll_wechat.setOnClickListener(this);
        findViewById(R.id.tv_pay).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        ll_ali.setSelected(true);
        if (CommonAppConfig.getInstance().getUserBean() != null && !TextUtils.isEmpty(CommonAppConfig.getInstance().getUserBean().getBalance())) {
            tv_diamond_count.setText(CommonAppConfig.getInstance().getUserBean().getBalance());
        }
        mAdapter = new ChargeAdapter(R.layout.item_charge, new ArrayList<>());
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mSelectPosition = position;
                for (int i = 0; i < mAdapter.getData().size(); i++) {
                    if (i == position) {
                        mAdapter.getItem(position).setSelect(true);
                    }else {
                        mAdapter.getItem(i).setSelect(false);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        recyclerview.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerview.setAdapter(mAdapter);

        mvpPresenter.getList();
    }

    @Override
    public void getDataSuccess(List<CoinBean> list) {
        if (list != null) {
            if (list.size() > 0) {
                list.get(0).setSelect(true);
            }
            mAdapter.setNewData(list);
        }
    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void paySuccess(String url) {
        dismissLoadingDialog();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        startActivity(intent);
    }

    @Override
    public void payFail(String msg) {
        dismissLoadingDialog();
        ToastUtil.show(msg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_title:
                if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                    MyWalletActivity.forward(this, 0);
                }
                break;
            case R.id.ll_ali:
                if (ll_ali.isSelected()) {
                    return;
                }
                ll_ali.setSelected(true);
                tv_ali.setTextColor(getResources().getColor(R.color.c_E3AC72));
                ll_wechat.setSelected(false);
                tv_wechat.setTextColor(getResources().getColor(R.color.c_333333));
                break;
            case R.id.ll_wechat:
                if (ll_wechat.isSelected()) {
                    return;
                }
                ll_ali.setSelected(false);
                tv_ali.setTextColor(getResources().getColor(R.color.c_333333));
                ll_wechat.setSelected(true);
                tv_wechat.setTextColor(getResources().getColor(R.color.c_E3AC72));
                break;
            case R.id.tv_pay:
                showLoadingDialog();
                mvpPresenter.doPay(mAdapter.getItem(mSelectPosition).getId(), ll_ali.isSelected()?1:0);
                break;
        }
    }
}
