package com.onecric.live.adapter;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.activity.MainActivity;
import com.onecric.live.model.CricketMatchBean;
import com.onecric.live.model.SubscribeTypeBean;
import com.onecric.live.presenter.match.SubscribePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.util.DialogUtil;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.util.TimeUtil;
import com.onecric.live.util.ToastUtil;
import com.tencent.qcloud.tuicore.util.DateTimeUtil;

import java.util.Date;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/27
 */
public class CricketInnerNewAdapter extends BaseQuickAdapter<CricketMatchBean, BaseViewHolder> {
    MainActivity mainActivity;

    public CricketInnerNewAdapter(MainActivity mainActivity, int layoutResId, @Nullable List<CricketMatchBean> data) {
        super(layoutResId, data);
        this.mainActivity = mainActivity;
    }


    @Override
    protected void convert(@NonNull BaseViewHolder helper, CricketMatchBean item) {
        if (helper.getLayoutPosition() == getItemCount() - 1) {
            helper.setVisible(R.id.line,false);
        } else {
            helper.setVisible(R.id.line,true);
        }
        helper.getView(R.id.tv_state_time).setVisibility(View.GONE);
        helper.getView(R.id.tv_state_live).setVisibility(View.GONE);

        TextView resultTv = helper.getView(R.id.tv_result);

        helper.setTextColor(R.id.tv_home_score, mContext.getResources().getColor(R.color.c_111111));
        helper.setTextColor(R.id.tv_home_name, mContext.getResources().getColor(R.color.c_111111));
        helper.setTextColor(R.id.tv_away_name, mContext.getResources().getColor(R.color.c_111111));
        helper.setTextColor(R.id.tv_away_score, mContext.getResources().getColor(R.color.c_111111));
        if (item.getStatus() == 2) {//已结束
            helper.setText(R.id.tv_state_info,"Completed");
            resultTv.setTypeface(ResourcesCompat.getFont(mContext, R.font.noto_sans_display_regular));
            if (item.getHome_id() == item.getWinner_id()) {//主赢
                helper.setTextColor(R.id.tv_away_score, mContext.getResources().getColor(R.color.c_999999));
                helper.setTextColor(R.id.tv_away_score2, mContext.getResources().getColor(R.color.c_999999));
            } else {//客赢
                helper.setTextColor(R.id.tv_home_score, mContext.getResources().getColor(R.color.c_999999));
                helper.setTextColor(R.id.tv_home_score2, mContext.getResources().getColor(R.color.c_999999));
            }
        } else {
            resultTv.setTypeface(ResourcesCompat.getFont(mContext, R.font.noto_sans_display_regular));
            if (item.getStatus() == 0) {//未开始
                helper.setText(R.id.tv_state_info,"Watch Live At");
                helper.setText(R.id.tv_state_time,"8:30 PM");
                helper.getView(R.id.tv_state_time).setVisibility(View.VISIBLE);
            } else {//已开始
                //区分赛事直播、主播直播
                helper.setText(R.id.tv_state_live,"ＬＩＶＥ");
                resultTv.setTypeface(ResourcesCompat.getFont(mContext, R.font.noto_sans_display_semibold));
                if (item.getLive_id() != 0) {
                    helper.getView(R.id.tv_live).setVisibility(View.VISIBLE);
                }
            }

        }

        if (!TextUtils.isEmpty(item.getMatch_result())) {
            helper.setText(R.id.tv_result, item.getMatch_result());
        } else {
            helper.setText(R.id.tv_result, "");
        }


        ImageView iv_home_logo = helper.getView(R.id.iv_home_logo);
        GlideUtil.loadTeamImageDefault(mContext, item.getHome_logo(), iv_home_logo);
        if (!TextUtils.isEmpty(item.getHome_name())) {
            helper.setText(R.id.tv_home_name, item.getHome_name());
        } else {
            helper.setText(R.id.tv_home_name, "");
        }

        ImageView iv_away_logo = helper.getView(R.id.iv_away_logo);
        GlideUtil.loadTeamImageDefault(mContext, item.getAway_logo(), iv_away_logo);
        if (!TextUtils.isEmpty(item.getAway_name())) {
            helper.setText(R.id.tv_away_name, item.getAway_name());
        } else {
            helper.setText(R.id.tv_away_name, "");
        }

        if (!TextUtils.isEmpty(item.getHome_display_score())) {
            if(item.getHome_display_score().contains("0/0")){
                helper.setText(R.id.tv_home_score, "");
                helper.setText(R.id.tv_home_score2, "Yet To Bat");
            }else if (item.getHome_display_score().contains(" ")) {
                String[] split = item.getHome_display_score().split(" ");
                helper.setText(R.id.tv_home_score, " "+split[0]);
                helper.setText(R.id.tv_home_score2, split[1]);
            } else {
                helper.setText(R.id.tv_home_score, item.getHome_display_score());
            }
        } else {
            helper.setText(R.id.tv_home_score, "");
            helper.setText(R.id.tv_home_score2, " ");
        }

        if (!TextUtils.isEmpty(item.getAway_display_score())) {
            if(item.getAway_display_score().contains("0/0")){
                helper.setText(R.id.tv_away_score, "");
                helper.setText(R.id.tv_away_score2, "Yet To Bat");
            }else if (item.getAway_display_score().contains(" ")) {
                String[] split = item.getAway_display_score().split(" ");
                helper.setText(R.id.tv_away_score, " "+split[0]);
                helper.setText(R.id.tv_away_score2, split[1]);
            } else {
                helper.setText(R.id.tv_away_score, item.getAway_display_score());
            }
        } else {
            helper.setText(R.id.tv_away_score, "");
            helper.setText(R.id.tv_away_score2, "");
        }





    }


}
