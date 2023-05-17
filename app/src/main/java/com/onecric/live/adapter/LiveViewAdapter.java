package com.onecric.live.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.PlayCardsBean;
import com.onecric.live.util.GlideUtil;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
//item_view_live
public class LiveViewAdapter extends BaseQuickAdapter<PlayCardsBean, BaseViewHolder> {
    private SimpleDateFormat sfdate1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat sfdate2 = new SimpleDateFormat("MM-dd hh:mm:ss", Locale.ENGLISH);
    public LiveViewAdapter(int layoutResId, @Nullable List<PlayCardsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, PlayCardsBean item) {
        if(!TextUtils.isEmpty(item.tournament)){
            helper.setText(R.id.tv_title,item.tournament);
        }else{
            helper.setText(R.id.tv_title,"");
        }

        if(!TextUtils.isEmpty(item.matchResult)){
            helper.setText(R.id.tv_time, item.matchResult);
        }else{
            helper.setText(R.id.tv_time,"");
        }

        ImageView iv_home_logo = helper.getView(R.id.iv_home_logo);
        GlideUtil.loadTeamImageDefault(mContext, item.homeLogo, iv_home_logo);
        ImageView iv_away_logo = helper.getView(R.id.iv_away_logo);
        GlideUtil.loadTeamImageDefault(mContext, item.awayLogo, iv_away_logo);

        if(!TextUtils.isEmpty(item.homeName)) {
            helper.setText(R.id.tv_home_name, item.homeName);
        }else {
            helper.setText(R.id.tv_home_name, "");
        }
        if(!TextUtils.isEmpty(item.awayName)) {
            helper.setText(R.id.tv_away_name, item.awayName);
        }else {
            helper.setText(R.id.tv_away_name, "");
        }

        if(item.liveStatus != 1){
            helper.setText(R.id.tv_home_score, "");
            helper.setText(R.id.tv_home_score2, "﹣");
            helper.setText(R.id.tv_away_score, "");
            helper.setText(R.id.tv_away_score2, "﹣");
            return;
        }

        if(!TextUtils.isEmpty(item.homeDisplayScore) && !item.homeDisplayScore.equals("0")){
            if (item.homeDisplayScore.contains("(")) {
                String[] split = item.homeDisplayScore.split("\\(");
                helper.setText(R.id.tv_home_score, split[0]);
                helper.setText(R.id.tv_home_score2, " (" + split[1]);
            } else {
                helper.setText(R.id.tv_home_score, item.homeDisplayScore);
            }
        }else{
            helper.setText(R.id.tv_home_score, "");
            helper.setText(R.id.tv_home_score2, "﹣");
        }

        if (!TextUtils.isEmpty(item.awayDisplayScore) && !item.awayDisplayScore.equals("0")) {
            if (item.awayDisplayScore.contains("(")) {
                String[] split = item.awayDisplayScore.split("\\(");
                helper.setText(R.id.tv_away_score, split[0]);
                helper.setText(R.id.tv_away_score2, " (" + split[1]);
            } else {
                helper.setText(R.id.tv_away_score, item.awayDisplayScore);
            }
        } else {
            helper.setText(R.id.tv_away_score, "");
            helper.setText(R.id.tv_away_score2, "﹣");
        }

    }

}
