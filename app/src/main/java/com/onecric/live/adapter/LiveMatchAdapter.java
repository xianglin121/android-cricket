package com.onecric.live.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.LiveMatchListBean;
import com.onecric.live.util.GlideUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class LiveMatchAdapter extends BaseQuickAdapter<LiveMatchListBean.MatchItemBean, BaseViewHolder> {
    private SimpleDateFormat sfdate1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat sfdate2 = new SimpleDateFormat("MM-dd hh:mm:ss", Locale.ENGLISH);
    public LiveMatchAdapter(int layoutResId, @Nullable List<LiveMatchListBean.MatchItemBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, LiveMatchListBean.MatchItemBean item) {
        helper.setText(R.id.tv_title,item.getTitle());
        helper.setText(R.id.tv_time, "");
        if(!TextUtils.isEmpty(item.getScheduled())) {
            try {
                String dateStr = sfdate2.format(sfdate1.parse(item.getScheduled()));
                helper.setText(R.id.tv_time, dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
                helper.setText(R.id.tv_time, item.getScheduled());
            }
        }

        ImageView iv_home_logo = helper.getView(R.id.iv_home_logo);
        GlideUtil.loadTeamImageDefault(mContext, item.getHome_logo(), iv_home_logo);
        ImageView iv_away_logo = helper.getView(R.id.iv_away_logo);
        GlideUtil.loadTeamImageDefault(mContext, item.getAway_logo(), iv_away_logo);

        if(!TextUtils.isEmpty(item.getHome_name())) {
            helper.setText(R.id.tv_home_name, item.getHome_name());
        }else {
            helper.setText(R.id.tv_home_name, "");
        }
        if(!TextUtils.isEmpty(item.getAway_name())) {
            helper.setText(R.id.tv_away_name, item.getAway_name());
        }else {
            helper.setText(R.id.tv_away_name, "");
        }

        if(item.getIslive() != 1){
            helper.setVisible(R.id.iv_live,false);
            helper.setText(R.id.tv_home_score, "");//
            helper.setText(R.id.tv_home_score2, "﹣");
            helper.setText(R.id.tv_away_score, "");
            helper.setText(R.id.tv_away_score2, "﹣");
            return;
        }

        if(!TextUtils.isEmpty(item.getHome_score()) && !item.getHome_score().equals("0")){
            helper.setVisible(R.id.iv_live,true);
            if (item.getHome_score().contains("(")) {
                String[] split = item.getHome_score().split("\\(");
                helper.setText(R.id.tv_home_score, split[0]);
                helper.setText(R.id.tv_home_score2, " (" + split[1]);
            } else {
                helper.setText(R.id.tv_home_score, item.getHome_score());
            }
        }else{
            helper.setText(R.id.tv_home_score, "");
            helper.setText(R.id.tv_home_score2, "﹣");
        }

        if (!TextUtils.isEmpty(item.getAway_score()) && !item.getAway_score().equals("0")) {
            if (item.getAway_score().contains("(")) {
                String[] split = item.getAway_score().split("\\(");
                helper.setText(R.id.tv_away_score, split[0]);
                helper.setText(R.id.tv_away_score2, " (" + split[1]);
            } else {
                helper.setText(R.id.tv_away_score, item.getAway_score());
            }
        } else {
            helper.setText(R.id.tv_away_score, "");
            helper.setText(R.id.tv_away_score2, "﹣");
        }

    }
}
