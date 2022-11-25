package com.longya.live.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.longya.live.R;
import com.longya.live.activity.HeadlineDetailActivity;
import com.longya.live.adapter.CricketPointsAdapter;
import com.longya.live.adapter.CricketUpdatesAdapter;
import com.longya.live.custom.ItemDecoration;
import com.longya.live.model.HeadlineBean;
import com.longya.live.model.UpdatesBean;
import com.longya.live.view.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/26
 */
public class CricketUpdatesFragment extends BaseFragment {
    public static CricketUpdatesFragment newInstance() {
        CricketUpdatesFragment fragment = new CricketUpdatesFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private RecyclerView rv_updates;
    private CricketUpdatesAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cricket_updates;
    }

    @Override
    protected void initUI() {
        rv_updates = findViewById(R.id.rv_updates);
    }

    @Override
    protected void initData() {
        mAdapter = new CricketUpdatesAdapter(new ArrayList<>());
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HeadlineDetailActivity.forward(getContext(), mAdapter.getItem(position).getId());
            }
        });
        rv_updates.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_updates.addItemDecoration(new ItemDecoration(getContext(), getResources().getColor(R.color.c_CCCCCC), 0, 0.5f));
        rv_updates.setAdapter(mAdapter);
    }

    public void setData(List<UpdatesBean> list) {
        if (list != null && list.size() > 0) {
            list.get(0).setItemType(UpdatesBean.HEAD);
            mAdapter.setNewData(list);
        }
    }
}
