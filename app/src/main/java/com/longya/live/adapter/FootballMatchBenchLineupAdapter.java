package com.longya.live.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.model.FootballLineupEventBean;
import com.longya.live.model.FootballLineupSubstituteBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class FootballMatchBenchLineupAdapter extends BaseQuickAdapter<FootballLineupSubstituteBean, BaseViewHolder> {
    public FootballMatchBenchLineupAdapter(int layoutResId, @Nullable List<FootballLineupSubstituteBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, FootballLineupSubstituteBean item) {
        ConstraintLayout rootView = helper.getView(R.id.rootView);
        if (helper.getLayoutPosition()%2 == 0) {
            rootView.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
        }else {
            rootView.setBackgroundColor(mContext.getResources().getColor(R.color.c_FFFBF6));
        }
        TextView tv_number_one = helper.getView(R.id.tv_number_one);
        TextView tv_name_one = helper.getView(R.id.tv_name_one);
        TextView tv_position_one = helper.getView(R.id.tv_position_one);
        TextView tv_name_two = helper.getView(R.id.tv_name_two);
        TextView tv_number_two = helper.getView(R.id.tv_number_two);
        TextView tv_position_two = helper.getView(R.id.tv_position_two);
        if (item.getHome_player() != null) {
            tv_number_one.setVisibility(View.VISIBLE);
            tv_number_one.setText(String.valueOf(item.getHome_player().getShirt_number()));
            if (!TextUtils.isEmpty(item.getHome_player().getName())) {
                tv_name_one.setText(item.getHome_player().getName());
            }
            if ("F".equals(item.getHome_player().getPosition())) {
                tv_position_one.setText(mContext.getString(R.string.position_forward));
            }else if ("M".equals(item.getHome_player().getPosition())) {
                tv_position_one.setText(mContext.getString(R.string.position_midfield));
            }else if ("D".equals(item.getHome_player().getPosition())) {
                tv_position_one.setText(mContext.getString(R.string.position_guard));
            }else if ("G".equals(item.getHome_player().getPosition())) {
                tv_position_one.setText(mContext.getString(R.string.position_goalkeeper));
            }else {
                tv_position_one.setText(mContext.getString(R.string.position_unknown));
            }

            ImageView iv_up_state_one = helper.getView(R.id.iv_up_state_one);
            TextView tv_up_time_one = helper.getView(R.id.tv_up_time_one);
            ImageView iv_down_state_one = helper.getView(R.id.iv_down_state_one);
            TextView tv_down_time_one = helper.getView(R.id.tv_down_time_one);
            LinearLayout ll_goal = helper.getView(R.id.ll_home_goal);
            TextView tv_goal_count = helper.getView(R.id.tv_home_goal_count);
            LinearLayout ll_own_goal = helper.getView(R.id.ll_home_own_goal);
            TextView tv_own_goal_count = helper.getView(R.id.tv_home_own_goal_count);
            LinearLayout ll_yc = helper.getView(R.id.ll_home_yc);
            TextView tv_yc_count = helper.getView(R.id.tv_home_yc_count);
            ImageView iv_rc = helper.getView(R.id.iv_home_rc);
            if (item.getHome_player().getIncidents() != null && item.getHome_player().getIncidents().size() > 0) {
                int ycCount = 0;
                int rcCount = 0;
                int upCount = 0;
                int downCount = 0;
                String upTime = "";
                String downTime = "";
                int goalCount = 0;
                int ownGoalCount = 0;
                for (int i = 0; i < item.getHome_player().getIncidents().size(); i++) {
                    FootballLineupEventBean bean = item.getHome_player().getIncidents().get(i);
                    if (bean.getType() == 1) {//进球
                        goalCount++;
                    }else if (bean.getType() == 3) {//黄牌
                        ycCount++;
                    }else if (bean.getType() == 4) {//红牌
                        rcCount++;
                    }else if (bean.getType() == 100) {//换上场
                        upCount++;
                        upTime = bean.getMinute()+"'";
                    }else if (bean.getType() == 101) {//换下场
                        downCount++;
                        downTime = bean.getMinute()+"'";
                    }else if (bean.getType() == 17) {//乌龙球
                        ownGoalCount++;
                    }
                }
                if (upCount > 0) {
                    iv_up_state_one.setVisibility(View.VISIBLE);
                    tv_up_time_one.setVisibility(View.VISIBLE);
                    tv_up_time_one.setText(upTime);
                }else {
                    iv_up_state_one.setVisibility(View.GONE);
                    tv_up_time_one.setVisibility(View.GONE);
                }
                if (downCount > 0) {
                    iv_down_state_one.setVisibility(View.VISIBLE);
                    tv_down_time_one.setVisibility(View.VISIBLE);
                    tv_down_time_one.setText(downTime);
                }else {
                    iv_down_state_one.setVisibility(View.GONE);
                    tv_down_time_one.setVisibility(View.GONE);
                }
                if (goalCount > 0) {
                    ll_goal.setVisibility(View.VISIBLE);
                    if (goalCount > 1) {
                        tv_goal_count.setText("x" + goalCount);
                    }else {
                        tv_goal_count.setText("");
                    }
                }else {
                    ll_goal.setVisibility(View.GONE);
                }
                if (ownGoalCount > 0) {
                    ll_own_goal.setVisibility(View.VISIBLE);
                    if (ownGoalCount > 1) {
                        tv_own_goal_count.setText("x" + ownGoalCount);
                    }else {
                        tv_own_goal_count.setText("");
                    }
                }else {
                    ll_own_goal.setVisibility(View.GONE);
                }
                if (ycCount > 0) {
                    ll_yc.setVisibility(View.VISIBLE);
                    if (ycCount > 1) {
                        tv_yc_count.setText("x" + ycCount);
                    }else {
                        tv_yc_count.setText("");
                    }
                }else {
                    ll_yc.setVisibility(View.GONE);
                }
                if (rcCount > 0) {
                    iv_rc.setVisibility(View.VISIBLE);
                }else {
                    iv_rc.setVisibility(View.GONE);
                }
            }
        }else {
            tv_number_one.setVisibility(View.GONE);
        }
        if (item.getAway_player() != null) {
            tv_number_two.setVisibility(View.VISIBLE);
            tv_number_two.setText(String.valueOf(item.getAway_player().getShirt_number()));
            if (!TextUtils.isEmpty(item.getAway_player().getName())) {
                tv_name_two.setText(item.getAway_player().getName());
            }
            if ("F".equals(item.getAway_player().getPosition())) {
                tv_position_two.setText(mContext.getString(R.string.position_forward));
            }else if ("M".equals(item.getAway_player().getPosition())) {
                tv_position_two.setText(mContext.getString(R.string.position_midfield));
            }else if ("D".equals(item.getAway_player().getPosition())) {
                tv_position_two.setText(mContext.getString(R.string.position_guard));
            }else if ("G".equals(item.getAway_player().getPosition())) {
                tv_position_two.setText(mContext.getString(R.string.position_goalkeeper));
            }else {
                tv_position_two.setText(mContext.getString(R.string.position_unknown));
            }

            ImageView iv_up_state_two = helper.getView(R.id.iv_up_state_two);
            TextView tv_up_time_two = helper.getView(R.id.tv_up_time_two);
            ImageView iv_down_state_two = helper.getView(R.id.iv_down_state_two);
            TextView tv_down_time_two = helper.getView(R.id.tv_down_time_two);
            LinearLayout ll_goal = helper.getView(R.id.ll_away_goal);
            TextView tv_goal_count = helper.getView(R.id.tv_away_goal_count);
            LinearLayout ll_own_goal = helper.getView(R.id.ll_away_own_goal);
            TextView tv_own_goal_count = helper.getView(R.id.tv_away_own_goal_count);
            LinearLayout ll_yc = helper.getView(R.id.ll_away_yc);
            TextView tv_yc_count = helper.getView(R.id.tv_away_yc_count);
            ImageView iv_rc = helper.getView(R.id.iv_away_rc);
            if (item.getAway_player().getIncidents() != null && item.getAway_player().getIncidents().size() > 0) {
                int ycCount = 0;
                int rcCount = 0;
                int upCount = 0;
                int downCount = 0;
                String upTime = "";
                String downTime = "";
                int goalCount = 0;
                int ownGoalCount = 0;
                for (int i = 0; i < item.getAway_player().getIncidents().size(); i++) {
                    FootballLineupEventBean bean = item.getAway_player().getIncidents().get(i);
                    if (bean.getType() == 1) {//进球
                        goalCount++;
                    }else if (bean.getType() == 3) {//黄牌
                        ycCount++;
                    }else if (bean.getType() == 4) {//红牌
                        rcCount++;
                    }else if (bean.getType() == 100) {//换上场
                        upCount++;
                        upTime = bean.getMinute()+"'";
                    }else if (bean.getType() == 101) {//换下场
                        downCount++;
                        downTime = bean.getMinute()+"'";
                    }else if (bean.getType() == 17) {//乌龙球
                        ownGoalCount++;
                    }
                }
                if (upCount > 0) {
                    iv_up_state_two.setVisibility(View.VISIBLE);
                    tv_up_time_two.setVisibility(View.VISIBLE);
                    tv_up_time_two.setText(upTime);
                }else {
                    iv_up_state_two.setVisibility(View.GONE);
                    tv_up_time_two.setVisibility(View.GONE);
                }
                if (downCount > 0) {
                    iv_down_state_two.setVisibility(View.VISIBLE);
                    tv_down_time_two.setVisibility(View.VISIBLE);
                    tv_down_time_two.setText(downTime);
                }else {
                    iv_down_state_two.setVisibility(View.GONE);
                    tv_down_time_two.setVisibility(View.GONE);
                }
                if (goalCount > 0) {
                    ll_goal.setVisibility(View.VISIBLE);
                    if (goalCount > 1) {
                        tv_goal_count.setText("x" + goalCount);
                    }else {
                        tv_goal_count.setText("");
                    }
                }else {
                    ll_goal.setVisibility(View.GONE);
                }
                if (ownGoalCount > 0) {
                    ll_own_goal.setVisibility(View.VISIBLE);
                    if (ownGoalCount > 1) {
                        tv_own_goal_count.setText("x" + ownGoalCount);
                    }else {
                        tv_own_goal_count.setText("");
                    }
                }else {
                    ll_own_goal.setVisibility(View.GONE);
                }
                if (ycCount > 0) {
                    ll_yc.setVisibility(View.VISIBLE);
                    if (ycCount > 1) {
                        tv_yc_count.setText("x" + ycCount);
                    }else {
                        tv_yc_count.setText("");
                    }
                }else {
                    ll_yc.setVisibility(View.GONE);
                }
                if (rcCount > 0) {
                    iv_rc.setVisibility(View.VISIBLE);
                }else {
                    iv_rc.setVisibility(View.GONE);
                }
            }
        }else {
            tv_number_two.setVisibility(View.GONE);
        }
    }
}
