package com.onecric.live.adapter;

import static com.onecric.live.util.DateUtil.getRelativeLocalDate;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.LiveBean;
import com.onecric.live.util.GlideUtil;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class LiveMoreVideoAdapter extends BaseQuickAdapter<LiveBean, BaseViewHolder>{
    //    private SimpleDateFormat sfdate = new SimpleDateFormat("EEE,dd MMM.", Locale.ENGLISH);
    private SimpleDateFormat sfdate2 = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
    private Drawable drawableArrRed, drawableArrTransparent;

    public LiveMoreVideoAdapter(int layoutResId, @Nullable List<LiveBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, LiveBean item) {
        helper.setText(R.id.tv_live_title, TextUtils.isEmpty(item.tournament)?"":item.tournament);

        ImageView iv_live_thumb = helper.getView(R.id.iv_live_thumb);
        GlideUtil.loadUpdatesImageDefault(mContext, item.getThumb(), iv_live_thumb);
        helper.setGone(R.id.tv_state_info,false);
        helper.setGone(R.id.tv_state_time,false);
        helper.setGone(R.id.iv_state_live,false);
        TextView tv_home_score = helper.getView(R.id.tv_home_score);
        TextView tv_away_score = helper.getView(R.id.tv_away_score);
        tv_home_score.setTextColor(mContext.getResources().getColor(R.color.c_111111));
        tv_away_score.setTextColor(mContext.getResources().getColor(R.color.c_111111));
        tv_home_score.setCompoundDrawables(null,null,drawableArrTransparent,null);
        tv_away_score.setCompoundDrawables(null,null,drawableArrTransparent,null);

        if(item.getIslive() == 0){
            //未开始
            helper.setGone(R.id.tv_state_time,true);
            helper.setGone(R.id.tv_state_info,true);
            helper.setText(R.id.tv_state_info,mContext.getString(R.string.watch_live_at));
            try {
                String st = getRelativeLocalDate(sfdate2,item.getStarttime());
                helper.setText(R.id.tv_state_time, Html.fromHtml("<strong>"+st.substring(0,5)+"</strong> <small>"+st.substring(5)+"</small>"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }else{
            //已开始
            helper.setGone(R.id.tv_state_info,false);
            helper.setGone(R.id.tv_state_time,false);
            helper.setGone(R.id.iv_state_live,true);
        }

/*        if (item.status == 2) {//已结束
            helper.setGone(R.id.tv_state_info,true);
            helper.setText(R.id.tv_state_info,mContext.getString(R.string.completed));
            if (item.homeId == item.winId) {//主赢 右边
                tv_home_score.setCompoundDrawables(null,null,drawableArrRed,null);
                tv_away_score.setTextColor(mContext.getResources().getColor(R.color.c_999999));
                helper.setTextColor(R.id.tv_away_score2, mContext.getResources().getColor(R.color.c_999999));
            } else if(item.awayId == item.winId){//客赢
                tv_away_score.setCompoundDrawables(null,null,drawableArrRed,null);
                tv_home_score.setTextColor(mContext.getResources().getColor(R.color.c_999999));
                helper.setTextColor(R.id.tv_home_score2, mContext.getResources().getColor(R.color.c_999999));
            }
        }*/
        ImageView iv_home_logo = helper.getView(R.id.iv_home_logo);
        ImageView iv_away_logo = helper.getView(R.id.iv_away_logo);
        GlideUtil.loadTeamImageDefault(mContext, item.home_logo,iv_home_logo);
        helper.setText(R.id.tv_home_name,TextUtils.isEmpty(item.homeName)?"":item.homeName);
        GlideUtil.loadTeamImageDefault(mContext, item.away_logo, iv_away_logo);
        helper.setText(R.id.tv_away_name,TextUtils.isEmpty(item.homeName)?"":item.awayName);
        helper.setText(R.id.tv_home_score2,"");
        if (!TextUtils.isEmpty(item.homeDisplayScore)) {
            if(item.homeDisplayScore.contains("0/0")){
                tv_home_score.setText("");
                helper.setText(R.id.tv_home_score2,mContext.getString(R.string.yet_to_bat));
            }else {
                String scoreStr = item.homeDisplayScore;
                if (scoreStr.contains(" ")){
                    String[] split = scoreStr.split(" ");
//                    if(split.length>1){
                        helper.setText(R.id.tv_home_score2, split[1]);
//                    }
                    scoreStr = split[0];
                }
                if(scoreStr.contains("&")){
                    SpannableStringBuilder builder = new SpannableStringBuilder(scoreStr);
                    builder.setSpan(new ForegroundColorSpan(Color.parseColor("#99999999")), 0, scoreStr.indexOf("&"), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    helper.setText(R.id.tv_home_score, builder);
                }else{
                    helper.setText(R.id.tv_home_score, scoreStr);
                }
            }
        } else {
            tv_home_score.setText("");
        }
        helper.setText(R.id.tv_away_score2,"");
        if (!TextUtils.isEmpty(item.awayDisplayScore)) {
            if(item.awayDisplayScore.contains("0/0")){
                tv_away_score.setText("");
                helper.setText(R.id.tv_away_score2,mContext.getString(R.string.yet_to_bat));

            }else {
                String scoreStr = item.awayDisplayScore;
                if (scoreStr.contains(" ")){
                    String[] split = scoreStr.split(" ");
//                    if(split.length>1){
                        helper.setText(R.id.tv_away_score2, split[1]);
//                    }
                    scoreStr = split[0];
                }
                if(scoreStr.contains("&")){
                    SpannableStringBuilder builder = new SpannableStringBuilder(scoreStr);
                    builder.setSpan(new ForegroundColorSpan(Color.parseColor("#99999999")), 0, scoreStr.indexOf("&"), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    helper.setText(R.id.tv_away_score, builder);
                }else{
                    helper.setText(R.id.tv_away_score, scoreStr);
                }
            }
        } else {
            tv_away_score.setText("");
        }


//        旧布局
        /*ImageView iv_cover = helper.getView(R.id.iv_cover);
        ImageView iv_avatar = helper.getView(R.id.iv_avatar);
        ImageView iv_live = helper.getView(R.id.iv_live);
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_num = helper.getView(R.id.tv_num);
        TextView tv_time = helper.getView(R.id.tv_time);
        ImageView iv_hot = helper.getView(R.id.iv_hot);

        GlideUtil.loadLiveImageDefault(mContext, item.getThumb(), iv_cover);
        GlideUtil.loadUserImageDefault(mContext, item.getAvatar(), iv_avatar);
        if (!TextUtils.isEmpty(item.getTitle())) {
            tv_title.setText(item.getTitle());
        }else {
            tv_title.setText("");
        }
        if (!TextUtils.isEmpty(item.getUser_nickname())) {
            tv_name.setText(item.getUser_nickname());
        }else {
            tv_name.setText("");
        }
        tv_num.setText(item.getHeat() > 1000 ? String.format("%.1f",(float)item.getHeat()/1000) + "K" :item.getHeat()+"");
        if(item.getIslive() == 0){
            iv_live.setVisibility(View.GONE);
            tv_time.setVisibility(View.VISIBLE);
//            iv_cover.setColorFilter(Color.parseColor("#40000000"));
            tv_time.setText(mContext.getString(R.string.watch_live_at)+" "+getRelativeLocalDate(sfdate2,item.getStarttime()));
            iv_hot.setVisibility(View.GONE);
            tv_num.setVisibility(View.GONE);
        }else{
            iv_cover.setColorFilter(null);
            iv_live.setVisibility(View.VISIBLE);
            tv_time.setVisibility(View.GONE);
            iv_hot.setVisibility(View.VISIBLE);
            tv_num.setVisibility(View.VISIBLE);
        }*/

    }

}
