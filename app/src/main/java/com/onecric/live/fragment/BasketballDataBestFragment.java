package com.onecric.live.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.R;
import com.onecric.live.adapter.BasketballDataBestLeftAdapter;
import com.onecric.live.adapter.BasketballDataBestRightAdapter;
import com.onecric.live.model.DataBestLeftBean;
import com.onecric.live.model.DataBestRightBean;
import com.onecric.live.presenter.match.BasketballDataBestPresenter;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.match.BasketballDataBestView;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/3/4
 */
public class BasketballDataBestFragment extends MvpFragment<BasketballDataBestPresenter> implements BasketballDataBestView, View.OnClickListener {
    public static BasketballDataBestFragment newInstance() {
        BasketballDataBestFragment fragment = new BasketballDataBestFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private int mSeasonId;
    private TextView tv_team;
    private View line_team;
    private TextView tv_player;
    private View line_player;
    private RecyclerView rv_left;
    private BasketballDataBestLeftAdapter mLeftAdapter;
    private TextView tv_type;
    private RecyclerView rv_right;
    private BasketballDataBestRightAdapter mRightAdapter;

    private List<DataBestLeftBean> teamList;
    private List<DataBestLeftBean> playerList;
    public int mTypeId;

    public int mType;//0:球队 1:球员

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_basketball_data_best;
    }

    @Override
    protected BasketballDataBestPresenter createPresenter() {
        return new BasketballDataBestPresenter(this);
    }

    @Override
    protected void initUI() {
        tv_team = findViewById(R.id.tv_team);
        line_team = findViewById(R.id.line_team);
        tv_player = findViewById(R.id.tv_player);
        line_player = findViewById(R.id.line_player);
        rv_left = findViewById(R.id.rv_left);
        tv_type = findViewById(R.id.tv_type);
        rv_right = findViewById(R.id.rv_right);

        findViewById(R.id.ll_team).setOnClickListener(this);
        findViewById(R.id.ll_player).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        //初始化球队左侧列表
        teamList = new ArrayList<>();
        teamList.add(new DataBestLeftBean(0, "得分", true));
        teamList.add(new DataBestLeftBean(1, "失分", false));
        teamList.add(new DataBestLeftBean(2, "两分球命中", false));
        teamList.add(new DataBestLeftBean(3, "三分球命中", false));
        teamList.add(new DataBestLeftBean(4, "投篮命中", false));
        teamList.add(new DataBestLeftBean(5, "犯规", false));
        teamList.add(new DataBestLeftBean(6, "篮板", false));
        teamList.add(new DataBestLeftBean(7, "防守篮板", false));
        teamList.add(new DataBestLeftBean(8, "进攻篮板", false));
        teamList.add(new DataBestLeftBean(9, "助攻", false));
        teamList.add(new DataBestLeftBean(10, "失误", false));
        teamList.add(new DataBestLeftBean(11, "抢断", false));
        teamList.add(new DataBestLeftBean(12, "盖帽", false));
        teamList.add(new DataBestLeftBean(13, "罚球命中", false));
        //初始化球员左侧列表
        playerList = new ArrayList<>();
        playerList.add(new DataBestLeftBean(0, "得分", true));
        playerList.add(new DataBestLeftBean(1, "上场时间", false));
        playerList.add(new DataBestLeftBean(2, "罚球命中", false));
        playerList.add(new DataBestLeftBean(3, "两分球命中", false));
        playerList.add(new DataBestLeftBean(4, "三分球命中", false));
        playerList.add(new DataBestLeftBean(5, "投篮命中", false));
        playerList.add(new DataBestLeftBean(6, "篮板", false));
        playerList.add(new DataBestLeftBean(7, "防守篮板", false));
        playerList.add(new DataBestLeftBean(8, "进攻篮板", false));
        playerList.add(new DataBestLeftBean(9, "助攻", false));
        playerList.add(new DataBestLeftBean(10, "失误", false));
        playerList.add(new DataBestLeftBean(11, "抢断", false));
        playerList.add(new DataBestLeftBean(12, "盖帽", false));
        playerList.add(new DataBestLeftBean(13, "个人犯规", false));

        mLeftAdapter = new BasketballDataBestLeftAdapter(R.layout.item_basketball_data_best_left, teamList);
        mLeftAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mTypeId = mLeftAdapter.getItem(position).getType();
                for (int i = 0; i < mLeftAdapter.getData().size(); i++) {
                    if (i == position) {
                        mLeftAdapter.getItem(i).setSelect(true);
                    }else {
                        mLeftAdapter.getItem(i).setSelect(false);
                    }
                }
                mLeftAdapter.notifyDataSetChanged();
                if (mType == 0) {
                    mvpPresenter.getTeamList(mSeasonId, mTypeId);
                }else {
                    mvpPresenter.getMemberList(mSeasonId, mTypeId);
                }
            }
        });
        rv_left.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_left.setAdapter(mLeftAdapter);

        mRightAdapter = new BasketballDataBestRightAdapter(R.layout.item_basketball_data_best_right, new ArrayList<>(), this);
        rv_right.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_right.setAdapter(mRightAdapter);
    }

    public void getList(int id) {
        mSeasonId = id;
        mType = 0;
        toggleType();
        mvpPresenter.getTeamList(id, mTypeId);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataSuccess(List<DataBestRightBean> list) {
        mRightAdapter.setNewData(list);
    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_team:
                if (mType == 0) {
                    return;
                }
                mType = 0;
                toggleType();
                mvpPresenter.getTeamList(mSeasonId, mTypeId);
                break;
            case R.id.ll_player:
                if (mType == 1) {
                    return;
                }
                mType = 1;
                toggleType();
                mvpPresenter.getMemberList(mSeasonId, mTypeId);
                break;
        }
    }

    private void toggleType() {
        if (mType == 0) {
            tv_team.setTextColor(getResources().getColor(R.color.c_E3AC72));
            line_team.setVisibility(View.VISIBLE);
            tv_player.setTextColor(getResources().getColor(R.color.c_666666));
            line_player.setVisibility(View.GONE);
            tv_type.setText("球队");
            mLeftAdapter.setNewData(teamList);
        }else {
            tv_team.setTextColor(getResources().getColor(R.color.c_666666));
            line_team.setVisibility(View.GONE);
            tv_player.setTextColor(getResources().getColor(R.color.c_E3AC72));
            line_player.setVisibility(View.VISIBLE);
            tv_type.setText("球员");
            mLeftAdapter.setNewData(playerList);
        }
    }
}
