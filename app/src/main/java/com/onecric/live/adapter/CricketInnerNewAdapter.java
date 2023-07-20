package com.onecric.live.adapter;

import static com.onecric.live.util.TimeUtil.stampToTime;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.AppManager;
import com.onecric.live.R;
import com.onecric.live.model.CricketNewBean;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.util.TimeUtil;
import com.tencent.qcloud.tuicore.util.DateTimeUtil;

import java.util.Date;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/27
 */
public class CricketInnerNewAdapter extends BaseQuickAdapter<CricketNewBean.CricketMatchNewBean, BaseViewHolder> {
    private Drawable drawableArrRed, drawableArrTransparent;
    public CricketInnerNewAdapter(int layoutResId, @Nullable List<CricketNewBean.CricketMatchNewBean> data) {
        super(layoutResId, data);
        drawableArrRed = AppManager.mContext.getDrawable(R.mipmap.icon_arrow_left_two);
        drawableArrRed.setBounds(0,0,drawableArrRed.getMinimumWidth(),drawableArrRed.getMinimumHeight());
        drawableArrTransparent = AppManager.mContext.getDrawable(R.mipmap.img_transparent1624);
        drawableArrTransparent.setBounds(0,0,drawableArrTransparent.getMinimumWidth(),drawableArrTransparent.getMinimumHeight());
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CricketNewBean.CricketMatchNewBean item) {
        helper.getView(R.id.tv_state_time).setVisibility(View.GONE);
        helper.getView(R.id.tv_state_live).setVisibility(View.GONE);
        helper.getView(R.id.tv_state_watch_live).setVisibility(View.GONE);
        helper.getView(R.id.tv_state_score).setVisibility(View.GONE);
        TextView tv_home_score = helper.getView(R.id.tv_home_score);
        TextView tv_away_score = helper.getView(R.id.tv_away_score);
        tv_home_score.setTextColor(mContext.getResources().getColor(R.color.c_111111));
        tv_away_score.setTextColor(mContext.getResources().getColor(R.color.c_111111));
        TextView resultTv = helper.getView(R.id.tv_result);
        resultTv.setTypeface(ResourcesCompat.getFont(mContext, R.font.noto_sans_display_semibold));
        tv_home_score.setCompoundDrawables(null,null,drawableArrTransparent,null);
        tv_away_score.setCompoundDrawables(null,null,drawableArrTransparent,null);

        if (item.getStatus() == 2) {//已结束
            helper.setText(R.id.tv_state_info,mContext.getString(R.string.completed));
            if (item.getHomeId() == item.getWinId()) {//主赢 右边
                tv_home_score.setCompoundDrawables(null,null,drawableArrRed,null);
                tv_away_score.setTextColor(mContext.getResources().getColor(R.color.c_999999));
                helper.setTextColor(R.id.tv_away_score2, mContext.getResources().getColor(R.color.c_999999));
            } else if(item.getAwayId() == item.getWinId()){//客赢
                tv_away_score.setCompoundDrawables(null,null,drawableArrRed,null);
                tv_home_score.setTextColor(mContext.getResources().getColor(R.color.c_999999));
                helper.setTextColor(R.id.tv_home_score2, mContext.getResources().getColor(R.color.c_999999));
            }
        } else if (item.getStatus() == 0) {//未开始
            //转时间戳 得到倒计时毫秒数
            long time = DateTimeUtil.getStringToDate(item.getScheduled(), "yyyy-MM-dd HH:mm:ss");
            long countTime = time - new Date().getTime();
            if (item.getFastStatus() == 1 && countTime > 0) {
                //开始倒计时
                new CountDownTimer(countTime, 1000) {
                    public void onTick(long millisUntilFinished) {
                        helper.setText(R.id.tv_state_info,mContext.getString(R.string.watch_live_at));
                        helper.setText(R.id.tv_state_time, Html.fromHtml("<strong>" + TimeUtil.timeConversion(millisUntilFinished / 1000) + "</strong>"));
                    }

                    public void onFinish() {
                        item.setStatus(1);
                        notifyItemChanged(helper.getLayoutPosition());
                    }
                }.start();
            }else{
                helper.setText(R.id.tv_state_info,mContext.getString(R.string.watch_live_at));
                try{
                    String st = stampToTime(time,"hh:mm a");
                    helper.setText(R.id.tv_state_time, Html.fromHtml("<strong>"+st.substring(0,5)+"</strong> <small>"+st.substring(5)+"</small>"));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            helper.getView(R.id.tv_state_time).setVisibility(View.VISIBLE);
        } else {//已开始
            resultTv.setTypeface(ResourcesCompat.getFont(mContext, R.font.noto_sans_display_regular));
            helper.getView(R.id.tv_state_info).setVisibility(View.GONE);
            if(item.getMatchLive() != 1 && !"1".equals(item.getLiveStatus()) ){
                helper.getView(R.id.tv_state_score).setVisibility(View.VISIBLE);
            }else {
                if(item.getMatchLive() == 1){
                    helper.getView(R.id.tv_state_watch_live).setVisibility(View.VISIBLE);
                }
                if ("1".equals(item.getLiveStatus()) && item.getLiveId() != 0) {
                    //主播直播
                    helper.getView(R.id.tv_state_live).setVisibility(View.VISIBLE);
                }
                helper.addOnClickListener(R.id.tv_state_live);
                helper.addOnClickListener(R.id.tv_state_watch_live);
            }

        }

        if (TextUtils.isEmpty(item.getMatchResult()) || "The match is live".equals(item.getMatchResult())) {
            helper.getView(R.id.tv_result).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.tv_result).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_result, item.getMatchResult());
        }

        ImageView iv_home_logo = helper.getView(R.id.iv_home_logo);
        GlideUtil.loadTeamImageDefault(mContext, item.getHomeLogo(), iv_home_logo);
        if (!TextUtils.isEmpty(item.getHomeName())) {
            helper.setText(R.id.tv_home_name, item.getHomeName());
        } else {
            helper.setText(R.id.tv_home_name, "");
        }

        ImageView iv_away_logo = helper.getView(R.id.iv_away_logo);
        GlideUtil.loadTeamImageDefault(mContext, item.getAwayLogo(), iv_away_logo);
        if (!TextUtils.isEmpty(item.getAwayName())) {
            helper.setText(R.id.tv_away_name, item.getAwayName());
        } else {
            helper.setText(R.id.tv_away_name, "");
        }

        helper.setText(R.id.tv_home_score2, " ");
        if (!TextUtils.isEmpty(item.getHomeDisplayScore())) {
            if(item.getHomeDisplayScore().contains("0/0")){
                helper.setText(R.id.tv_home_score, "");
                helper.setText(R.id.tv_home_score2, mContext.getString(R.string.yet_to_bat));
            }else{
                //                String scoreStr = "11/11&22/22 (33)";
                String scoreStr = item.getHomeDisplayScore();
                if (scoreStr.contains(" ")){
                    String[] split = scoreStr.split(" ");
                    helper.setText(R.id.tv_home_score2, split[1]);
                    scoreStr = split[0];
                }
                if(scoreStr.contains("&")){
                    SpannableStringBuilder builder = new SpannableStringBuilder(scoreStr);
                    builder.setSpan(new ForegroundColorSpan(Color.parseColor("#99111111")), 0, scoreStr.indexOf("&"), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    helper.setText(R.id.tv_home_score, builder);
                }else{
                    helper.setText(R.id.tv_home_score, scoreStr);
                }
            }
        } else {
            helper.setText(R.id.tv_home_score, "");
        }

        helper.setText(R.id.tv_away_score2, "");
        if (!TextUtils.isEmpty(item.getAwayDisplayScore())) {
            if(item.getAwayDisplayScore().contains("0/0")){
                helper.setText(R.id.tv_away_score, "");
                helper.setText(R.id.tv_away_score2, mContext.getString(R.string.yet_to_bat));
            }else {
                String scoreStr = item.getAwayDisplayScore();
                if (scoreStr.contains(" ")){
                    String[] split = scoreStr.split(" ");
                    helper.setText(R.id.tv_away_score2, split[1]);
                    scoreStr = split[0];
                }
                if(scoreStr.contains("&")){
                    SpannableStringBuilder builder = new SpannableStringBuilder(scoreStr);
                    builder.setSpan(new ForegroundColorSpan(Color.parseColor("#99111111")), 0, scoreStr.indexOf("&"), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    helper.setText(R.id.tv_away_score, builder);
                }else{
                    helper.setText(R.id.tv_away_score, scoreStr);
                }
            }
        } else {
            helper.setText(R.id.tv_away_score, "");
        }
    }
}
