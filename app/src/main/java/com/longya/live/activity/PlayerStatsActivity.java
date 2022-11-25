package com.longya.live.activity;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.longya.live.R;
import com.longya.live.adapter.PlayerStatesAdapter;
import com.longya.live.adapter.PlayerStatesLeftAdapter;
import com.longya.live.custom.ObservableScrollView;
import com.longya.live.model.JsonBean;
import com.longya.live.presenter.cricket.PlayerStatsPresenter;
import com.longya.live.view.BaseActivity;
import com.longya.live.view.MvpActivity;
import com.longya.live.view.cricket.PlayerStatsView;

import java.util.Arrays;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/9/6
 */
public class PlayerStatsActivity extends MvpActivity<PlayerStatsPresenter> implements PlayerStatsView {
    public static void forward(Context context) {
        Intent intent = new Intent(context, PlayerStatsActivity.class);
        context.startActivity(intent);
    }

    private TabLayout tabLayout;
    private ObservableScrollView scrollView1;
    private ObservableScrollView scrollView2;
    private RecyclerView rv_left;
    private PlayerStatesLeftAdapter mLeftAdapter;
    private RecyclerView rv_right;
    private PlayerStatesAdapter mAdapter;

    @Override
    protected PlayerStatsPresenter createPresenter() {
        return new PlayerStatsPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_player_states;
    }

    @Override
    protected void initView() {
        setTitleText(getString(R.string.player_stats));
        tabLayout = findViewById(R.id.tabLayout);
        scrollView1 = findViewById(R.id.scrollView1);
        scrollView2 = findViewById(R.id.scrollView2);
        rv_left = findViewById(R.id.rv_left);
        rv_right = findViewById(R.id.rv_right);
    }

    @Override
    protected void initData() {
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.all2)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.wk)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.bat)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.bowl)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.ar)));

        mLeftAdapter = new PlayerStatesLeftAdapter(R.layout.item_player_states_left, Arrays.asList("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
        rv_left.setLayoutManager(new LinearLayoutManager(this));
        rv_left.setAdapter(mLeftAdapter);
        rv_left.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
                    rv_right.scrollBy(dx, dy);
                }
            }
        });

        mAdapter = new PlayerStatesAdapter(R.layout.item_player_states, Arrays.asList("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
        rv_right.setLayoutManager(new LinearLayoutManager(this));
        rv_right.setAdapter(mAdapter);
        rv_right.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
                    rv_left.scrollBy(dx, dy);
                }
            }
        });
        scrollView1.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
                scrollView2.smoothScrollTo(x, y);
            }
        });
        scrollView2.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
                scrollView1.smoothScrollTo(x, y);
            }
        });
    }

    @Override
    public void getDataSuccess(JsonBean model) {

    }

    @Override
    public void getDataFail(String msg) {

    }
}
