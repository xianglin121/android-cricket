package com.onecric.live.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.R;
import com.onecric.live.adapter.BasketballDataBestLeftAdapter;
import com.onecric.live.adapter.FootballDataBestRightAdapter;
import com.onecric.live.model.DataBestLeftBean;
import com.onecric.live.model.FootballDataBestRightBean;
import com.onecric.live.presenter.match.FootballDataBestPresenter;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.match.FootballDataBestView;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/3/4
 */
public class FootballDataBestFragment extends MvpFragment<FootballDataBestPresenter> implements FootballDataBestView, View.OnClickListener {
    public static FootballDataBestFragment newInstance() {
        FootballDataBestFragment fragment = new FootballDataBestFragment();
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
    private FootballDataBestRightAdapter mRightAdapter;

    private List<DataBestLeftBean> teamList;
    private List<DataBestLeftBean> playerList;
    public int mTypeId;

    public int mType;//0:球队 1:球员

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_football_data_best;
    }

    @Override
    protected FootballDataBestPresenter createPresenter() {
        return new FootballDataBestPresenter(this);
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
        teamList.add(new DataBestLeftBean(0, "进球", true));
        teamList.add(new DataBestLeftBean(1, "点球", false));
        teamList.add(new DataBestLeftBean(2, "助攻", false));
        teamList.add(new DataBestLeftBean(3, "红牌", false));
        teamList.add(new DataBestLeftBean(4, "黄牌", false));
        teamList.add(new DataBestLeftBean(5, "射门", false));
        teamList.add(new DataBestLeftBean(6, "射正", false));
        teamList.add(new DataBestLeftBean(7, "过人", false));
        teamList.add(new DataBestLeftBean(8, "过人成功", false));
        teamList.add(new DataBestLeftBean(9, "解围", false));
        teamList.add(new DataBestLeftBean(10, "有效阻挡", false));
        teamList.add(new DataBestLeftBean(11, "抢断", false));
        teamList.add(new DataBestLeftBean(12, "传球", false));
        teamList.add(new DataBestLeftBean(13, "传球成功", false));
        teamList.add(new DataBestLeftBean(14, "关键传球", false));
        teamList.add(new DataBestLeftBean(15, "传中球", false));
        teamList.add(new DataBestLeftBean(16, "传中球成功", false));
        teamList.add(new DataBestLeftBean(17, "长传", false));
        teamList.add(new DataBestLeftBean(18, "成功长传", false));
        teamList.add(new DataBestLeftBean(19, "1对1拼抢", false));
        teamList.add(new DataBestLeftBean(20, "1对1拼抢成功", false));
        teamList.add(new DataBestLeftBean(21, "犯规", false));
        teamList.add(new DataBestLeftBean(22, "被侵犯", false));
        teamList.add(new DataBestLeftBean(23, "失球", false));
        teamList.add(new DataBestLeftBean(24, "拦截", false));
        teamList.add(new DataBestLeftBean(25, "越位", false));
        teamList.add(new DataBestLeftBean(26, "两黄变红", false));
        teamList.add(new DataBestLeftBean(27, "角球", false));
        teamList.add(new DataBestLeftBean(28, "控球率", false));
        //初始化球员左侧列表
        playerList = new ArrayList<>();
        playerList.add(new DataBestLeftBean(0, "比赛场次", true));
        playerList.add(new DataBestLeftBean(1, "上场场次", false));
        playerList.add(new DataBestLeftBean(2, "首发", false));
        playerList.add(new DataBestLeftBean(3, "进球", false));
        playerList.add(new DataBestLeftBean(4, "点球", false));
        playerList.add(new DataBestLeftBean(5, "助攻", false));
        playerList.add(new DataBestLeftBean(6, "出场时间", false));
        playerList.add(new DataBestLeftBean(7, "红牌", false));
        playerList.add(new DataBestLeftBean(8, "黄牌", false));
        playerList.add(new DataBestLeftBean(9, "射门", false));
        playerList.add(new DataBestLeftBean(10, "射正", false));
        playerList.add(new DataBestLeftBean(11, "过人", false));
        playerList.add(new DataBestLeftBean(12, "过人成功", false));
        playerList.add(new DataBestLeftBean(13, "解围", false));
        playerList.add(new DataBestLeftBean(14, "有效阻挡", false));
        playerList.add(new DataBestLeftBean(15, "拦截", false));
        playerList.add(new DataBestLeftBean(16, "抢断", false));
        playerList.add(new DataBestLeftBean(17, "传球", false));
        playerList.add(new DataBestLeftBean(18, "传球成功", false));
        playerList.add(new DataBestLeftBean(19, "关键传球", false));
        playerList.add(new DataBestLeftBean(20, "传中球", false));
        playerList.add(new DataBestLeftBean(21, "传中球成功", false));
        playerList.add(new DataBestLeftBean(22, "长传", false));
        playerList.add(new DataBestLeftBean(23, "成功长传", false));
        playerList.add(new DataBestLeftBean(24, "1对1拼抢", false));
        playerList.add(new DataBestLeftBean(25, "1对1拼抢成功", false));
        playerList.add(new DataBestLeftBean(26, "丢球", false));
        playerList.add(new DataBestLeftBean(27, "犯规", false));
        playerList.add(new DataBestLeftBean(28, "被侵犯", false));
        playerList.add(new DataBestLeftBean(29, "越位", false));
        playerList.add(new DataBestLeftBean(30, "两黄变红", false));
        playerList.add(new DataBestLeftBean(31, "扑救", false));
        playerList.add(new DataBestLeftBean(32, "拳击球", false));
        playerList.add(new DataBestLeftBean(33, "守门员出击", false));
        playerList.add(new DataBestLeftBean(34, "守门员出击成功", false));
        playerList.add(new DataBestLeftBean(35, "高空出击", false));

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

        mRightAdapter = new FootballDataBestRightAdapter(R.layout.item_basketball_data_best_right, new ArrayList<>(), this);
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
    public void getDataSuccess(List<FootballDataBestRightBean> list) {
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
