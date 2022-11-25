package com.longya.live.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.longya.live.R;
import com.longya.live.activity.CricketDetailActivity;
import com.longya.live.adapter.CricketDetailAdapter;
import com.longya.live.adapter.CricketPointsAdapter;
import com.longya.live.custom.ItemDecoration;
import com.longya.live.model.CricketPointsBean;
import com.longya.live.model.CricketTeamBean;
import com.longya.live.view.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/26
 */
public class CricketPointsFragment extends BaseFragment {
    public static CricketPointsFragment newInstance() {
        CricketPointsFragment fragment = new CricketPointsFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private RecyclerView rv_points;
    private CricketPointsAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cricket_points;
    }

    @Override
    protected void initUI() {
        rv_points = findViewById(R.id.rv_points);
    }

    @Override
    protected void initData() {
        mAdapter = new CricketPointsAdapter(R.layout.item_cricket_points, new ArrayList<>());
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mAdapter.getItem(position).setExpand(!mAdapter.getItem(position).isExpand());
                mAdapter.notifyItemChanged(position);
            }
        });
        rv_points.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_points.addItemDecoration(new ItemDecoration(getContext(), getResources().getColor(R.color.c_CCCCCC), 0, 0.5f));
        rv_points.setAdapter(mAdapter);
    }

    public void setList(List<CricketPointsBean> list) {
        if (list != null) {
            if (mAdapter != null) {
                mAdapter.setNewData(list);
            }
        }
    }
}
