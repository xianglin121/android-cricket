package com.onecric.live.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.R;
import com.onecric.live.activity.CricketDetailActivity;
import com.onecric.live.activity.CricketInnerActivity;
import com.onecric.live.activity.CricketTeamsActivity;
import com.onecric.live.adapter.CricketPointsAdapter;
import com.onecric.live.custom.ItemDecoration;
import com.onecric.live.model.CricketInfoBean;
import com.onecric.live.model.CricketPointsBean;
import com.onecric.live.presenter.cricket.CricketInfoPresenter;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.cricket.CricketInfoView;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/27
 */
public class CricketInfoFragment extends MvpFragment<CricketInfoPresenter> implements CricketInfoView, View.OnClickListener {
    public static CricketInfoFragment newInstance(int matchId) {
        CricketInfoFragment fragment = new CricketInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("matchId", matchId);
        bundle.putInt("tournamentId", matchId);
        fragment.setArguments(bundle);
        return fragment;
    }

    private int mMatchId;
    private ImageView iv_home_logo;
    private TextView tv_home_name;
    private ImageView iv_away_logo;
    private TextView tv_away_name;
    private TextView tv_tournament_name;
    private TextView tv_match;
    private TextView tv_start_time;
    private TextView tv_venue;
    private TextView tv_playing_home_name;
    private TextView tv_home_member;
    private TextView tv_playing_away_name;
    private TextView tv_away_member;

    private RecyclerView rv_points;
    private CricketPointsAdapter mAdapter;

    private CricketInfoBean mModel;
    private int mTournamentId;
    private int mHomeId;
    private int mAwayId;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cricket_info;
    }

    @Override
    protected CricketInfoPresenter createPresenter() {
        return new CricketInfoPresenter(this);
    }

    @Override
    protected void initUI() {
        mMatchId = getArguments().getInt("matchId");
        if(getArguments().getInt("tournamentId")!=0){
            mTournamentId = getArguments().getInt("tournamentId");
        }
        iv_home_logo = findViewById(R.id.iv_home_logo);
        tv_home_name = findViewById(R.id.tv_home_name);
        iv_away_logo = findViewById(R.id.iv_away_logo);
        tv_away_name = findViewById(R.id.tv_away_name);
        tv_tournament_name = findViewById(R.id.tv_tournament_name);
        tv_match = findViewById(R.id.tv_match);
        tv_start_time = findViewById(R.id.tv_start_time);
        tv_venue = findViewById(R.id.tv_venue);
        tv_playing_home_name = findViewById(R.id.tv_playing_home_name);
        tv_home_member = findViewById(R.id.tv_home_member);
        tv_playing_away_name = findViewById(R.id.tv_playing_away_name);
        tv_away_member = findViewById(R.id.tv_away_member);
        rv_points = findViewById(R.id.rv_points);

        findViewById(R.id.tv_jump_squad).setOnClickListener(this);
        findViewById(R.id.ll_home).setOnClickListener(this);
        findViewById(R.id.ll_away).setOnClickListener(this);
        findViewById(R.id.ll_tournament).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_jump_squad:
                ((CricketDetailActivity)getContext()).mViewPager.setCurrentItem(4);
                break;
            case R.id.ll_home:
                if (mModel != null) {
                    String homeName = mModel.getHome_name();
                    if (TextUtils.isEmpty(homeName)) {
                        homeName = "";
                    }
                    CricketTeamsActivity.forward(getContext(), homeName, mHomeId);
                }
                break;
            case R.id.ll_away:
                if (mModel != null) {
                    String awayName = mModel.getAway_name();
                    if (TextUtils.isEmpty(awayName)) {
                        awayName = "";
                    }
                    CricketTeamsActivity.forward(getContext(), awayName, mAwayId);
                }
                break;
            case R.id.ll_tournament:
                if (mModel != null) {
                    if (!TextUtils.isEmpty(mModel.getTournament_name()) && mTournamentId > 0) {
                        CricketInnerActivity.forward(getContext(), mModel.getTournament_name(), mModel.getType(), mTournamentId);
                    }
                }
                break;
        }
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

        mvpPresenter.getData(mMatchId);
    }

    public void getList(int homeId, int awayId, int tournamentId) {
        mTournamentId = tournamentId;
        mHomeId = homeId;
        mAwayId = awayId;
        if(mvpPresenter!=null){
            mvpPresenter.getPointsList(tournamentId);
        }
    }

/*    public void getData(int homeId, int awayId, int tournamentId,int mId) {
        mMatchId = mId;
        mTournamentId = tournamentId;
        mHomeId = homeId;
        mAwayId = awayId;
        mvpPresenter.getData(mMatchId);
        mvpPresenter.getPointsList(tournamentId);
    }*/

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataSuccess(CricketInfoBean model) {
        if (model != null) {
            mModel = model;
            GlideUtil.loadTeamImageDefault(getContext(), model.getHome_logo(), iv_home_logo);
            if (!TextUtils.isEmpty(model.getHome_name())) {
                tv_home_name.setText(model.getHome_name());
            }
            GlideUtil.loadTeamImageDefault(getContext(), model.getAway_logo(), iv_away_logo);
            if (!TextUtils.isEmpty(model.getAway_name())) {
                tv_away_name.setText(model.getAway_name());
            }
            if (!TextUtils.isEmpty(model.getTournament_name())) {
                tv_tournament_name.setText(model.getTournament_name());
            }
            if (!TextUtils.isEmpty(model.getMatch())) {
                tv_match.setText(model.getMatch());
            }
            if (!TextUtils.isEmpty(model.getMatch_start_time())) {
                tv_start_time.setText(model.getMatch_start_time());
            }
            if (!TextUtils.isEmpty(model.getVenue_name())) {
                tv_venue.setText(model.getVenue_name());
            }
            if (!TextUtils.isEmpty(model.getHome_name())) {
                tv_playing_home_name.setText(model.getHome_name());
            }
            if (!TextUtils.isEmpty(model.getHome_member())) {
                tv_home_member.setText(model.getHome_member());
            }
            if (!TextUtils.isEmpty(model.getAway_name())) {
                tv_playing_away_name.setText(model.getAway_name());
            }
            if (!TextUtils.isEmpty(model.getAway_member())) {
                tv_away_member.setText(model.getAway_member());
            }
        }
    }

    @Override
    public void getPointsDataSuccess(List<CricketPointsBean> list) {
        if (list != null) {
            if (mAdapter != null) {
                mAdapter.setNewData(list);
            }
        }
    }

    @Override
    public void getDataFail(String msg) {

    }
}
