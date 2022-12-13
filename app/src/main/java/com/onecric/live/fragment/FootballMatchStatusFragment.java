package com.onecric.live.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.dinuscxj.progressbar.CircleProgressBar;
import com.onecric.live.R;
import com.onecric.live.adapter.FootballEventLiveAdapter;
import com.onecric.live.adapter.FootballMatchImportantAdapter;
import com.onecric.live.adapter.FootballMatchStatisticAdapter;
import com.onecric.live.custom.RaceStatusView;
import com.onecric.live.custom.ScoreProgressBar;
import com.onecric.live.model.FootballDetailBean;
import com.onecric.live.model.FootballEventBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.StatisticBean;
import com.onecric.live.presenter.match.FootballMatchStatusPresenter;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.util.SpUtil;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.match.FootballMatchStatusView;
import com.zyyoona7.popup.EasyPopup;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FootballMatchStatusFragment extends MvpFragment<FootballMatchStatusPresenter> implements FootballMatchStatusView, View.OnClickListener {

    public static FootballMatchStatusFragment newInstance() {
        FootballMatchStatusFragment fragment = new FootballMatchStatusFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private ImageView iv_team_one;
    private ImageView iv_team_two;
    private RaceStatusView raceStatusView;
    private CircleProgressBar progressbar_one;
    private TextView tv_home_attack, tv_away_attack;
    private CircleProgressBar progressbar_two;
    private TextView tv_home_danger_attack, tv_away_danger_attack;
    private CircleProgressBar progressbar_three;
    private TextView tv_home_control_ball, tv_away_control_ball;
    private TextView tv_home_corner_kick, tv_home_yc, tv_home_rc;
    private TextView tv_away_corner_kick, tv_away_yc, tv_away_rc;
    private TextView tv_home_shot_on_goal, tv_away_shot_on_goal;
    private ScoreProgressBar pb_shot_on_goal;
    private TextView tv_home_miss_the_goal, tv_away_miss_the_goal;
    private ScoreProgressBar pb_miss_the_goal;
    private TextView tv_important_event, tv_event_live, tv_statistics;
    private ImageView iv_important_logo_one, iv_important_logo_two;
    private TextView tv_important_name_one, tv_important_name_two;
    private ConstraintLayout cl_important;
    private RecyclerView rv_important;
    private TextView tv_venue;
    private FootballMatchImportantAdapter mImportantAdapter;
    private RecyclerView rv_event_live;
    private FootballEventLiveAdapter mLiveAdapter;
    private List<FootballEventBean> importantList;
    private List<FootballEventBean> importantBallList;
    private ConstraintLayout cl_statistic;
    private ImageView iv_statistic_logo_one, iv_statistic_logo_two;
    private TextView tv_statistic_name_one, tv_statistic_name_two;
    private RecyclerView rv_statistic;
    private FootballMatchStatisticAdapter mStatisticAdapter;
    private ImageView iv_goal;
    private boolean isAll = true;//是否显示全部事件
    private ImageView iv_detail;
    private EasyPopup popup;
    private boolean isShow;//是否显示详情弹窗
    private TextView tv_environment;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_football_match_status;
    }

    @Override
    protected FootballMatchStatusPresenter createPresenter() {
        return new FootballMatchStatusPresenter(this);
    }

    @Override
    protected void initUI() {
        importantList = new ArrayList<>();
        importantBallList = new ArrayList<>();
        iv_team_one = rootView.findViewById(R.id.iv_team_one);
        iv_team_two = rootView.findViewById(R.id.iv_team_two);
        raceStatusView = rootView.findViewById(R.id.raceStatusView);
        progressbar_one = rootView.findViewById(R.id.circleProgressbar_one);
        tv_home_attack = rootView.findViewById(R.id.tv_home_attack);
        tv_away_attack = rootView.findViewById(R.id.tv_away_attack);
        progressbar_two = rootView.findViewById(R.id.circleProgressbar_two);
        tv_home_danger_attack = rootView.findViewById(R.id.tv_home_danger_attack);
        tv_away_danger_attack = rootView.findViewById(R.id.tv_away_danger_attack);
        progressbar_three = rootView.findViewById(R.id.circleProgressbar_three);
        tv_home_control_ball = rootView.findViewById(R.id.tv_home_control_ball);
        tv_away_control_ball = rootView.findViewById(R.id.tv_away_control_ball);
        tv_home_corner_kick = rootView.findViewById(R.id.tv_home_corner_kick);
        tv_home_yc = rootView.findViewById(R.id.tv_home_yc);
        tv_home_rc = rootView.findViewById(R.id.tv_home_rc);
        tv_away_corner_kick = rootView.findViewById(R.id.tv_away_corner_kick);
        tv_away_yc = rootView.findViewById(R.id.tv_away_yc);
        tv_away_rc = rootView.findViewById(R.id.tv_away_rc);
        tv_home_shot_on_goal = rootView.findViewById(R.id.tv_home_shot_on_goal);
        tv_away_shot_on_goal = rootView.findViewById(R.id.tv_away_shot_on_goal);
        pb_shot_on_goal = rootView.findViewById(R.id.pb_shot_on_goal);
        tv_home_miss_the_goal = rootView.findViewById(R.id.tv_home_miss_the_goal);
        tv_away_miss_the_goal = rootView.findViewById(R.id.tv_away_miss_the_goal);
        pb_miss_the_goal = rootView.findViewById(R.id.pb_miss_the_goal);
        tv_important_event = rootView.findViewById(R.id.tv_important_event);
        tv_event_live = rootView.findViewById(R.id.tv_event_live);
        tv_statistics = rootView.findViewById(R.id.tv_statistics);
        iv_important_logo_one = rootView.findViewById(R.id.iv_important_logo_one);
        iv_important_logo_two = rootView.findViewById(R.id.iv_important_logo_two);
        tv_important_name_one = rootView.findViewById(R.id.tv_important_name_one);
        tv_important_name_two = rootView.findViewById(R.id.tv_important_name_two);
        cl_important = rootView.findViewById(R.id.cl_important);
        rv_important = rootView.findViewById(R.id.rv_important);
        tv_venue = rootView.findViewById(R.id.tv_venue);
        rv_event_live = rootView.findViewById(R.id.rv_event_live);
        cl_statistic = rootView.findViewById(R.id.cl_statistic);
        iv_statistic_logo_one = rootView.findViewById(R.id.iv_statistic_logo_one);
        iv_statistic_logo_two = rootView.findViewById(R.id.iv_statistic_logo_two);
        tv_statistic_name_one = rootView.findViewById(R.id.tv_statistic_name_one);
        tv_statistic_name_two = rootView.findViewById(R.id.tv_statistic_name_two);
        rv_statistic = rootView.findViewById(R.id.rv_statistic);
        iv_goal = rootView.findViewById(R.id.iv_goal);
        iv_detail = rootView.findViewById(R.id.iv_detail);
        tv_environment = rootView.findViewById(R.id.tv_environment);

        tv_important_event.setOnClickListener(this);
        tv_event_live.setOnClickListener(this);
        tv_statistics.setOnClickListener(this);
        iv_goal.setOnClickListener(this);
        iv_detail.setOnClickListener(this);

        tv_important_event.setSelected(true);
    }

    @Override
    protected void initData() {
        popup = EasyPopup.create().setContentView(getContext(), R.layout.view_football_match_event_introduction).setFocusAndOutsideEnable(true).apply();
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                isShow = false;
                iv_detail.setBackgroundResource(R.mipmap.icon_football_match_detail);
            }
        });

        mImportantAdapter = new FootballMatchImportantAdapter(R.layout.item_football_match_important, new ArrayList<>());
        rv_important.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_important.setAdapter(mImportantAdapter);

        mLiveAdapter = new FootballEventLiveAdapter(R.layout.item_football_event_live, new ArrayList<>());
        rv_event_live.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_event_live.setAdapter(mLiveAdapter);

        mStatisticAdapter = new FootballMatchStatisticAdapter(R.layout.item_football_match_statistic, new ArrayList<>());
        rv_statistic.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_statistic.setAdapter(mStatisticAdapter);
    }

    public void setData(FootballDetailBean detailBean) {
        GlideUtil.loadTeamImageDefault(getContext(), detailBean.getHome_team_log(), iv_team_one);
        GlideUtil.loadTeamImageDefault(getContext(), detailBean.getAway_team_log(), iv_team_two);
        raceStatusView.setData(detailBean.getTrend());
        if (detailBean.getHome_data() != null && detailBean.getAway_data() != null) {
            //进攻
            int homeAttack = detailBean.getHome_data().get(3);
            int awayAttack = detailBean.getAway_data().get(3);
            progressbar_one.setMax(homeAttack + awayAttack);
            if (homeAttack >= awayAttack) {
                double awayPercent = 0;
                if (awayAttack > 0){
                    awayPercent = new BigDecimal(awayAttack).divide(new BigDecimal(homeAttack+awayAttack), 2, RoundingMode.HALF_UP).doubleValue();
                }
                int awayDegree = new BigDecimal(360).multiply(new BigDecimal(awayPercent)).add(new BigDecimal(-90)).intValue();
                progressbar_one.setProgressStartColor(getResources().getColor(R.color.c_F84A4B));
                progressbar_one.setProgressEndColor(getResources().getColor(R.color.c_F84A4B));
                progressbar_one.setProgress(homeAttack);
                progressbar_one.setStartDegree(awayDegree);
            }else {
                progressbar_one.setProgressStartColor(getResources().getColor(R.color.c_687AE0));
                progressbar_one.setProgressEndColor(getResources().getColor(R.color.c_687AE0));
                progressbar_one.setProgress(awayAttack);
            }
            if (homeAttack > 0) {
//                tv_home_attack.setText(new BigDecimal(homeAttack).divide(new BigDecimal(homeAttack+awayAttack), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).intValue() + "");
                tv_home_attack.setText(homeAttack + "");
            }else {
                tv_home_attack.setText("0");
            }
            if (awayAttack > 0) {
//                tv_away_attack.setText(new BigDecimal(awayAttack).divide(new BigDecimal(homeAttack+awayAttack), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).intValue() + "");
                tv_away_attack.setText(awayAttack + "");
            }else {
                tv_away_attack.setText("0");
            }
            //危险进攻
            int homeDangerAttack = detailBean.getHome_data().get(4);
            int awayDangerAttack = detailBean.getAway_data().get(4);
            progressbar_two.setMax(homeDangerAttack + awayDangerAttack);
            if (homeDangerAttack >= awayDangerAttack) {
                double awayPercent = 0;
                if (awayDangerAttack > 0) {
                    awayPercent = new BigDecimal(awayDangerAttack).divide(new BigDecimal(homeDangerAttack +awayDangerAttack), 2, RoundingMode.HALF_UP).doubleValue();
                }
                int awayDegree = new BigDecimal(360).multiply(new BigDecimal(awayPercent)).add(new BigDecimal(-90)).intValue();
                progressbar_two.setProgressStartColor(getResources().getColor(R.color.c_F84A4B));
                progressbar_two.setProgressEndColor(getResources().getColor(R.color.c_F84A4B));
                progressbar_two.setProgress(homeDangerAttack);
                progressbar_two.setStartDegree(awayDegree);
            }else {
                progressbar_two.setProgressStartColor(getResources().getColor(R.color.c_687AE0));
                progressbar_two.setProgressEndColor(getResources().getColor(R.color.c_687AE0));
                progressbar_two.setProgress(awayDangerAttack);
            }
            if (homeDangerAttack > 0) {
//                tv_home_danger_attack.setText(new BigDecimal(homeDangerAttack).divide(new BigDecimal(homeDangerAttack+awayDangerAttack), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).intValue() + "");
                tv_home_danger_attack.setText(homeDangerAttack + "");
            }else {
                tv_home_danger_attack.setText("0");
            }
            if (awayDangerAttack > 0) {
//                tv_away_danger_attack.setText(new BigDecimal(awayDangerAttack).divide(new BigDecimal(homeDangerAttack+awayDangerAttack), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).intValue() + "");
                tv_away_danger_attack.setText(awayDangerAttack + "");
            }else {
                tv_away_danger_attack.setText("0");
            }
            //控球率
            int homeControlBall = detailBean.getHome_data().get(5);
            int awayControlBall = detailBean.getAway_data().get(5);
            progressbar_three.setMax(homeControlBall + awayControlBall);
            if (homeControlBall >= awayControlBall) {
                double awayPercent = 0;
                if (awayControlBall > 0) {
                    awayPercent = new BigDecimal(awayControlBall).divide(new BigDecimal(homeControlBall +awayControlBall), 2, RoundingMode.HALF_UP).doubleValue();
                }
                int awayDegree = new BigDecimal(360).multiply(new BigDecimal(awayPercent)).add(new BigDecimal(-90)).intValue();
                progressbar_three.setProgressStartColor(getResources().getColor(R.color.c_F84A4B));
                progressbar_three.setProgressEndColor(getResources().getColor(R.color.c_F84A4B));
                progressbar_three.setProgress(homeControlBall);
                progressbar_three.setStartDegree(awayDegree);
            }else {
                progressbar_three.setProgressStartColor(getResources().getColor(R.color.c_687AE0));
                progressbar_three.setProgressEndColor(getResources().getColor(R.color.c_687AE0));
                progressbar_three.setProgress(awayControlBall);
            }
            if (homeControlBall > 0) {
//                tv_home_control_ball.setText(new BigDecimal(homeControlBall).divide(new BigDecimal(homeControlBall+awayControlBall), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).intValue() + "");
                tv_home_control_ball.setText(homeControlBall + "");
            }else {
                tv_home_control_ball.setText("0");
            }
            if (awayControlBall > 0) {
//                tv_away_control_ball.setText(new BigDecimal(awayControlBall).divide(new BigDecimal(homeControlBall+awayControlBall), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).intValue() + "");
                tv_away_control_ball.setText(awayControlBall + "");
            }else {
                tv_away_control_ball.setText("0");
            }
            tv_home_corner_kick.setText(String.valueOf(detailBean.getHome_data().get(8)));
            tv_home_yc.setText(String.valueOf(detailBean.getHome_data().get(1)));
            tv_home_rc.setText(String.valueOf(detailBean.getHome_data().get(2)));
            tv_away_corner_kick.setText(String.valueOf(detailBean.getAway_data().get(8)));
            tv_away_yc.setText(String.valueOf(detailBean.getAway_data().get(1)));
            tv_away_rc.setText(String.valueOf(detailBean.getAway_data().get(2)));
            //射正球门
            tv_home_shot_on_goal.setText(String.valueOf(detailBean.getHome_data().get(6)));
            tv_away_shot_on_goal.setText(String.valueOf(detailBean.getAway_data().get(6)));
            int max = detailBean.getHome_data().get(6)+detailBean.getAway_data().get(6);
            float homePercent = 0;
            if (detailBean.getHome_data().get(6) > 0) {
                homePercent = new BigDecimal(detailBean.getHome_data().get(6)).divide(new BigDecimal(max), 2, RoundingMode.HALF_UP).floatValue();
            }
            float awayPercent = 0;
            if (detailBean.getAway_data().get(6) > 0) {
                awayPercent = new BigDecimal(detailBean.getAway_data().get(6)).divide(new BigDecimal(max), 2, RoundingMode.HALF_UP).floatValue();
            }
            int leftColor = 0;
            int rightColor = 0;
            if (detailBean.getHome_data().get(6) > detailBean.getAway_data().get(6)) {
                leftColor = getResources().getColor(R.color.c_F84A4B);
                rightColor = getResources().getColor(R.color.c_4D687AE0);
            }else if (detailBean.getHome_data().get(6) < detailBean.getAway_data().get(6)) {
                leftColor = getResources().getColor(R.color.c_4DF84A4B);
                rightColor = getResources().getColor(R.color.c_687AE0);
            }else {
                leftColor = getResources().getColor(R.color.c_F84A4B);
                rightColor = getResources().getColor(R.color.c_687AE0);
            }
            pb_shot_on_goal.setProgress(leftColor, rightColor, homePercent, awayPercent);
            //射偏球门
            tv_home_miss_the_goal.setText(String.valueOf(detailBean.getHome_data().get(7)));
            tv_away_miss_the_goal.setText(String.valueOf(detailBean.getAway_data().get(7)));
            int maxTwo = detailBean.getHome_data().get(7)+detailBean.getAway_data().get(7);
            float homePercentTwo = 0;
            if (detailBean.getHome_data().get(7) > 0) {
                homePercentTwo = new BigDecimal(detailBean.getHome_data().get(7)).divide(new BigDecimal(maxTwo), 2, RoundingMode.HALF_UP).floatValue();
            }
            float awayPercentTwo = 0;
            if (detailBean.getAway_data().get(7) > 0) {
                awayPercentTwo = new BigDecimal(detailBean.getAway_data().get(7)).divide(new BigDecimal(maxTwo), 2, RoundingMode.HALF_UP).floatValue();
            }
            int pbLeftColor = 0;
            int pbRightColor = 0;
            if (detailBean.getHome_data().get(7) > detailBean.getAway_data().get(7)) {
                pbLeftColor = getResources().getColor(R.color.c_F84A4B);
                pbRightColor = getResources().getColor(R.color.c_4D687AE0);
            }else if (detailBean.getHome_data().get(7) < detailBean.getAway_data().get(7)) {
                pbLeftColor = getResources().getColor(R.color.c_4DF84A4B);
                pbRightColor = getResources().getColor(R.color.c_687AE0);
            }else {
                pbLeftColor = getResources().getColor(R.color.c_F84A4B);
                pbRightColor = getResources().getColor(R.color.c_687AE0);
            }
            pb_miss_the_goal.setProgress(pbLeftColor, pbRightColor, homePercentTwo, awayPercentTwo);
            //重要事件
            GlideUtil.loadTeamImageDefault(getContext(), detailBean.getHome_team_log(), iv_important_logo_one);
            GlideUtil.loadTeamImageDefault(getContext(), detailBean.getAway_team_log(), iv_important_logo_two);
        }
        //重要事件
        if (detailBean.getTlive() != null) {
            for (int i = 0; i < detailBean.getTlive().size(); i++) {
                if (detailBean.getTlive().get(i).getPosition() != 0) {
                    importantList.add(detailBean.getTlive().get(i));
                    if (detailBean.getTlive().get(i).getType() == 1 || detailBean.getTlive().get(i).getType() == 17) {
                        importantBallList.add(detailBean.getTlive().get(i));
                    }
                }
            }
            Collections.reverse(importantList);
            mImportantAdapter.setNewData(importantList);
        }
        //场地信息
        if (detailBean.getSpace() != null) {
            if ("2".equals(SpUtil.getInstance().getStringValue(SpUtil.FOOTBALL_LANGUAGE))) {
                if (!TextUtils.isEmpty(detailBean.getSpace().getName_en())) {
                    tv_venue.setText(detailBean.getSpace().getName_en());
                }else {
                    tv_venue.setText(getContext().getString(R.string.tip_no_venue_information));
                }
            }else {
                if (!TextUtils.isEmpty(detailBean.getSpace().getName_zh())) {
                    tv_venue.setText(detailBean.getSpace().getName_zh());
                }else {
                    tv_venue.setText(getContext().getString(R.string.tip_no_venue_information));
                }
            }
        }
        //事件直播
        List<FootballEventBean> tlive = detailBean.getTlive();
        Collections.reverse(tlive);
        mLiveAdapter.setNewData(tlive);
        //技术统计
        GlideUtil.loadTeamImageDefault(getContext(), detailBean.getHome_team_log(), iv_statistic_logo_one);
        GlideUtil.loadTeamImageDefault(getContext(), detailBean.getAway_team_log(), iv_statistic_logo_two);
        if (detailBean.getTechnology() != null && detailBean.getTechnology().size() > 1) {
            List<StatisticBean> list = new ArrayList<>();
            JSONObject homeJsonObject = (JSONObject) JSONObject.toJSON(detailBean.getTechnology().get(0));
            JSONObject awayJsonObject = (JSONObject) JSONObject.toJSON(detailBean.getTechnology().get(1));
            Map<String, Object> homeInnerMap = homeJsonObject.getInnerMap();
            Map<String, Object> awayInnerMap = awayJsonObject.getInnerMap();
            for (String key : homeInnerMap.keySet()) {
                StatisticBean statisticBean = new StatisticBean();
                statisticBean.setName(getStatisticName(key));
                statisticBean.setHomeData(String.valueOf((Integer) homeInnerMap.get(key)));
                statisticBean.setAwayData(String.valueOf((Integer) awayInnerMap.get(key)));
                list.add(statisticBean);
            }
            mStatisticAdapter.setNewData(list);
        }
        if ("1".equals(SpUtil.getInstance().getStringValue(SpUtil.FOOTBALL_LANGUAGE))) {
            if (!TextUtils.isEmpty(detailBean.getHome_team_name_zht())) {
                tv_important_name_one.setText(detailBean.getHome_team_name_zht());
            }else {
                tv_important_name_one.setText("");
            }
            if (!TextUtils.isEmpty(detailBean.getAway_team_name_zht())) {
                tv_important_name_two.setText(detailBean.getAway_team_name_zht());
            }else {
                tv_important_name_two.setText("");
            }
            if (!TextUtils.isEmpty(detailBean.getHome_team_name_zht())) {
                tv_statistic_name_one.setText(detailBean.getHome_team_name_zht());
            }else {
                tv_statistic_name_one.setText("");
            }
            if (!TextUtils.isEmpty(detailBean.getAway_team_name_zht())) {
                tv_statistic_name_two.setText(detailBean.getAway_team_name_zht());
            }else {
                tv_statistic_name_two.setText("");
            }
        }else if ("2".equals(SpUtil.getInstance().getStringValue(SpUtil.FOOTBALL_LANGUAGE))) {
            if (!TextUtils.isEmpty(detailBean.getHome_team_name_en())) {
                tv_important_name_one.setText(detailBean.getHome_team_name_en());
            }else {
                tv_important_name_one.setText("");
            }
            if (!TextUtils.isEmpty(detailBean.getAway_team_name_en())) {
                tv_important_name_two.setText(detailBean.getAway_team_name_en());
            }else {
                tv_important_name_two.setText("");
            }
            if (!TextUtils.isEmpty(detailBean.getHome_team_name_en())) {
                tv_statistic_name_one.setText(detailBean.getHome_team_name_en());
            }else {
                tv_statistic_name_one.setText("");
            }
            if (!TextUtils.isEmpty(detailBean.getAway_team_name_en())) {
                tv_statistic_name_two.setText(detailBean.getAway_team_name_en());
            }else {
                tv_statistic_name_two.setText("");
            }
        }else {
            if (!TextUtils.isEmpty(detailBean.getHome_team_name_zh())) {
                tv_important_name_one.setText(detailBean.getHome_team_name_zh());
            }else {
                tv_important_name_one.setText("");
            }
            if (!TextUtils.isEmpty(detailBean.getAway_team_name_zh())) {
                tv_important_name_two.setText(detailBean.getAway_team_name_zh());
            }else {
                tv_important_name_two.setText("");
            }
            if (!TextUtils.isEmpty(detailBean.getHome_team_name_zh())) {
                tv_statistic_name_one.setText(detailBean.getHome_team_name_zh());
            }else {
                tv_statistic_name_one.setText("");
            }
            if (!TextUtils.isEmpty(detailBean.getAway_team_name_zh())) {
                tv_statistic_name_two.setText(detailBean.getAway_team_name_zh());
            }else {
                tv_statistic_name_two.setText("");
            }
        }
        if (detailBean.getEnvironment() != null) {
            String str = "";
            if (!TextUtils.isEmpty(detailBean.getEnvironment().getWeather_str())) {
                str = str + detailBean.getEnvironment().getWeather_str();
            }
            if (!TextUtils.isEmpty(detailBean.getEnvironment().getTemperature())) {
                str = str + detailBean.getEnvironment().getTemperature() + ",";
            }
            if (!TextUtils.isEmpty(detailBean.getEnvironment().getWind())) {
                str = str + "风速" + detailBean.getEnvironment().getWind() + ",";
            }
            if (!TextUtils.isEmpty(detailBean.getEnvironment().getPressure())) {
                str = str + "气压" + detailBean.getEnvironment().getPressure() + ",";
            }
            if (!TextUtils.isEmpty(detailBean.getEnvironment().getHumidity())) {
                str = str + "湿度" + detailBean.getEnvironment().getHumidity() + ",";
            }
            tv_environment.setText(str);
        }
    }

    private String getStatisticName(String name) {
        String statistic = "";
        switch (name){
            case "goals":
                statistic = "进球";
                break;
            case "penalty":
                statistic = "点球";
                break;
            case "dribble":
                statistic = "过人";
                break;
            case "dribble_succ":
                statistic = "过人成功";
                break;
            case "clearances":
                statistic = "解围";
                break;
            case "blocked_shots":
                statistic = "有效阻挡";
                break;
            case "interceptions":
                statistic = "拦截";
                break;
            case "tackles":
                statistic = "抢断";
                break;
            case "passes":
                statistic = "传球";
                break;
            case "passes_accuracy":
                statistic = "传球成功";
                break;
            case "key_passes":
                statistic = "关键传球";
                break;
            case "crosses":
                statistic = "传中球";
                break;
            case "crosses_accuracy":
                statistic = "传中球成功";
                break;
            case "long_balls":
                statistic = "长传";
                break;
            case "long_balls_accuracy":
                statistic = "成功长传";
                break;
            case "duels":
                statistic = "1对1拼抢";
                break;
            case "duels_won":
                statistic = "1对1拼抢成功";
                break;
            case "dispossessed":
                statistic = "丢球";
                break;
            case "fouls":
                statistic = "犯规";
                break;
            case "was_fouled":
                statistic = "被侵犯";
                break;
            case "offsides":
                statistic = "越位";
                break;
            case "yellow2red_cards":
                statistic = "两黄变红";
                break;
            case "saves":
                statistic = "扑救";
                break;
            case "punches":
                statistic = "拳击球";
                break;
            case "runs_out":
                statistic = "守门员出击";
                break;
            case "runs_out_succ":
                statistic = "守门员出击成功";
                break;
            case "good_high_claim":
                statistic = "高空出击";
                break;
        }
        return statistic;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataSuccess(JsonBean model) {

    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_important_event:
                if (tv_important_event.isSelected()) {
                    return;
                }
                tv_important_event.setSelected(true);
                tv_event_live.setSelected(false);
                tv_statistics.setSelected(false);
                cl_important.setVisibility(View.VISIBLE);
                rv_event_live.setVisibility(View.GONE);
                cl_statistic.setVisibility(View.GONE);
                iv_goal.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_event_live:
                if (tv_event_live.isSelected()) {
                    return;
                }
                tv_important_event.setSelected(false);
                tv_event_live.setSelected(true);
                tv_statistics.setSelected(false);
                cl_important.setVisibility(View.GONE);
                rv_event_live.setVisibility(View.VISIBLE);
                cl_statistic.setVisibility(View.GONE);
                iv_goal.setVisibility(View.GONE);
                break;
            case R.id.tv_statistics:
                if (tv_statistics.isSelected()) {
                    return;
                }
                tv_important_event.setSelected(false);
                tv_event_live.setSelected(false);
                tv_statistics.setSelected(true);
                cl_important.setVisibility(View.GONE);
                rv_event_live.setVisibility(View.GONE);
                cl_statistic.setVisibility(View.VISIBLE);
                iv_goal.setVisibility(View.GONE);
                break;
            case R.id.iv_detail:
//                View view = LayoutInflater.from(this).inflate(R.layout.view_football_match_event_introduction, null);
//                PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//                popupWindow.setOutsideTouchable(true);
//                popupWindow.showAsDropDown(iv_detail, -DpUtil.dp2px(50), 0, Gravity.START);
                isShow = !isShow;
                if (isShow) {
                    iv_detail.setBackgroundResource(R.mipmap.icon_football_match_pack_up);
                    popup.showAtAnchorView(iv_detail, YGravity.CENTER, XGravity.LEFT, 0, 0);
                }else {
                    iv_detail.setBackgroundResource(R.mipmap.icon_football_match_detail);
                }
                break;
            case R.id.iv_goal:
                isAll = !isAll;
                if (isAll) {
                    iv_goal.setBackgroundResource(R.mipmap.icon_football_match_goal);
                    mImportantAdapter.setNewData(importantList);
                }else {
                    iv_goal.setBackgroundResource(R.mipmap.icon_football_match_all);
                    mImportantAdapter.setNewData(importantBallList);
                }
                break;
        }
    }
}
