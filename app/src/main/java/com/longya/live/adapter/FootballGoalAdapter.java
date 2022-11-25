package com.longya.live.adapter;

import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.model.FootballMatchBean;
import com.longya.live.model.LiveBean;
import com.longya.live.model.MatchSocketBean;
import com.longya.live.util.GlideUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class FootballGoalAdapter extends BaseQuickAdapter<MatchSocketBean, BaseViewHolder> {
    public FootballGoalAdapter(int layoutResId, @Nullable List<MatchSocketBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MatchSocketBean item) {
        if (!TextUtils.isEmpty(item.getHome_team_name())) {
            helper.setText(R.id.tv_home_team_name, item.getHome_team_name());
        }else {
            helper.setText(R.id.tv_home_team_name, "");
        }
        if (!TextUtils.isEmpty(item.getAway_team_name())) {
            helper.setText(R.id.tv_away_team_name, item.getAway_team_name());
        }else {
            helper.setText(R.id.tv_away_team_name, "");
        }
        helper.setText(R.id.tv_home_team_score, String.valueOf(item.getHome_score()));
        helper.setText(R.id.tv_away_team_score, String.valueOf(item.getAway_score()));
        int count = item.getHome_score() + item.getAway_score();
        helper.setText(R.id.tv_goal_count, "全场第 " + count + " 粒进球");
        if (!TextUtils.isEmpty(item.getMatch_str())) {
            helper.setText(R.id.tv_time, item.getMatch_str());
        }else {
            helper.setText(R.id.tv_time, "");
        }
        RotateAnimation rotateAnimation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(800);
        rotateAnimation.setRepeatCount(-1);
        ImageView iv_icon = helper.getView(R.id.iv_icon);
        iv_icon.startAnimation(rotateAnimation);
    }
}
