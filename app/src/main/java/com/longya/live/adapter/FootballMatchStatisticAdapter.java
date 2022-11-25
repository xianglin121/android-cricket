package com.longya.live.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.custom.ScoreProgressBar;
import com.longya.live.model.StatisticBean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class FootballMatchStatisticAdapter extends BaseQuickAdapter<StatisticBean, BaseViewHolder> {
    public FootballMatchStatisticAdapter(int layoutResId, @Nullable List<StatisticBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, StatisticBean item) {
        ScoreProgressBar score_pb = helper.getView(R.id.score_pb);
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_score_one = helper.getView(R.id.tv_score_one);
        TextView tv_score_two = helper.getView(R.id.tv_score_two);
        tv_title.setText(item.getName());
        if (TextUtils.isEmpty(item.getHomeData())) {
            item.setHomeData("0");
        }
        if (TextUtils.isEmpty(item.getAwayData())) {
            item.setAwayData("0");
        }
        tv_score_one.setText(item.getHomeData());
        tv_score_two.setText(item.getAwayData());

        score_pb.postDelayed(new Runnable() {
            @Override
            public void run() {
                int max = Integer.valueOf(item.getHomeData()) + Integer.valueOf(item.getAwayData());
                float homePercent = 0;
                if (Integer.valueOf(item.getHomeData()) > 0) {
                    homePercent = new BigDecimal(item.getHomeData()).divide(new BigDecimal(max), 2, RoundingMode.HALF_UP).floatValue();
                }
                float awayPercent = 0;
                if (Integer.valueOf(item.getAwayData()) > 0) {
                    awayPercent = new BigDecimal(item.getAwayData()).divide(new BigDecimal(max), 2, RoundingMode.HALF_UP).floatValue();
                }
                int leftColor = 0;
                int rightColor = 0;
                if (Integer.valueOf(item.getHomeData()) > Integer.valueOf(item.getAwayData())) {
                    leftColor = mContext.getResources().getColor(R.color.c_F84A4B);
                    rightColor = mContext.getResources().getColor(R.color.c_4D687AE0);
                } else if (Integer.valueOf(item.getHomeData()) < Integer.valueOf(item.getAwayData())) {
                    leftColor = mContext.getResources().getColor(R.color.c_4DF84A4B);
                    rightColor = mContext.getResources().getColor(R.color.c_687AE0);
                } else {
                    leftColor = mContext.getResources().getColor(R.color.c_F84A4B);
                    rightColor = mContext.getResources().getColor(R.color.c_687AE0);
                }
                score_pb.setProgress(leftColor, rightColor, homePercent, awayPercent);
            }
        }, 100);
    }
}
