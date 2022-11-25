package com.longya.live.fragment;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.longya.live.R;
import com.longya.live.activity.CricketInnerActivity;
import com.longya.live.activity.CricketStatsActivity;
import com.longya.live.model.CricketStatsBean;
import com.longya.live.view.BaseFragment;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/26
 */
public class CricketStatsFragment extends BaseFragment implements View.OnClickListener {
    public static CricketStatsFragment newInstance() {
        CricketStatsFragment fragment = new CricketStatsFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private int mId;
    private TextView tv_highest_score_name;
    private TextView tv_highest_score;
    private TextView tv_most_run_name;
    private TextView tv_most_run;
    private TextView tv_best_batting_name;
    private TextView tv_best_batting;
    private TextView tv_highest_strike_name;
    private TextView tv_highest_strike;
    private TextView tv_maximum_six_name;
    private TextView tv_maximum_six;
    private TextView tv_most_wicket_name;
    private TextView tv_most_wicket;
    private TextView tv_best_bowling_av_name;
    private TextView tv_best_bowling_av;
    private TextView tv_best_bowling_ec_name;
    private TextView tv_best_bowling_ec;
    private TextView tv_most_catches_name;
    private TextView tv_most_catches;
    private TextView tv_most_run_out_name;
    private TextView tv_most_run_out;
    private TextView tv_most_stumping_name;
    private TextView tv_most_stumping;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cricket_stats;
    }

    @Override
    protected void initUI() {
        tv_highest_score_name = findViewById(R.id.tv_highest_score_name);
        tv_highest_score = findViewById(R.id.tv_highest_score);
        tv_most_run_name = findViewById(R.id.tv_most_run_name);
        tv_most_run = findViewById(R.id.tv_most_run);
        tv_best_batting_name = findViewById(R.id.tv_best_batting_name);
        tv_best_batting = findViewById(R.id.tv_best_batting);
        tv_highest_strike_name = findViewById(R.id.tv_highest_strike_name);
        tv_highest_strike = findViewById(R.id.tv_highest_strike);
        tv_maximum_six_name = findViewById(R.id.tv_maximum_six_name);
        tv_maximum_six = findViewById(R.id.tv_maximum_six);
        tv_most_wicket_name = findViewById(R.id.tv_most_wicket_name);
        tv_most_wicket = findViewById(R.id.tv_most_wicket);
        tv_best_bowling_av_name = findViewById(R.id.tv_best_bowling_av_name);
        tv_best_bowling_av = findViewById(R.id.tv_best_bowling_av);
        tv_best_bowling_ec_name = findViewById(R.id.tv_best_bowling_ec_name);
        tv_best_bowling_ec = findViewById(R.id.tv_best_bowling_ec);
        tv_most_catches_name = findViewById(R.id.tv_most_catches_name);
        tv_most_catches = findViewById(R.id.tv_most_catches);
        tv_most_run_out_name = findViewById(R.id.tv_most_run_out_name);
        tv_most_run_out = findViewById(R.id.tv_most_run_out);
        tv_most_stumping_name = findViewById(R.id.tv_most_stumping_name);
        tv_most_stumping = findViewById(R.id.tv_most_stumping);


        findViewById(R.id.ll_highest_individual_score).setOnClickListener(this);
        findViewById(R.id.ll_most_runs).setOnClickListener(this);
        findViewById(R.id.ll_best_batting_average).setOnClickListener(this);
        findViewById(R.id.ll_highest_strike_rate).setOnClickListener(this);
        findViewById(R.id.ll_maximum_sixes).setOnClickListener(this);
        findViewById(R.id.ll_most_wickets).setOnClickListener(this);
        findViewById(R.id.ll_best_bowling_average).setOnClickListener(this);
        findViewById(R.id.ll_best_bowling_economy).setOnClickListener(this);
        findViewById(R.id.ll_most_catches).setOnClickListener(this);
        findViewById(R.id.ll_most_run_outs).setOnClickListener(this);
        findViewById(R.id.ll_most_stumpings).setOnClickListener(this);
    }

    @Override
    protected void initData() {
    }

    public void setData(int id, CricketStatsBean bean) {
        mId = id;
        if (bean != null) {
            if (!TextUtils.isEmpty(bean.getHighest_score_name())) {
                tv_highest_score_name.setText(bean.getHighest_score_name() + " - ");
                tv_highest_score.setText(bean.getHighest_score());
            }
            if (!TextUtils.isEmpty(bean.getMost_runs_name())) {
                tv_most_run_name.setText(bean.getMost_runs_name() + " - ");
                tv_most_run.setText(bean.getMost_runs());
            }
            if (!TextUtils.isEmpty(bean.getBest_battingAv_name())) {
                tv_best_batting_name.setText(bean.getBest_battingAv_name() + " - ");
                tv_best_batting.setText(bean.getBest_battingAv());
            }
            if (!TextUtils.isEmpty(bean.getHighest_strikeRate_name())) {
                tv_highest_strike_name.setText(bean.getHighest_strikeRate_name() + " - ");
                tv_highest_strike.setText(bean.getHighest_strikeRate());
            }
            if (!TextUtils.isEmpty(bean.getMaximum_sixes_name())) {
                tv_maximum_six_name.setText(bean.getMaximum_sixes_name() + " - ");
                tv_maximum_six.setText(bean.getMaximum_sixes());
            }
            if (!TextUtils.isEmpty(bean.getMost_wickets_name())) {
                tv_most_wicket_name.setText(bean.getMost_wickets_name() + " - ");
                tv_most_wicket.setText(bean.getMost_wickets());
            }
            if (!TextUtils.isEmpty(bean.getBest_bowling_average_name())) {
                tv_best_bowling_av_name.setText(bean.getBest_bowling_average_name() + " - ");
                tv_best_bowling_av.setText(bean.getBest_bowling_average());
            }
            if (!TextUtils.isEmpty(bean.getBest_bowling_economy_name())) {
                tv_best_bowling_ec_name.setText(bean.getBest_bowling_economy_name() + " - ");
                tv_best_bowling_ec.setText(bean.getBest_bowling_economy());
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_highest_individual_score:
                CricketStatsActivity.forward(getContext(), 0, mId, ((CricketInnerActivity)getActivity()).mType);
                break;
            case R.id.ll_most_runs:
                CricketStatsActivity.forward(getContext(), 1, mId, ((CricketInnerActivity)getActivity()).mType);
                break;
            case R.id.ll_best_batting_average:
                CricketStatsActivity.forward(getContext(), 2, mId, ((CricketInnerActivity)getActivity()).mType);
                break;
            case R.id.ll_highest_strike_rate:
                CricketStatsActivity.forward(getContext(), 3, mId, ((CricketInnerActivity)getActivity()).mType);
                break;
            case R.id.ll_maximum_sixes:
                CricketStatsActivity.forward(getContext(), 4, mId, ((CricketInnerActivity)getActivity()).mType);
                break;
            case R.id.ll_most_wickets:
                CricketStatsActivity.forward(getContext(), 5, mId, ((CricketInnerActivity)getActivity()).mType);
                break;
            case R.id.ll_best_bowling_average:
                CricketStatsActivity.forward(getContext(), 6, mId, ((CricketInnerActivity)getActivity()).mType);
                break;
            case R.id.ll_best_bowling_economy:
                CricketStatsActivity.forward(getContext(), 7, mId, ((CricketInnerActivity)getActivity()).mType);
                break;
            case R.id.ll_most_catches:
                CricketStatsActivity.forward(getContext(), 8, mId, ((CricketInnerActivity)getActivity()).mType);
                break;
            case R.id.ll_most_run_outs:
                CricketStatsActivity.forward(getContext(), 9, mId, ((CricketInnerActivity)getActivity()).mType);
                break;
            case R.id.ll_most_stumpings:
                CricketStatsActivity.forward(getContext(), 10, mId, ((CricketInnerActivity)getActivity()).mType);
                break;
        }
    }
}
