package com.onecric.live.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.activity.LiveDetailActivity;
import com.onecric.live.activity.LoginActivity;
import com.onecric.live.model.BasketballMatchBean;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.util.SpUtil;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/11
 */
public class BasketballMatchInnerAdapter extends BaseQuickAdapter<BasketballMatchBean, BaseViewHolder> {


    public BasketballMatchInnerAdapter(int layoutResId, @Nullable List<BasketballMatchBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, BasketballMatchBean item) {
//        ImageView iv_label = helper.getView(R.id.iv_label);
//        iv_label.setBackgroundResource(R.mipmap.bg_league_label);
//        iv_label.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
        helper.addOnClickListener(R.id.iv_collect);
        ImageView iv_collect = helper.getView(R.id.iv_collect);
        if (item.getIs_collect() == 1) {
            iv_collect.setSelected(true);
        }else {
            iv_collect.setSelected(false);
        }
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_time = helper.getView(R.id.tv_time);
        TextView tv_round = helper.getView(R.id.tv_round);
        TextView tv_team_name_one = helper.getView(R.id.tv_team_name_one);
        TextView tv_team_name_two = helper.getView(R.id.tv_team_name_two);
//        tv_title.setBackgroundResource(R.mipmap.bg_league_label);
//        tv_title.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
        if ("1".equals(SpUtil.getInstance().getStringValue(SpUtil.BASKETBALL_LANGUAGE))) {//繁体中文
            if (!TextUtils.isEmpty(item.getShort_name_zht())) {
                tv_title.setText(item.getShort_name_zht());
            }else {
                tv_title.setText("");
            }
            if (!TextUtils.isEmpty(item.getHome_team_data().getShort_name_zht())) {
                tv_team_name_one.setText(item.getHome_team_data().getShort_name_zht());
            }else {
                tv_team_name_one.setText("");
            }
            if (!TextUtils.isEmpty(item.getAway_team_data().getShort_name_zht())) {
                tv_team_name_two.setText(item.getAway_team_data().getShort_name_zht());
            }else {
                tv_team_name_two.setText("");
            }
        }else if ("2".equals(SpUtil.getInstance().getStringValue(SpUtil.BASKETBALL_LANGUAGE))) {//英文
            if (!TextUtils.isEmpty(item.getShort_name_en())) {
                tv_title.setText(item.getShort_name_en());
            }else {
                tv_title.setText("");
            }
            if (!TextUtils.isEmpty(item.getHome_team_data().getShort_name_en())) {
                tv_team_name_one.setText(item.getHome_team_data().getShort_name_en());
            }else {
                tv_team_name_one.setText("");
            }
            if (!TextUtils.isEmpty(item.getAway_team_data().getShort_name_en())) {
                tv_team_name_two.setText(item.getAway_team_data().getShort_name_en());
            }else {
                tv_team_name_two.setText("");
            }
        }else {//简体中文
            if (!TextUtils.isEmpty(item.getShort_name_zh())) {
                tv_title.setText(item.getShort_name_zh());
            }else {
                tv_title.setText("");
            }
            if (!TextUtils.isEmpty(item.getHome_team_data().getShort_name_zh())) {
                tv_team_name_one.setText(item.getHome_team_data().getShort_name_zh());
            }else {
                tv_team_name_one.setText("");
            }
            if (!TextUtils.isEmpty(item.getAway_team_data().getShort_name_zh())) {
                tv_team_name_two.setText(item.getAway_team_data().getShort_name_zh());
            }else {
                tv_team_name_two.setText("");
            }
        }
        if (!TextUtils.isEmpty(item.getMatch_time())) {
            tv_time.setText(item.getMatch_time());
        }else {
            tv_time.setText("");
        }
        if (!TextUtils.isEmpty(item.getStatus_name())) {
            if (!TextUtils.isEmpty(item.getTime_left())) {
                tv_round.setText(item.getStatus_name() + item.getTime_left());
            }else {
                tv_round.setText(item.getStatus_name());
            }
        }

        ImageView iv_logo = helper.getView(R.id.iv_logo);
        ImageView iv_anchor = helper.getView(R.id.iv_anchor);
        helper.setText(R.id.tv_home_round_one, String.valueOf(item.getHome_scores().get(0)));
        helper.setText(R.id.tv_home_round_two, String.valueOf(item.getHome_scores().get(1)));
        helper.setText(R.id.tv_home_round_three, String.valueOf(item.getHome_scores().get(2)));
        helper.setText(R.id.tv_home_round_four, String.valueOf(item.getHome_scores().get(3)));
        helper.setText(R.id.tv_home_round_total, String.valueOf(item.getHome_scores_total()));
        helper.setText(R.id.tv_away_round_one, String.valueOf(item.getAway_scores().get(0)));
        helper.setText(R.id.tv_away_round_two, String.valueOf(item.getAway_scores().get(1)));
        helper.setText(R.id.tv_away_round_three, String.valueOf(item.getAway_scores().get(2)));
        helper.setText(R.id.tv_away_round_four, String.valueOf(item.getAway_scores().get(3)));
        helper.setText(R.id.tv_away_round_total, String.valueOf(item.getAway_scores_total()));

        if (item.getAnchor() != null && item.getAnchor().getId() > 0) {
            iv_logo.setVisibility(View.GONE);
            iv_anchor.setVisibility(View.VISIBLE);
            GlideUtil.loadUserImageDefault(mContext, item.getAnchor().getAvatar(), iv_anchor);
            iv_anchor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken()) && SpUtil.getInstance().getBooleanValue(SpUtil.VIDEO_OVERTIME)){
//                        LoginActivity.forward(mContext);
                    }else{
                        LiveDetailActivity.forward(mContext, item.getAnchor().getId(), 2, item.getAnchor().getMatch_id(),true);
                    }
                }
            });
        }else {
            iv_anchor.setVisibility(View.GONE);
            iv_anchor.setOnClickListener(null);
            if (item.getMlive() == 0) {
                iv_logo.setVisibility(View.GONE);
            }else {
                iv_logo.setVisibility(View.VISIBLE);
                iv_logo.setImageResource(R.mipmap.icon_match_animation);

            }
        }
    }
}
