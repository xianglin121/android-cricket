package com.onecric.live.fragment;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.onecric.live.model.CricketMatchBean;
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

    private SmartRefreshLayout smart_rl;
    private RecyclerView recyclerView,sRecyclerView;
    private CricketSquadAdapter mAdapter,mSAdapter;

    private ImageView iv_home_logo;
    private TextView tv_home_name;
    private ImageView iv_away_logo;
    private TextView tv_away_name,tv_start_title,tv_bench_title;

    private CricketMatchBean mModel;

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
        sRecyclerView = findViewById(R.id.recyclerView_start);
        tv_bench_title = findViewById(R.id.tv_bench_title);
        tv_start_title = findViewById(R.id.tv_start_title);
        iv_home_logo = findViewById(R.id.iv_home_logo);
        tv_home_name = findViewById(R.id.tv_home_name);
        iv_away_logo = findViewById(R.id.iv_away_logo);
        tv_away_name = findViewById(R.id.tv_away_name);
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
                if(mModel != null){
                    try{
                        mvpPresenter.getList(mModel.getMatch_id(),Integer.parseInt(mModel.getTournament_id()),mModel.getHome_id(),mModel.getAway_id());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
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
//        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.item_cricket_squad_header, null);
//        mAdapter.addHeaderView(inflate);
//        View inflate2 = LayoutInflater.from(getContext()).inflate(R.layout.layout_common_empty, null, false);
//        inflate2.findViewById(R.id.ll_empty).setVisibility(View.VISIBLE);
//        mAdapter.setEmptyView(inflate2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        mSAdapter = new CricketSquadAdapter(R.layout.item_cricket_squad, new ArrayList<>());
        mSAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_home_logo) {
                    PlayerProfileActivity.forward(getContext(), mAdapter.getItem(position).getHome_player_id());
                }else if (view.getId() == R.id.iv_away_logo) {
                    PlayerProfileActivity.forward(getContext(), mAdapter.getItem(position).getAway_player_id());
                }
            }
        });
        sRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        sRecyclerView.setAdapter(mSAdapter);
    }

    public void getList(CricketMatchBean model) {
        if(model == null){
            mAdapter.setNewData(null);
        }
        mModel = model;
        if (!TextUtils.isEmpty(model.getHome_name())) {
            tv_home_name.setText(model.getHome_name());
        }
        if (!TextUtils.isEmpty(model.getAway_name())) {
            tv_away_name.setText(model.getAway_name());
        }
        GlideUtil.loadTeamImageDefault(getContext(), model.getHome_logo(), iv_home_logo);
        GlideUtil.loadTeamImageDefault(getContext(), model.getAway_logo(), iv_away_logo);

        mvpPresenter.getList(model.getMatch_id(),Integer.parseInt(mModel.getTournament_id()),mModel.getHome_id(),mModel.getAway_id());
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }



    @Override
    public void getDataSuccess(List<CricketSquadBean> list) {

    }

    @Override
    public void getDataFail(String msg) {
        smart_rl.finishRefresh();
    }

    @Override
    public void getDataSuccess(List<CricketSquadBean> bList, int type) {
        smart_rl.finishRefresh();
        findViewById(R.id.ll_empty).setVisibility(View.GONE);
        if(type == 1){
            if (bList.size()>0) {
                sRecyclerView.setVisibility(View.VISIBLE);
                tv_start_title.setVisibility(View.VISIBLE);
                mSAdapter.setNewData(bList);
                mSAdapter.notifyDataSetChanged();
            }else{
                sRecyclerView.setVisibility(View.GONE);
                tv_start_title.setVisibility(View.GONE);
                mSAdapter.setNewData(null);
            }
            mvpPresenter.getBenchList(mModel.getMatch_id(),Integer.parseInt(mModel.getTournament_id()),mModel.getHome_id(),mModel.getAway_id());
        }else if(type == 2){
            if (bList.size()>0) {
                recyclerView.setVisibility(View.VISIBLE);
                tv_bench_title.setVisibility(View.VISIBLE);
                mAdapter.setNewData(bList);
                mAdapter.notifyDataSetChanged();
            }else{
                recyclerView.setVisibility(View.GONE);
                tv_bench_title.setVisibility(View.GONE);
                mAdapter.setNewData(null);
                if(mSAdapter.getData().size()<=0){
                    findViewById(R.id.ll_empty).setVisibility(View.VISIBLE);
                }
            }
        }

    }
}
