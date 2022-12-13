package com.onecric.live.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onecric.live.R;
import com.onecric.live.adapter.FantasyMatchAdapter;
import com.onecric.live.adapter.FantasyPerformanceAdapter;
import com.onecric.live.custom.ItemDecoration;
import com.onecric.live.view.BaseFragment;

import java.util.Arrays;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/26
 */
public class PlayerFantasyFragment extends BaseFragment {
    public static PlayerFantasyFragment newInstance() {
        PlayerFantasyFragment fragment = new PlayerFantasyFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private RecyclerView rv_match;
    private FantasyMatchAdapter mMatchAdapter;
    private RecyclerView rv_performance;
    private FantasyPerformanceAdapter mPerformanceAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_player_fantasy;
    }

    @Override
    protected void initUI() {
        rv_match = findViewById(R.id.rv_match);
        rv_performance = findViewById(R.id.rv_performance);
    }

    @Override
    protected void initData() {
        mMatchAdapter = new FantasyMatchAdapter(R.layout.item_fantasy_match, Arrays.asList("", "", ""));
        rv_match.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_match.addItemDecoration(new ItemDecoration(getContext(), getResources().getColor(R.color.c_CCCCCC), 0, 0.5f));
        rv_match.setAdapter(mMatchAdapter);

        mPerformanceAdapter = new FantasyPerformanceAdapter(R.layout.item_fantasy_performance, Arrays.asList("", "", ""));
        rv_performance.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_performance.addItemDecoration(new ItemDecoration(getContext(), getResources().getColor(R.color.c_CCCCCC), 0, 0.5f));
        rv_performance.setAdapter(mPerformanceAdapter);
    }
}
