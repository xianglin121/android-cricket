package com.onecric.live.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.FootballLineupEventBean;
import com.onecric.live.model.FootballLineupPlayerBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class FootballMatchLineupInnerAdapter extends BaseQuickAdapter<FootballLineupPlayerBean, BaseViewHolder> {

    private boolean isHome;//是否是主队

    public FootballMatchLineupInnerAdapter(int layoutResId, @Nullable List<FootballLineupPlayerBean> data, boolean isHome) {
        super(layoutResId, data);
        this.isHome = isHome;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, FootballLineupPlayerBean item) {
        TextView tv_number = helper.getView(R.id.tv_number);
        tv_number.setText(String.valueOf(item.getShirt_number()));
        if (isHome) {
            tv_number.setBackgroundResource(R.mipmap.img_red_ream_uniform);
        }else {
            tv_number.setBackgroundResource(R.mipmap.img_blue_ream_uniform);
        }
        if (!TextUtils.isEmpty(item.getName())) {
            helper.setText(R.id.tv_name, item.getName());
        }else {
            helper.setText(R.id.tv_name, "");
        }
        LinearLayout ll_yc = helper.getView(R.id.ll_home_yc);
        ImageView iv_yc = helper.getView(R.id.iv_yc);
        TextView tv_yc_count = helper.getView(R.id.tv_home_yc_count);
        ImageView iv_rc = helper.getView(R.id.iv_home_rc);
        ImageView iv_state = helper.getView(R.id.iv_state);
        TextView tv_time = helper.getView(R.id.tv_time);
        ImageView iv_goal = helper.getView(R.id.iv_goal);
        TextView tv_goal_count = helper.getView(R.id.tv_home_goal_count);
        LinearLayout ll_own_goal = helper.getView(R.id.ll_home_own_goal);
        ImageView iv_own_goal = helper.getView(R.id.iv_own_goal);
        TextView tv_own_goal_count = helper.getView(R.id.tv_home_own_goal_count);
        int ycCount = 0;
        int rcCount = 0;
        int downCount = 0;
        String time = "";
        int goalCount = 0;
        int ownGoalCount = 0;
        for (int i = 0; i < item.getIncidents().size(); i++) {
            FootballLineupEventBean bean = item.getIncidents().get(i);
            if (bean.getType() == 1) {//进球
                goalCount++;
            }else if (bean.getType() == 3) {//黄牌
                ycCount++;
            }else if (bean.getType() == 4) {//红牌
                rcCount++;
            }else if (bean.getType() == 101) {//换下场
                downCount++;
                time = bean.getMinute()+"'";
            }else if (bean.getType() == 17) {//乌龙球
                ownGoalCount++;
            }
        }
        if (ycCount > 0) {
            ll_yc.setVisibility(View.VISIBLE);
            if (ycCount > 1) {
                tv_yc_count.setVisibility(View.VISIBLE);
                tv_yc_count.setText("x" + ycCount);
            }else {
                tv_yc_count.setVisibility(View.GONE);
            }
        }else {
            ll_yc.setVisibility(View.GONE);
        }
        if (rcCount > 0) {
            iv_rc.setVisibility(View.VISIBLE);
        }else {
            iv_rc.setVisibility(View.GONE);
        }
        if (downCount > 0) {
            iv_state.setVisibility(View.VISIBLE);
            tv_time.setVisibility(View.VISIBLE);
            tv_time.setText(time);
        }else {
            iv_state.setVisibility(View.GONE);
            tv_time.setVisibility(View.GONE);
        }
        if (goalCount > 0) {
            iv_goal.setVisibility(View.VISIBLE);
            if (goalCount > 1) {
                tv_goal_count.setVisibility(View.VISIBLE);
                tv_goal_count.setText("x" + goalCount);
            }else {
                tv_goal_count.setVisibility(View.GONE);
            }
        }else {
            iv_goal.setVisibility(View.GONE);
            tv_goal_count.setVisibility(View.GONE);
        }
        if (ownGoalCount > 0) {
            ll_own_goal.setVisibility(View.VISIBLE);
            iv_own_goal.setVisibility(View.VISIBLE);
            if (ownGoalCount > 1) {
                tv_own_goal_count.setVisibility(View.VISIBLE);
                tv_own_goal_count.setText("x" + ownGoalCount);
            }else {
                tv_own_goal_count.setVisibility(View.GONE);
            }
        }else {
            ll_own_goal.setVisibility(View.GONE);
            iv_own_goal.setVisibility(View.GONE);
            tv_own_goal_count.setVisibility(View.GONE);
        }
    }
}
