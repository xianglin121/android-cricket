package com.onecric.live.fragment;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onecric.live.R;
import com.onecric.live.activity.PlayerProfileActivity;
import com.onecric.live.adapter.PlayerPerformanceAdapter;
import com.onecric.live.adapter.RecentMatchesAdapter;
import com.onecric.live.custom.ItemDecoration;
import com.onecric.live.model.BattingBean;
import com.onecric.live.model.BowlingBean;
import com.onecric.live.model.FieldingBean;
import com.onecric.live.model.PerformanceBean;
import com.onecric.live.model.RecentMatchesBean;
import com.onecric.live.util.DialogUtil;
import com.onecric.live.view.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/26
 */
public class PlayerCareerFragment extends BaseFragment implements View.OnClickListener {
    public static final String TYPE_ODI = "odi";
    public static final String TYPE_T20I = "t20i";
    public static final String TYPE_T20 = "t20";
    public static final String TYPE_TEST = "test";

    public static PlayerCareerFragment newInstance() {
        PlayerCareerFragment fragment = new PlayerCareerFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private TextView ty_type;
    private TextView tv_performance;
    private RecyclerView rv_performance;
    private PlayerPerformanceAdapter mPerformanceAdapter;
    private List<PerformanceBean> mBattingList = new ArrayList<>();
    private List<PerformanceBean> mBowlingList = new ArrayList<>();
    private List<PerformanceBean> mFieldingList = new ArrayList<>();

    private TextView tv_matches;
    private RecyclerView rv_recent;
    private RecentMatchesAdapter mMatchesAdapter;

    private String mType = TYPE_ODI;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_player_career;
    }

    @Override
    protected void initUI() {
        ty_type = findViewById(R.id.ty_type);
        tv_performance = findViewById(R.id.tv_performance);
        rv_performance = findViewById(R.id.rv_performance);
        rv_recent = findViewById(R.id.rv_recent);
        tv_matches = findViewById(R.id.tv_matches);

        findViewById(R.id.ll_type).setOnClickListener(this);
        findViewById(R.id.ll_performance).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mPerformanceAdapter = new PlayerPerformanceAdapter(R.layout.item_player_performance, new ArrayList<>());
        rv_performance.setLayoutManager(new GridLayoutManager(getContext(), 4));
        rv_performance.setAdapter(mPerformanceAdapter);

        mMatchesAdapter = new RecentMatchesAdapter(R.layout.item_recent_matches, new ArrayList<>());
        rv_recent.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_recent.addItemDecoration(new ItemDecoration(getContext(), getResources().getColor(R.color.c_CCCCCC), 0, 0.5f));
        rv_recent.setAdapter(mMatchesAdapter);
    }

    public void setData(BattingBean batting, BowlingBean bowling, FieldingBean fielding, List<RecentMatchesBean> list) {
        mBattingList.clear();
        if (batting == null) {
            batting = new BattingBean();
        }
        mBattingList.add(new PerformanceBean(getString(R.string.matches2), String.valueOf(batting.getMatches())));
        mBattingList.add(new PerformanceBean(getString(R.string.innings), String.valueOf(batting.getInnings())));
        mBattingList.add(new PerformanceBean(getString(R.string.runs), String.valueOf(batting.getRuns())));
        mBattingList.add(new PerformanceBean(getString(R.string.balls_faced), String.valueOf(batting.getBalls_faced())));
        mBattingList.add(new PerformanceBean(getString(R.string.average), String.valueOf(batting.getAverage())));
        mBattingList.add(new PerformanceBean(getString(R.string.strike_rate2), String.valueOf(batting.getStrike_rate())));
        mBattingList.add(new PerformanceBean(getString(R.string.not_outs), String.valueOf(batting.getNot_outs())));
        mBattingList.add(new PerformanceBean("100S", String.valueOf(batting.getHundreds())));
        mBattingList.add(new PerformanceBean("50S", String.valueOf(batting.getFifties())));
        mBattingList.add(new PerformanceBean("6S", String.valueOf(batting.getSixes())));
        mBattingList.add(new PerformanceBean("4S", String.valueOf(batting.getFours())));

        mBowlingList.clear();
        if (bowling == null) {
            bowling = new BowlingBean();
        }
        mBowlingList.add(new PerformanceBean(getString(R.string.matches2), String.valueOf(bowling.getMatches())));
        mBowlingList.add(new PerformanceBean(getString(R.string.innings), String.valueOf(bowling.getInnings())));
        mBowlingList.add(new PerformanceBean(getString(R.string.balls_bowled2), String.valueOf(bowling.getBalls_bowled())));
        mBowlingList.add(new PerformanceBean(getString(R.string.runs), String.valueOf(bowling.getRuns())));
        mBowlingList.add(new PerformanceBean(getString(R.string.overs), String.valueOf(bowling.getOvers())));
        mBowlingList.add(new PerformanceBean(getString(R.string.maidens), String.valueOf(bowling.getMaidens())));
        mBowlingList.add(new PerformanceBean(getString(R.string.wickets), String.valueOf(bowling.getWickets())));
        mBowlingList.add(new PerformanceBean(getString(R.string.average), String.valueOf(bowling.getAverage())));
        mBowlingList.add(new PerformanceBean(getString(R.string.strike_rate2), String.valueOf(bowling.getStrike_rate())));
        mBowlingList.add(new PerformanceBean(getString(R.string.four_wkt), String.valueOf(bowling.getFour_wicket_hauls())));
        mBowlingList.add(new PerformanceBean(getString(R.string.five_wkt), String.valueOf(bowling.getFive_wicket_hauls())));

        mFieldingList.clear();
        if (fielding == null) {
            fielding = new FieldingBean();
        }
        mFieldingList.add(new PerformanceBean(getString(R.string.catches), String.valueOf(fielding.getCatches())));
        mFieldingList.add(new PerformanceBean(getString(R.string.stumpings), String.valueOf(fielding.getStumpings())));
        mFieldingList.add(new PerformanceBean(getString(R.string.run_outs), String.valueOf(fielding.getRunouts())));

        tv_performance.setText(getString(R.string.batting));
        mPerformanceAdapter.setNewData(mBattingList);

        if (list == null || list.size()<=0) {
            tv_matches.setVisibility(View.GONE);
            findViewById(R.id.ll_2).setVisibility(View.GONE);
        }else{
            mMatchesAdapter.setNewData(list);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_type:
                SparseArray<String> array = new SparseArray<>();
                array.put(0, "ODI");
                array.put(1, "T20I");
                array.put(2, "T20");
                array.put(3, "TEST");
                DialogUtil.showStringArrayDialog(getContext(), array, new DialogUtil.StringArrayDialogCallback() {
                    @Override
                    public void onItemClick(String text, int tag) {
                        ty_type.setText(text);
                        switch (tag) {
                            case 0:
                                mType = TYPE_ODI;
                                tv_matches.setText(getString(R.string.recent_odi_matches));
                                break;
                            case 1:
                                mType = TYPE_T20I;
                                tv_matches.setText(getString(R.string.recent_t20i_matches));
                                break;
                            case 2:
                                mType = TYPE_T20;
                                tv_matches.setText(getString(R.string.recent_t20_matches));
                                break;
                            case 3:
                                mType = TYPE_TEST;
                                tv_matches.setText(getString(R.string.recent_test_matches));
                                break;
                        }
                        ((PlayerProfileActivity)getActivity()).getCareerData(mType);
                    }
                });
                break;
            case R.id.ll_performance:
                SparseArray<String> temp = new SparseArray<>();
                temp.put(0, getString(R.string.batting));
                temp.put(1, getString(R.string.bowling));
                temp.put(2, getString(R.string.fielding));
                DialogUtil.showStringArrayDialog(getContext(), temp, new DialogUtil.StringArrayDialogCallback() {
                    @Override
                    public void onItemClick(String text, int tag) {
                        tv_performance.setText(text);
                        switch (tag) {
                            case 0:
                                mPerformanceAdapter.setNewData(mBattingList);
                                break;
                            case 1:
                                mPerformanceAdapter.setNewData(mBowlingList);
                                break;
                            case 2:
                                mPerformanceAdapter.setNewData(mFieldingList);
                                break;
                        }
                    }
                });
                break;
        }
    }
}
