package com.longya.live.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.longya.live.R;
import com.longya.live.adapter.BasketballEventLiveAdapter;
import com.longya.live.adapter.BasketballMatchStatisticAdapter;
import com.longya.live.model.BasketballDetailBean;
import com.longya.live.model.EventBean;
import com.longya.live.model.JsonBean;
import com.longya.live.model.StatisticBean;
import com.longya.live.presenter.match.BasketballMatchStatusPresenter;
import com.longya.live.util.GlideUtil;
import com.longya.live.util.SpUtil;
import com.longya.live.view.MvpFragment;
import com.longya.live.view.match.BasketballMatchStatusView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BasketballMatchStatusFragment extends MvpFragment<BasketballMatchStatusPresenter> implements BasketballMatchStatusView, View.OnClickListener {

    public static BasketballMatchStatusFragment newInstance() {
        BasketballMatchStatusFragment fragment = new BasketballMatchStatusFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private BasketballDetailBean mDetailBean;
    private TextView tv_team_name_one, tv_team_name_two;
    private TextView tv_home_score_one, tv_home_score_two, tv_home_score_three, tv_home_score_four, tv_home_score_total;
    private TextView tv_away_score_one, tv_away_score_two, tv_away_score_three, tv_away_score_four, tv_away_score_total;
    private CircleProgressBar progressbar_one;
    private TextView tv_home_team_shot, tv_away_team_shot;
    private CircleProgressBar progressbar_two;
    private TextView tv_home_team_three_point, tv_away_team_three_point;
    private CircleProgressBar progressbar_three;
    private TextView tv_home_team_free_throw, tv_away_team_free_throw;
    private TextView tv_event_live, tv_statistics;
    private RecyclerView rv_event_live;
    private BasketballEventLiveAdapter mLiveAdapter;
    private ConstraintLayout cl_statistic;
    private ImageView iv_statistic_logo_one, iv_statistic_logo_two;
    private TextView tv_statistic_name_one, tv_statistic_name_two;
    private RecyclerView rv_statistic;
    private BasketballMatchStatisticAdapter mStatisticAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_basketball_match_status;
    }

    @Override
    protected BasketballMatchStatusPresenter createPresenter() {
        return new BasketballMatchStatusPresenter(this);
    }

    @Override
    protected void initUI() {
        tv_team_name_one = rootView.findViewById(R.id.tv_team_name_one);
        tv_team_name_two = rootView.findViewById(R.id.tv_team_name_two);
        tv_home_score_one = rootView.findViewById(R.id.tv_home_score_one);
        tv_home_score_two = rootView.findViewById(R.id.tv_home_score_two);
        tv_home_score_three = rootView.findViewById(R.id.tv_home_score_three);
        tv_home_score_four = rootView.findViewById(R.id.tv_home_score_four);
        tv_home_score_total = rootView.findViewById(R.id.tv_home_score_total);
        tv_away_score_one = rootView.findViewById(R.id.tv_away_score_one);
        tv_away_score_two = rootView.findViewById(R.id.tv_away_score_two);
        tv_away_score_three = rootView.findViewById(R.id.tv_away_score_three);
        tv_away_score_four = rootView.findViewById(R.id.tv_away_score_four);
        tv_away_score_total = rootView.findViewById(R.id.tv_away_score_total);
        progressbar_one = rootView.findViewById(R.id.circleProgressbar_one);
        tv_home_team_shot = rootView.findViewById(R.id.tv_home_team_shot);
        tv_away_team_shot = rootView.findViewById(R.id.tv_away_team_shot);
        progressbar_two = rootView.findViewById(R.id.circleProgressbar_two);
        tv_home_team_three_point = rootView.findViewById(R.id.tv_home_team_three_point);
        tv_away_team_three_point = rootView.findViewById(R.id.tv_away_team_three_point);
        progressbar_three = rootView.findViewById(R.id.circleProgressbar_three);
        tv_home_team_free_throw = rootView.findViewById(R.id.tv_home_team_free_throw);
        tv_away_team_free_throw = rootView.findViewById(R.id.tv_away_team_free_throw);
        tv_event_live = rootView.findViewById(R.id.tv_event_live);
        tv_statistics = rootView.findViewById(R.id.tv_statistics);
        rv_event_live = rootView.findViewById(R.id.rv_event_live);
        cl_statistic = rootView.findViewById(R.id.cl_statistic);
        iv_statistic_logo_one = rootView.findViewById(R.id.iv_statistic_logo_one);
        iv_statistic_logo_two = rootView.findViewById(R.id.iv_statistic_logo_two);
        tv_statistic_name_one = rootView.findViewById(R.id.tv_statistic_name_one);
        tv_statistic_name_two = rootView.findViewById(R.id.tv_statistic_name_two);
        rv_statistic = rootView.findViewById(R.id.rv_statistic);

        tv_event_live.setOnClickListener(this);
        tv_statistics.setOnClickListener(this);

        tv_event_live.setSelected(true);
    }

    @Override
    protected void initData() {
        mLiveAdapter = new BasketballEventLiveAdapter(R.layout.item_basketball_event_live, new ArrayList<>());
        rv_event_live.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_event_live.setAdapter(mLiveAdapter);

        mStatisticAdapter = new BasketballMatchStatisticAdapter(R.layout.item_basketball_match_statistic, new ArrayList<>());
        rv_statistic.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_statistic.setAdapter(mStatisticAdapter);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    public void setData(BasketballDetailBean detailBean) {
        if (detailBean != null) {
            mDetailBean = detailBean;
            if ("1".equals(SpUtil.getInstance().getStringValue(SpUtil.BASKETBALL_LANGUAGE))) {//繁体中文
                if (!TextUtils.isEmpty(detailBean.getHome_team_data().getShort_name_zht())) {
                    tv_team_name_one.setText(detailBean.getHome_team_data().getShort_name_zht());
                }else {
                    tv_team_name_one.setText("");
                }
                if (!TextUtils.isEmpty(detailBean.getAway_team_data().getShort_name_zht())) {
                    tv_team_name_two.setText(detailBean.getAway_team_data().getShort_name_zht());
                }else {
                    tv_team_name_two.setText("");
                }
            }else if ("2".equals(SpUtil.getInstance().getStringValue(SpUtil.BASKETBALL_LANGUAGE))) {//英文
                if (!TextUtils.isEmpty(detailBean.getHome_team_data().getShort_name_en())) {
                    tv_team_name_one.setText(detailBean.getHome_team_data().getShort_name_en());
                }else {
                    tv_team_name_one.setText("");
                }
                if (!TextUtils.isEmpty(detailBean.getAway_team_data().getShort_name_en())) {
                    tv_team_name_two.setText(detailBean.getAway_team_data().getShort_name_en());
                }else {
                    tv_team_name_two.setText("");
                }
            }else {
                if (!TextUtils.isEmpty(detailBean.getHome_team_data().getShort_name_zh())) {
                    tv_team_name_one.setText(detailBean.getHome_team_data().getShort_name_zh());
                }else {
                    tv_team_name_one.setText("");
                }
                if (!TextUtils.isEmpty(detailBean.getAway_team_data().getShort_name_zh())) {
                    tv_team_name_two.setText(detailBean.getAway_team_data().getShort_name_zh());
                }else {
                    tv_team_name_two.setText("");
                }
            }
            tv_home_score_one.setText(String.valueOf(detailBean.getHome_scores().get(0)));
            tv_home_score_two.setText(String.valueOf(detailBean.getHome_scores().get(1)));
            tv_home_score_three.setText(String.valueOf(detailBean.getHome_scores().get(2)));
            tv_home_score_four.setText(String.valueOf(detailBean.getHome_scores().get(3)));
            tv_home_score_total.setText(detailBean.getHome_scores_total());
            tv_away_score_one.setText(String.valueOf(detailBean.getAway_scores().get(0)));
            tv_away_score_two.setText(String.valueOf(detailBean.getAway_scores().get(1)));
            tv_away_score_three.setText(String.valueOf(detailBean.getAway_scores().get(2)));
            tv_away_score_four.setText(String.valueOf(detailBean.getAway_scores().get(3)));
            tv_away_score_total.setText(detailBean.getAway_scores_total());
            if (detailBean.getPlayers() != null && detailBean.getPlayers().getHome() != null && detailBean.getPlayers().getAway() != null) {
                //投篮
//                int homeShot = Integer.valueOf(detailBean.getPlayers().getHome().getShots());
//                int awayShot = Integer.valueOf(detailBean.getPlayers().getAway().getShots());
//                progressbar_one.setMax(homeShot + awayShot);
                int shotOne = new BigDecimal(detailBean.getPlayers().getHome().getHits())
                        .divide(new BigDecimal(detailBean.getPlayers().getHome().getShots()), 2, RoundingMode.HALF_UP)
                        .multiply(new BigDecimal(100)).intValue();
                int shotTwo = new BigDecimal(detailBean.getPlayers().getAway().getHits())
                        .divide(new BigDecimal(detailBean.getPlayers().getAway().getShots()), 2, RoundingMode.HALF_UP)
                        .multiply(new BigDecimal(100)).intValue();
                progressbar_one.setMax(shotOne + shotTwo);
                if (shotOne >= shotTwo) {
                    double awayPercent = new BigDecimal(shotTwo).divide(new BigDecimal(shotOne+shotTwo), 2, RoundingMode.HALF_UP).doubleValue();
                    int awayDegree = new BigDecimal(360).multiply(new BigDecimal(awayPercent)).add(new BigDecimal(-90)).intValue();
                    progressbar_one.setProgressStartColor(getResources().getColor(R.color.c_687AE0));
                    progressbar_one.setProgressEndColor(getResources().getColor(R.color.c_687AE0));
                    progressbar_one.setProgress(shotOne);
                    progressbar_one.setStartDegree(awayDegree);
                }else {
                    progressbar_one.setProgressStartColor(getResources().getColor(R.color.c_F84A4B));
                    progressbar_one.setProgressEndColor(getResources().getColor(R.color.c_F84A4B));
                    progressbar_one.setProgress(shotTwo);
                }
                tv_home_team_shot.setText(String.valueOf(shotOne));
                tv_away_team_shot.setText(String.valueOf(shotTwo));
                //三分
                int homeThreePoint = Integer.valueOf(detailBean.getPlayers().getHome().getThree_pointer_shot());
                int awayThreePoint = Integer.valueOf(detailBean.getPlayers().getAway().getThree_pointer_shot());
                progressbar_two.setMax(homeThreePoint + awayThreePoint);
                if (homeThreePoint >= awayThreePoint) {
                    double awayPercent = new BigDecimal(awayThreePoint).divide(new BigDecimal(homeThreePoint +awayThreePoint), 2, RoundingMode.HALF_UP).doubleValue();
                    int awayDegree = new BigDecimal(360).multiply(new BigDecimal(awayPercent)).add(new BigDecimal(-90)).intValue();
                    progressbar_two.setProgressStartColor(getResources().getColor(R.color.c_687AE0));
                    progressbar_two.setProgressEndColor(getResources().getColor(R.color.c_687AE0));
                    progressbar_two.setProgress(homeThreePoint);
                    progressbar_two.setStartDegree(awayDegree);
                }else {
                    progressbar_two.setProgressStartColor(getResources().getColor(R.color.c_F84A4B));
                    progressbar_two.setProgressEndColor(getResources().getColor(R.color.c_F84A4B));
                    progressbar_two.setProgress(awayThreePoint);
                }
                int threePointOne = new BigDecimal(detailBean.getPlayers().getHome().getThree_pointer())
                        .divide(new BigDecimal(detailBean.getPlayers().getHome().getThree_pointer_shot()), 2, RoundingMode.HALF_UP)
                        .multiply(new BigDecimal(100)).intValue();
                int threePointTwo = new BigDecimal(detailBean.getPlayers().getAway().getThree_pointer())
                        .divide(new BigDecimal(detailBean.getPlayers().getAway().getThree_pointer_shot()), 2, RoundingMode.HALF_UP)
                        .multiply(new BigDecimal(100)).intValue();
                tv_home_team_three_point.setText(String.valueOf(threePointOne));
                tv_away_team_three_point.setText(String.valueOf(threePointTwo));
                //罚球
                int homeFreeThrow = Integer.valueOf(detailBean.getPlayers().getHome().getFree_throw_shots());
                int awayFreeThrow = Integer.valueOf(detailBean.getPlayers().getAway().getFree_throw_shots());
                progressbar_three.setMax(homeFreeThrow + awayFreeThrow);
                if (homeFreeThrow >= awayFreeThrow) {
                    double awayPercent = new BigDecimal(awayFreeThrow).divide(new BigDecimal(homeFreeThrow +awayFreeThrow), 2, RoundingMode.HALF_UP).doubleValue();
                    int awayDegree = new BigDecimal(360).multiply(new BigDecimal(awayPercent)).add(new BigDecimal(-90)).intValue();
                    progressbar_three.setProgressStartColor(getResources().getColor(R.color.c_687AE0));
                    progressbar_three.setProgressEndColor(getResources().getColor(R.color.c_687AE0));
                    progressbar_three.setProgress(homeFreeThrow);
                    progressbar_three.setStartDegree(awayDegree);
                }else {
                    progressbar_three.setProgressStartColor(getResources().getColor(R.color.c_F84A4B));
                    progressbar_three.setProgressEndColor(getResources().getColor(R.color.c_F84A4B));
                    progressbar_three.setProgress(awayFreeThrow);
                }
                int freeThrowOne = new BigDecimal(detailBean.getPlayers().getHome().getFree_throw())
                        .divide(new BigDecimal(detailBean.getPlayers().getHome().getFree_throw_shots()), 2, RoundingMode.HALF_UP)
                        .multiply(new BigDecimal(100)).intValue();
                int freeThrowTwo = new BigDecimal(detailBean.getPlayers().getAway().getFree_throw())
                        .divide(new BigDecimal(detailBean.getPlayers().getAway().getFree_throw_shots()), 2, RoundingMode.HALF_UP)
                        .multiply(new BigDecimal(100)).intValue();
                tv_home_team_free_throw.setText(String.valueOf(freeThrowOne));
                tv_away_team_free_throw.setText(String.valueOf(freeThrowTwo));
            }
            //事件直播
            if (detailBean.getTlive() != null) {
                mLiveAdapter.setNewData(detailBean.getTlive());
            }
            //技术统计
            GlideUtil.loadTeamImageDefault(getContext(), detailBean.getHome_team_data().getLogo(), iv_statistic_logo_one);
            GlideUtil.loadTeamImageDefault(getContext(), detailBean.getAway_team_data().getLogo(), iv_statistic_logo_two);
            if (!TextUtils.isEmpty(detailBean.getHome_team_data().getShort_name_zh())) {
                tv_statistic_name_one.setText(detailBean.getHome_team_data().getShort_name_zh());
            }else {
                tv_statistic_name_one.setText("");
            }
            if (!TextUtils.isEmpty(detailBean.getAway_team_data().getShort_name_zh())) {
                tv_statistic_name_two.setText(detailBean.getAway_team_data().getShort_name_zh());
            }else {
                tv_statistic_name_two.setText("");
            }
            if (detailBean.getPlayers() != null) {
                List<StatisticBean> list = new ArrayList<>();
                StatisticBean one = new StatisticBean();
                one.setName("投篮");
                one.setHomeData(detailBean.getPlayers().getHome().getShots());
                one.setAwayData(detailBean.getPlayers().getAway().getShots());
                list.add(one);
                StatisticBean two = new StatisticBean();
                two.setName("投篮命中");
                two.setHomeData(detailBean.getPlayers().getHome().getHits());
                two.setAwayData(detailBean.getPlayers().getAway().getHits());
                list.add(two);
                StatisticBean three = new StatisticBean();
                three.setName("三分");
                three.setHomeData(detailBean.getPlayers().getHome().getThree_pointer_shot());
                three.setAwayData(detailBean.getPlayers().getAway().getThree_pointer_shot());
                list.add(three);
                StatisticBean four = new StatisticBean();
                four.setName("三分命中");
                four.setHomeData(detailBean.getPlayers().getHome().getThree_pointer());
                four.setAwayData(detailBean.getPlayers().getAway().getThree_pointer());
                list.add(four);
                StatisticBean five = new StatisticBean();
                five.setName("罚球");
                five.setHomeData(detailBean.getPlayers().getHome().getFree_throw_shots());
                five.setAwayData(detailBean.getPlayers().getAway().getFree_throw_shots());
                list.add(five);
                StatisticBean six = new StatisticBean();
                six.setName("罚球命中");
                six.setHomeData(detailBean.getPlayers().getHome().getFree_throw());
                six.setAwayData(detailBean.getPlayers().getAway().getFree_throw());
                list.add(six);
                StatisticBean seven = new StatisticBean();
                seven.setName("篮板");
                seven.setHomeData(detailBean.getPlayers().getHome().getTotal_rebounds());
                seven.setAwayData(detailBean.getPlayers().getAway().getTotal_rebounds());
                list.add(seven);
                mStatisticAdapter.setNewData(list);
            }
        }
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
            case R.id.tv_event_live:
                if (tv_event_live.isSelected()) {
                    return;
                }
                tv_event_live.setSelected(true);
                tv_statistics.setSelected(false);
                rv_event_live.setVisibility(View.VISIBLE);
                cl_statistic.setVisibility(View.GONE);
                break;
            case R.id.tv_statistics:
                if (tv_statistics.isSelected()) {
                    return;
                }
                tv_event_live.setSelected(false);
                tv_statistics.setSelected(true);
                rv_event_live.setVisibility(View.GONE);
                cl_statistic.setVisibility(View.VISIBLE);
                break;
        }
    }
}
