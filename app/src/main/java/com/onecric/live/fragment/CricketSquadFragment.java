package com.onecric.live.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.R;
import com.onecric.live.activity.PlayerProfileActivity;
import com.onecric.live.adapter.CricketSquadAdapter;
import com.onecric.live.model.CricketSquadBean;
import com.onecric.live.presenter.cricket.CricketSquadPresenter;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.cricket.CricketSquadView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/27
 */
public class CricketSquadFragment extends MvpFragment<CricketSquadPresenter> implements CricketSquadView {
    public static CricketSquadFragment newInstance() {
        CricketSquadFragment fragment = new CricketSquadFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private int mMatchId;
    private SmartRefreshLayout smart_rl;
    private RecyclerView recyclerView;
    private CricketSquadAdapter mAdapter;

    private ImageView iv_home_logo;
    private TextView tv_home_name;
    private ImageView iv_away_logo;
    private TextView tv_away_name;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cricket_squad;
    }

    @Override
    protected CricketSquadPresenter createPresenter() {
        return new CricketSquadPresenter(this);
    }

    @Override
    protected void initUI() {
        smart_rl = findViewById(R.id.smart_rl);
        recyclerView = findViewById(R.id.recyclerView);
    }

    @Override
    protected void initData() {
        MaterialHeader materialHeader = new MaterialHeader(getContext());
        materialHeader.setColorSchemeColors(getResources().getColor(R.color.c_DC3C23));
        smart_rl.setRefreshHeader(materialHeader);
        smart_rl.setEnableLoadMore(false);
        smart_rl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getList(mMatchId);
            }
        });
        mAdapter = new CricketSquadAdapter(R.layout.item_cricket_squad, new ArrayList<>());
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_home_logo) {
                    PlayerProfileActivity.forward(getContext(), mAdapter.getItem(position).getHome_player_id());
                }else if (view.getId() == R.id.iv_away_logo) {
                    PlayerProfileActivity.forward(getContext(), mAdapter.getItem(position).getAway_player_id());
                }
            }
        });
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.item_cricket_squad_header, null);
        iv_home_logo = inflate.findViewById(R.id.iv_home_logo);
        tv_home_name = inflate.findViewById(R.id.tv_home_name);
        iv_away_logo = inflate.findViewById(R.id.iv_away_logo);
        tv_away_name = inflate.findViewById(R.id.tv_away_name);
        mAdapter.addHeaderView(inflate);
        View inflate2 = LayoutInflater.from(getContext()).inflate(R.layout.layout_common_empty, null, false);
        inflate2.findViewById(R.id.ll_empty).setVisibility(View.VISIBLE);
        mAdapter.setEmptyView(inflate2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

    }

    public void getList(int matchId, String homeName, String homeLogo, String awayName, String awayLogo) {
        mMatchId = matchId;
        if (!TextUtils.isEmpty(homeName)) {
            tv_home_name.setText(homeName);
        }
        if (!TextUtils.isEmpty(awayName)) {
            tv_away_name.setText(awayName);
        }
        GlideUtil.loadTeamImageDefault(getContext(), homeLogo, iv_home_logo);
        GlideUtil.loadTeamImageDefault(getContext(), awayLogo, iv_away_logo);
        mvpPresenter.getList(mMatchId);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataSuccess(List<CricketSquadBean> list) {
        smart_rl.finishRefresh();
        if (list != null) {
            mAdapter.setNewData(list);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getDataFail(String msg) {
        smart_rl.finishRefresh();
    }
}
