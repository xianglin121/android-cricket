package com.longya.live.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.longya.live.R;
import com.longya.live.adapter.MatchesPlayedRecentlyAdapter;
import com.longya.live.adapter.TeamComparisonAdapter;
import com.longya.live.model.CricketFantasyBean;
import com.longya.live.presenter.cricket.TeamComparisonPresenter;
import com.longya.live.util.DpUtil;
import com.longya.live.util.GlideUtil;
import com.longya.live.view.MvpActivity;
import com.longya.live.view.cricket.TeamComparisonView;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/9/6
 */
public class TeamComparisonActivity extends MvpActivity<TeamComparisonPresenter> implements TeamComparisonView, View.OnClickListener {
    public static void forward(Context context, int matchId) {
        Intent intent = new Intent(context, TeamComparisonActivity.class);
        intent.putExtra("matchId", matchId);
        context.startActivity(intent);
    }

    private int mMatchId;

    private TextView tv_home_name;
    private TextView tv_away_name;
    private ImageView iv_home_logo;
    private ImageView iv_away_logo;
    private TextView tv_home_win_rate;
    private TextView tv_away_win_rate;
    private ProgressBar progress_bar;
    private TextView tv_home_name_two;
    private TextView tv_away_name_two;
    private LinearLayout ll_home_round;
    private LinearLayout ll_away_round;
    private ViewGroup ll_home;
    private ViewGroup ll_away;
    private RecyclerView rv_home;
    private MatchesPlayedRecentlyAdapter mHomeAdapter;
    private RecyclerView rv_away;
    private MatchesPlayedRecentlyAdapter mAwayAdapter;

    private TextView tv_total_num;
    private TextView tv_home_won;
    private TextView tv_home_won_num;
    private ProgressBar pb_home;
    private TextView tv_away_won;
    private TextView tv_away_won_num;
    private ProgressBar pb_away;
    private TextView tv_no_result_num;
    private ProgressBar pb_no_result;
    private TextView tv_home_name_three;
    private TextView tv_away_name_three;
    private RecyclerView rv_recent;
    private TeamComparisonAdapter mAdapter;

    @Override
    protected TeamComparisonPresenter createPresenter() {
        return new TeamComparisonPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_team_comparison;
    }

    @Override
    protected void initView() {
        mMatchId = getIntent().getIntExtra("matchId", 0);
        setTitleText(getString(R.string.team_comparison));

        tv_home_name = findViewById(R.id.tv_home_name);
        iv_home_logo = findViewById(R.id.iv_home_logo);
        tv_away_name = findViewById(R.id.tv_away_name);
        iv_away_logo = findViewById(R.id.iv_away_logo);
        tv_home_win_rate = findViewById(R.id.tv_home_win_rate);
        tv_away_win_rate = findViewById(R.id.tv_away_win_rate);
        progress_bar = findViewById(R.id.progress_bar);
        tv_home_name_two = findViewById(R.id.tv_home_name_two);
        tv_away_name_two = findViewById(R.id.tv_away_name_two);
        ll_home_round = findViewById(R.id.ll_home_round);
        ll_away_round = findViewById(R.id.ll_away_round);
        ll_home = findViewById(R.id.ll_home);
        ll_away = findViewById(R.id.ll_away);
        rv_home = findViewById(R.id.rv_home);
        rv_away = findViewById(R.id.rv_away);
        tv_total_num = findViewById(R.id.tv_total_num);
        tv_home_won = findViewById(R.id.tv_home_won);
        tv_home_won_num = findViewById(R.id.tv_home_won_num);
        tv_away_won = findViewById(R.id.tv_away_won);
        tv_away_won_num = findViewById(R.id.tv_away_won_num);
        tv_no_result_num = findViewById(R.id.tv_no_result_num);
        tv_home_name_three = findViewById(R.id.tv_home_name_three);
        tv_away_name_three = findViewById(R.id.tv_away_name_three);
        rv_recent = findViewById(R.id.rv_recent);
        pb_home = findViewById(R.id.pb_home);
        pb_away = findViewById(R.id.pb_away);
        pb_no_result = findViewById(R.id.pb_no_result);

        findViewById(R.id.ll_home).setOnClickListener(this);
        findViewById(R.id.ll_away).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_home:
                ll_home.setSelected(!ll_home.isSelected());
                if (ll_home.isSelected()) {
                    rv_home.setVisibility(View.VISIBLE);
                }else {
                    rv_home.setVisibility(View.GONE);
                }
                break;
            case R.id.ll_away:
                ll_away.setSelected(!ll_away.isSelected());
                if (ll_away.isSelected()) {
                    rv_away.setVisibility(View.VISIBLE);
                }else {
                    rv_away.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    protected void initData() {
        mvpPresenter.getData(mMatchId);
    }

    @Override
    public void getDataSuccess(CricketFantasyBean model) {
        if (model != null) {
            if (!TextUtils.isEmpty(model.getHome_name())) {
                tv_home_name.setText(model.getHome_name());
                tv_home_name_two.setText(model.getHome_name());
                tv_home_won.setText(model.getHome_name() + " won");
                tv_home_name_three.setText(model.getHome_name());
            }
            if (!TextUtils.isEmpty(model.getAway_name())) {
                tv_away_name.setText(model.getAway_name());
                tv_away_name_two.setText(model.getAway_name());
                tv_away_won.setText(model.getAway_name() + " won");
                tv_away_name_three.setText(model.getAway_name());
            }
            GlideUtil.loadTeamImageDefault(this, model.getHome_logo(), iv_home_logo);
            GlideUtil.loadTeamImageDefault(this, model.getAway_logo(), iv_away_logo);
            tv_home_win_rate.setText(model.getHome_win_probabilities() + "%");
            tv_away_win_rate.setText(model.getAway_win_probabilities() + "%");
            progress_bar.setProgress((int) model.getHome_win_probabilities());
            if (model.getHome() != null) {
                for (int i = 0; i < model.getHome().size(); i++) {
                    TextView textView = new TextView(this);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DpUtil.dp2px(24), DpUtil.dp2px(24));
                    layoutParams.rightMargin = DpUtil.dp2px(7);
                    textView.setLayoutParams(layoutParams);
                    if ("L".equals(model.getHome().get(i))) {
                        textView.setBackgroundColor(getResources().getColor(R.color.c_DC3C23));
                        textView.setText("L");
                    }else {
                        textView.setBackgroundColor(getResources().getColor(R.color.c_219430));
                        textView.setText("W");
                    }
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextSize(12);
                    textView.setTextColor(Color.WHITE);
                    ll_home_round.addView(textView);
                }
            }
            if (model.getAway() != null) {
                for (int i = 0; i < model.getAway().size(); i++) {
                    TextView textView = new TextView(this);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DpUtil.dp2px(24), DpUtil.dp2px(24));
                    layoutParams.rightMargin = DpUtil.dp2px(7);
                    textView.setLayoutParams(layoutParams);
                    if ("L".equals(model.getAway().get(i))) {
                        textView.setBackgroundColor(getResources().getColor(R.color.c_DC3C23));
                        textView.setText("L");
                    }else {
                        textView.setBackgroundColor(getResources().getColor(R.color.c_219430));
                        textView.setText("W");
                    }
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextSize(12);
                    textView.setTextColor(Color.WHITE);
                    ll_away_round.addView(textView);
                }
            }
            if (model.getHome_list() != null) {
                mHomeAdapter = new MatchesPlayedRecentlyAdapter(R.layout.item_matches_played_recently, model.getHome_list());
                rv_home.setLayoutManager(new LinearLayoutManager(this));
                rv_home.setAdapter(mHomeAdapter);
            }
            if (model.getAway_list() != null) {
                mAwayAdapter = new MatchesPlayedRecentlyAdapter(R.layout.item_matches_played_recently, model.getAway_list());
                rv_away.setLayoutManager(new LinearLayoutManager(this));
                rv_away.setAdapter(mAwayAdapter);
            }
            int total = model.getHome_win_num() + model.getAway_win_num() + model.getNo_result();
            tv_total_num.setText(String.valueOf(total));
            tv_home_won_num.setText(String.valueOf(model.getHome_win_num()));
            tv_away_won_num.setText(String.valueOf(model.getAway_win_num()));
            tv_no_result_num.setText(String.valueOf(model.getNo_result()));
            if (model.getHome_win_num() > 0) {
                pb_home.setProgress(new BigDecimal(model.getHome_win_num()).divide(new BigDecimal(total), 2, BigDecimal.ROUND_HALF_DOWN).multiply(new BigDecimal(100)).intValue());
            }else {
                pb_home.setProgress(0);
            }
            if (model.getAway_win_num() > 0) {
                pb_away.setProgress(new BigDecimal(model.getAway_win_num()).divide(new BigDecimal(total), 2, BigDecimal.ROUND_HALF_DOWN).multiply(new BigDecimal(100)).intValue());
            }else {
                pb_away.setProgress(0);
            }
            if (model.getNo_result() > 0) {
                pb_no_result.setProgress(new BigDecimal(model.getNo_result()).divide(new BigDecimal(total), 2, BigDecimal.ROUND_HALF_DOWN).multiply(new BigDecimal(100)).intValue());
            }else {
                pb_no_result.setProgress(0);
            }
            if (model.getHistorical_confrontation() != null) {
                mAdapter = new TeamComparisonAdapter(R.layout.item_team_comparison, model.getHistorical_confrontation());
                rv_recent.setLayoutManager(new LinearLayoutManager(this));
                rv_recent.setAdapter(mAdapter);
            }
        }
    }

    @Override
    public void getDataFail(String msg) {

    }
}
