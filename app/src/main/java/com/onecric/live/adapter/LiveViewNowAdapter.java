package com.onecric.live.adapter;

import static com.onecric.live.util.TimeUtil.stampToTime;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.text.Html;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.AppManager;
import com.onecric.live.R;
import com.onecric.live.model.PlayCardsBean;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.util.TimeUtil;
import com.tencent.qcloud.tuicore.util.DateTimeUtil;

import java.util.Date;
import java.util.List;

public class LiveViewNowAdapter extends BaseMultiItemQuickAdapter<PlayCardsBean, BaseViewHolder> {
    private Context mContext;
    private Drawable drawableArrRed, drawableArrTransparent;
    public LiveViewNowAdapter(Context context, List<PlayCardsBean> data) {
        super(data);
        mContext = context;
        drawableArrRed = AppManager.mContext.getDrawable(R.mipmap.icon_arrow_left_two);
        drawableArrRed.setBounds(0,0,drawableArrRed.getMinimumWidth(),drawableArrRed.getMinimumHeight());
        drawableArrTransparent = AppManager.mContext.getDrawable(R.mipmap.img_transparent1624);
        drawableArrTransparent.setBounds(0,0,drawableArrTransparent.getMinimumWidth(),drawableArrTransparent.getMinimumHeight());

        addItemType(PlayCardsBean.LIVE_ITEM_TYPE_HEAD, R.layout.item_live_now_first);
        addItemType(PlayCardsBean.LIVE_ITEM_TYPE_NARRATE, R.layout.item_live_now);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, PlayCardsBean item) {
        helper.setText(R.id.tv_live_title,TextUtils.isEmpty(item.tournament)?"":item.tournament);

        ImageView iv_live_thumb = helper.getView(R.id.iv_live_thumb);
        GlideUtil.loadUpdatesImageDefault(mContext, item.thumb, iv_live_thumb);
        helper.setGone(R.id.tv_state_info,false);
        helper.setGone(R.id.tv_state_time,false);
        helper.setGone(R.id.iv_state_live,false);
        TextView tv_home_score = helper.getView(R.id.tv_home_score);
        TextView tv_away_score = helper.getView(R.id.tv_away_score);
        tv_home_score.setTextColor(mContext.getResources().getColor(R.color.c_111111));
        tv_away_score.setTextColor(mContext.getResources().getColor(R.color.c_111111));
        tv_home_score.setCompoundDrawables(null,null,drawableArrTransparent,null);
        tv_away_score.setCompoundDrawables(null,null,drawableArrTransparent,null);

        if (item.status == 2) {//已结束
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
        } else if (item.status == 0) {//未开始
            if(TextUtils.isEmpty(item.scheduled)){
                return;
            }
            helper.setGone(R.id.tv_state_time,true);
            helper.setGone(R.id.tv_state_info,true);
            //转时间戳 得到倒计时毫秒数
            long time = DateTimeUtil.getStringToDate(item.scheduled, "yyyy-MM-dd HH:mm:ss");
            long countTime = time - new Date().getTime();
            if (countTime > 0) {
                //开始倒计时
                new CountDownTimer(countTime, 1000) {
                    public void onTick(long millisUntilFinished) {
                        helper.setText(R.id.tv_state_info,mContext.getString(R.string.watch_live_at));
                        helper.setText(R.id.tv_state_time, Html.fromHtml("<strong>" + TimeUtil.timeConversion(millisUntilFinished / 1000) + "</strong>"));
                    }

                    public void onFinish() {
                        item.status = 1;
                        helper.setGone(R.id.tv_state_info,false);
                        helper.setGone(R.id.tv_state_time,false);
                        helper.setGone(R.id.iv_state_live,true);
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
        } else {//已开始
            helper.setGone(R.id.iv_state_live,true);
        }
        ImageView iv_home_logo = helper.getView(R.id.iv_home_logo);
        ImageView iv_away_logo = helper.getView(R.id.iv_away_logo);
        GlideUtil.loadTeamImageDefault(mContext, item.homeLogo,iv_home_logo);
        helper.setText(R.id.tv_home_name,TextUtils.isEmpty(item.homeName)?"":item.homeName);
        GlideUtil.loadTeamImageDefault(mContext, item.awayLogo, iv_away_logo);
        helper.setText(R.id.tv_away_name,TextUtils.isEmpty(item.awayName)?"":item.awayName);

        if (!TextUtils.isEmpty(item.homeDisplayScore)) {
            if(item.homeDisplayScore.contains("0/0")){
                tv_home_score.setText("");
                helper.setText(R.id.tv_home_score2,mContext.getString(R.string.yet_to_bat));
            }else if (item.homeDisplayScore.contains(" ")) {
                String[] split = item.homeDisplayScore.split(" ");
                tv_home_score.setText(" "+split[0]);
                helper.setText(R.id.tv_home_score2,split[1]);
            } else {
                tv_home_score.setText(item.homeDisplayScore);
            }
        } else {
            tv_home_score.setText("");
            helper.setText(R.id.tv_home_score2,"");
        }

        if (!TextUtils.isEmpty(item.awayDisplayScore)) {
            if(item.awayDisplayScore.contains("0/0")){
                tv_away_score.setText("");
                helper.setText(R.id.tv_away_score2,mContext.getString(R.string.yet_to_bat));

            }else if (item.awayDisplayScore.contains(" ")) {
                String[] split = item.awayDisplayScore.split(" ");
                tv_away_score.setText(" "+split[0]);
                helper.setText(R.id.tv_away_score2,split[1]);
            } else {
                tv_away_score.setText(item.awayDisplayScore);
            }
        } else {
            tv_away_score.setText("");
            helper.setText(R.id.tv_away_score2,"");
        }

    }

}
