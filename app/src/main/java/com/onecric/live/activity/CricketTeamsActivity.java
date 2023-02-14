package com.onecric.live.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.R;
import com.onecric.live.adapter.CricketTeamsSecondAdapter;
import com.onecric.live.custom.ItemDecoration;
import com.onecric.live.model.CricketPlayerBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.presenter.cricket.CricketTeamsPresenter;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.cricket.CricketTeamsView;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/9/1
 * 球队详情
 */
public class CricketTeamsActivity extends MvpActivity<CricketTeamsPresenter> implements CricketTeamsView {
    public static void forward(Context context, String name, int teamId) {
        Intent intent = new Intent(context, CricketTeamsActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("teamId", teamId);
        context.startActivity(intent);
    }

    private String mName;
    private int mTeamId;
    private TextView tv_batter;
    private TextView tv_bowler;
    private TextView tv_all_rounder;
    private RecyclerView rv_batters;
    private CricketTeamsSecondAdapter mBattersAdapter;
    private RecyclerView rv_bowlers;
    private CricketTeamsSecondAdapter mBowlersAdapter;
    private RecyclerView rv_all_rounder;
    private CricketTeamsSecondAdapter mAllRounderAdapter;

    @Override
    protected CricketTeamsPresenter createPresenter() {
        return new CricketTeamsPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_cricket_teams;
    }

    @Override
    protected void initView() {
        //scheme
        Intent intent = getIntent();
        String action = intent.getAction();
        if (Intent.ACTION_VIEW.equals(action)) {
            Uri uri = intent.getData();
            if (uri != null) {
                String name = uri.getQueryParameter("name");
                String id = uri.getQueryParameter("teamId");
                mName = name;
                mTeamId = Integer.parseInt(id);
            }
        }else{
            mName = getIntent().getStringExtra("name");
            mTeamId = getIntent().getIntExtra("teamId", 0);
        }

        if (!TextUtils.isEmpty(mName)) {
            setTitleText(mName);
        }

        tv_batter = findViewById(R.id.tv_batter);
        tv_bowler = findViewById(R.id.tv_bowler);
        tv_all_rounder = findViewById(R.id.tv_all_rounder);
        rv_batters = findViewById(R.id.rv_batters);
        rv_bowlers = findViewById(R.id.rv_bowlers);
        rv_all_rounder = findViewById(R.id.rv_all_rounder);
    }

    @Override
    protected void initData() {
        mBattersAdapter = new CricketTeamsSecondAdapter(R.layout.item_cricket_teams_second, new ArrayList<>());
        mBattersAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PlayerProfileActivity.forward(mActivity, mBattersAdapter.getItem(position).getPlayer_id());
            }
        });
        rv_batters.setLayoutManager(new LinearLayoutManager(this));
        rv_batters.addItemDecoration(new ItemDecoration(this, getResources().getColor(R.color.c_CCCCCC), 0, 0.5f));
        rv_batters.setAdapter(mBattersAdapter);

        mBowlersAdapter = new CricketTeamsSecondAdapter(R.layout.item_cricket_teams_second, new ArrayList<>());
        mBowlersAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PlayerProfileActivity.forward(mActivity, mBowlersAdapter.getItem(position).getPlayer_id());
            }
        });
        rv_bowlers.setLayoutManager(new LinearLayoutManager(this));
        rv_bowlers.addItemDecoration(new ItemDecoration(this, getResources().getColor(R.color.c_CCCCCC), 0, 0.5f));
        rv_bowlers.setAdapter(mBowlersAdapter);

        mAllRounderAdapter = new CricketTeamsSecondAdapter(R.layout.item_cricket_teams_second, new ArrayList<>());
        mAllRounderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PlayerProfileActivity.forward(mActivity, mAllRounderAdapter.getItem(position).getPlayer_id());
            }
        });
        rv_all_rounder.setLayoutManager(new LinearLayoutManager(this));
        rv_all_rounder.addItemDecoration(new ItemDecoration(this, getResources().getColor(R.color.c_CCCCCC), 0, 0.5f));
        rv_all_rounder.setAdapter(mAllRounderAdapter);

        mvpPresenter.getList(mTeamId);
    }

    @Override
    public void getDataSuccess(JsonBean model) {

    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void getDataSuccess(List<CricketPlayerBean> batters, String batterCount, List<CricketPlayerBean> bowlers, String bowlersCount, List<CricketPlayerBean> allRounder, String allRounderCount) {
        if (batters != null) {
            mBattersAdapter.setNewData(batters);
        }
        if (!TextUtils.isEmpty(batterCount)) {
            tv_batter.setText(batterCount);
        }
        if (bowlers != null) {
            mBowlersAdapter.setNewData(bowlers);
        }
        if (!TextUtils.isEmpty(bowlersCount)) {
            tv_bowler.setText(bowlersCount);
        }
        if (allRounder != null) {
            mAllRounderAdapter.setNewData(allRounder);
        }
        if (!TextUtils.isEmpty(allRounderCount)) {
            tv_all_rounder.setText(allRounderCount);
        }
    }
}
