package com.onecric.live.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.CommonCode;
import com.onecric.live.R;
import com.onecric.live.adapter.GroupSelectAdapter;
import com.onecric.live.model.ThemeClassifyBean;
import com.onecric.live.presenter.theme.GroupSelectPresenter;
import com.onecric.live.util.WordUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.theme.GroupSelectView;

import java.util.List;

public class GroupSelectActivity extends MvpActivity<GroupSelectPresenter> implements GroupSelectView, View.OnClickListener {

    public static void forwardForResult(Activity activity) {
        Intent intent = new Intent(activity, GroupSelectActivity.class);
        activity.startActivityForResult(intent, CommonCode.REQUEST_CODE_GROUP_SELECT);
    }

    private RecyclerView recyclerView;
    private GroupSelectAdapter mAdapter;

    @Override
    protected GroupSelectPresenter createPresenter() {
        return new GroupSelectPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_group_select;
    }

    @Override
    protected void initView() {
        setTitleText(WordUtil.getString(this, R.string.select_group));

        recyclerView = findViewById(R.id.recyclerView);
    }

    @Override
    protected void initData() {
        mvpPresenter.getData();
    }

    @Override
    public void getDataSuccess(List<ThemeClassifyBean> list) {
        if (list != null) {
            mAdapter = new GroupSelectAdapter(R.layout.item_group_select, list);
            mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent = new Intent();
                    intent.putExtra("id", mAdapter.getItem(position).getId());
                    intent.putExtra("name", mAdapter.getItem(position).getName());
                    setResult(CommonCode.RESULT_CODE_GROUP_SELECT, intent);
                    finish();
                }
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(mAdapter);
        }
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
