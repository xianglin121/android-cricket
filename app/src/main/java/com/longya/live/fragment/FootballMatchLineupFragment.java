package com.longya.live.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.longya.live.R;
import com.longya.live.adapter.FootballMatchBenchLineupAdapter;
import com.longya.live.adapter.FootballMatchLineupInnerAdapter;
import com.longya.live.model.FootballLineupBean;
import com.longya.live.model.FootballLineupPlayerBean;
import com.longya.live.model.FootballLineupSubstituteBean;
import com.longya.live.presenter.match.FootballMatchLineupPresenter;
import com.longya.live.util.DpUtil;
import com.longya.live.util.SpUtil;
import com.longya.live.view.MvpFragment;
import com.longya.live.view.match.FootballMatchLineupView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FootballMatchLineupFragment extends MvpFragment<FootballMatchLineupPresenter> implements FootballMatchLineupView, View.OnClickListener {

    public static FootballMatchLineupFragment newInstance(int id) {
        FootballMatchLineupFragment fragment = new FootballMatchLineupFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    private int mId;
//    private RecyclerView rv_red;
//    private FootballMatchLineupAdapter mRedAdapter;
//    private RecyclerView rv_blue;
//    private FootballMatchLineupAdapter mBlueAdapter;
    private TextView tv_home_coach, tv_away_coach;
    private TextView tv_home_formation, tv_away_formation;
    private LinearLayout ll_red, ll_blue;
    private RecyclerView rv_bench;
    private FootballMatchBenchLineupAdapter mBenchAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_football_match_lineup;
    }

    @Override
    protected FootballMatchLineupPresenter createPresenter() {
        return new FootballMatchLineupPresenter(this);
    }

    @Override
    protected void initUI() {
        mId = getArguments().getInt("id");
        tv_home_coach = rootView.findViewById(R.id.tv_home_coach);
        tv_away_coach = rootView.findViewById(R.id.tv_away_coach);
        tv_home_formation = rootView.findViewById(R.id.tv_home_formation);
        tv_away_formation = rootView.findViewById(R.id.tv_away_formation);
        ll_red = rootView.findViewById(R.id.ll_red);
        ll_blue = rootView.findViewById(R.id.ll_blue);
        rv_bench = rootView.findViewById(R.id.rv_bench);
    }

    @Override
    protected void initData() {
//        mRedAdapter = new FootballMatchLineupAdapter(R.layout.item_football_match_lineup, new ArrayList<>());
//        rv_red.setLayoutManager(new LinearLayoutManager(getContext()));
//        rv_red.setAdapter(mRedAdapter);
//
//        mBlueAdapter = new FootballMatchLineupAdapter(R.layout.item_football_match_lineup, new ArrayList<>());
//        rv_blue.setLayoutManager(new LinearLayoutManager(getContext()));
//        rv_blue.setAdapter(mBlueAdapter);

        mBenchAdapter = new FootballMatchBenchLineupAdapter(R.layout.item_football_match_bench_lineup, new ArrayList<>());
        rv_bench.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_bench.setAdapter(mBenchAdapter);

        mvpPresenter.getLineup(mId);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataSuccess(FootballLineupBean lineupBean) {
        if (lineupBean != null) {
            if ("1".equals(SpUtil.getInstance().getStringValue(SpUtil.FOOTBALL_LANGUAGE))) {
                if (lineupBean.getHome_manager() != null) {
                    if (!TextUtils.isEmpty(lineupBean.getHome_manager().getName_zht())) {
                        tv_home_coach.setText("主教练：" + lineupBean.getHome_manager().getName_zht());
                    }else {
                        tv_home_coach.setText("主教练：" + "");
                    }
                }
                if (lineupBean.getAway_manager() != null) {
                    if (!TextUtils.isEmpty(lineupBean.getAway_manager().getName_zht())) {
                        tv_away_coach.setText("主教练：" + lineupBean.getAway_manager().getName_zht());
                    }else {
                        tv_away_coach.setText("主教练：" + "");
                    }
                }
            }else if ("2".equals(SpUtil.getInstance().getStringValue(SpUtil.FOOTBALL_LANGUAGE))) {
                if (lineupBean.getHome_manager() != null) {
                    if (!TextUtils.isEmpty(lineupBean.getHome_manager().getName_en())) {
                        tv_home_coach.setText("主教练：" + lineupBean.getHome_manager().getName_en());
                    }else {
                        tv_home_coach.setText("主教练：" + "");
                    }
                }
                if (lineupBean.getAway_manager() != null) {
                    if (!TextUtils.isEmpty(lineupBean.getAway_manager().getName_en())) {
                        tv_away_coach.setText("主教练：" + lineupBean.getAway_manager().getName_en());
                    }else {
                        tv_away_coach.setText("主教练：" + "");
                    }
                }
            }else {
                if (lineupBean.getHome_manager() != null) {
                    if (!TextUtils.isEmpty(lineupBean.getHome_manager().getName_zh())) {
                        tv_home_coach.setText("主教练：" + lineupBean.getHome_manager().getName_zh());
                    }else {
                        tv_home_coach.setText("主教练：" + "");
                    }
                }
                if (lineupBean.getAway_manager() != null) {
                    if (!TextUtils.isEmpty(lineupBean.getAway_manager().getName_zh())) {
                        tv_away_coach.setText("主教练：" + lineupBean.getAway_manager().getName_zh());
                    }else {
                        tv_away_coach.setText("主教练：" + "");
                    }
                }
            }
            if (!TextUtils.isEmpty(lineupBean.getHome_formation())) {
                tv_home_formation.setText("阵容：" + lineupBean.getHome_formation());
            }else {
                tv_home_formation.setText("");
            }
            if (!TextUtils.isEmpty(lineupBean.getAway_formation())) {
                tv_away_formation.setText("阵容：" + lineupBean.getAway_formation());
            }else {
                tv_away_formation.setText("");
            }
            if (lineupBean.getHome_player_first() != null && lineupBean.getHome_player_first().size() > 0) {
                for (int i = 0; i < lineupBean.getHome_player_first().size(); i++) {
                    List<FootballLineupPlayerBean> list = lineupBean.getHome_player_first().get(i);
                    RecyclerView rv_player = new RecyclerView(getContext());
                    rv_player.setNestedScrollingEnabled(false);
                    if (lineupBean.getHome_player_first().size() == 4) {
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(0, 0, 0, DpUtil.dp2px(20));
                        rv_player.setLayoutParams(layoutParams);
                        ll_red.addView(rv_player);
                    }else {
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(0, 0, 0, DpUtil.dp2px(8));
                        rv_player.setLayoutParams(layoutParams);
                        ll_red.addView(rv_player);
                    }
                    FootballMatchLineupInnerAdapter lineupAdapter = new FootballMatchLineupInnerAdapter(R.layout.item_football_match_lineup_inner, list, true);
                    rv_player.setLayoutManager(new GridLayoutManager(getContext(), list.size()));
                    rv_player.setAdapter(lineupAdapter);
                }
            }
            if (lineupBean.getAway_player_first() != null && lineupBean.getAway_player_first().size() > 0) {
                Collections.reverse(lineupBean.getAway_player_first());
                for (int i = 0; i < lineupBean.getAway_player_first().size(); i++) {
                    List<FootballLineupPlayerBean> list = lineupBean.getAway_player_first().get(i);
                    RecyclerView rv_player = new RecyclerView(getContext());
                    rv_player.setNestedScrollingEnabled(false);
                    if (lineupBean.getAway_player_first().size() == 4) {
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(0, 0, 0, DpUtil.dp2px(20));
                        rv_player.setLayoutParams(layoutParams);
                        ll_blue.addView(rv_player);
                    }else {
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(0, 0, 0, DpUtil.dp2px(8));
                        rv_player.setLayoutParams(layoutParams);
                        ll_blue.addView(rv_player);
                    }
                    FootballMatchLineupInnerAdapter lineupAdapter = new FootballMatchLineupInnerAdapter(R.layout.item_football_match_lineup_inner, list, false);
                    rv_player.setLayoutManager(new GridLayoutManager(getContext(), list.size()));
                    rv_player.setAdapter(lineupAdapter);
                }
            }
            List<FootballLineupSubstituteBean> list = new ArrayList<>();
            if (lineupBean.getHome_player() != null) {
                for (int i = 0; i < lineupBean.getHome_player().size(); i++) {
                    FootballLineupPlayerBean homePlayerBean = lineupBean.getHome_player().get(i);
                    FootballLineupSubstituteBean substituteBean = new FootballLineupSubstituteBean();
                    substituteBean.setHome_player(homePlayerBean);
                    list.add(substituteBean);
                }
            }
            if (lineupBean.getAway_player() != null) {
                for (int i = 0; i < lineupBean.getAway_player().size(); i++) {
                    FootballLineupPlayerBean awayPlayerBean = lineupBean.getAway_player().get(i);
                    if (i < list.size()) {
                        list.get(i).setAway_player(awayPlayerBean);
                    }else {
                        FootballLineupSubstituteBean substituteBean = new FootballLineupSubstituteBean();
                        substituteBean.setAway_player(awayPlayerBean);
                        list.add(substituteBean);
                    }
                }
            }
            mBenchAdapter.setNewData(list);
        }
    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
