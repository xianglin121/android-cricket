package com.onecric.live.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.DateBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class MatchSelectDateAdapter extends BaseQuickAdapter<DateBean, BaseViewHolder> {
    private int mType;

    public MatchSelectDateAdapter(int layoutResId, @Nullable List<DateBean> data, int type) {
        super(layoutResId, data);
        mType = type;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DateBean item) {
        ConstraintLayout root = helper.getView(R.id.rootView);
        TextView tv_today = helper.getView(R.id.tv_today);
        TextView tv_date = helper.getView(R.id.tv_date);
        if (item.isSelect()) {
            tv_today.setTextColor(mContext.getResources().getColor(R.color.c_87390E));
            tv_date.setTextColor(mContext.getResources().getColor(R.color.c_87390E));
            root.setBackground(mContext.getResources().getDrawable(R.drawable.bg_match_select_date));
        }else {
            tv_today.setTextColor(mContext.getResources().getColor(R.color.c_999999));
            tv_date.setTextColor(mContext.getResources().getColor(R.color.c_999999));
            root.setBackground(null);
        }
        if (mType == 2) {
            if (helper.getLayoutPosition() == 0) {
                tv_today.setVisibility(View.VISIBLE);
                tv_date.setVisibility(View.GONE);
                return;
            }
        }else {
            if (helper.getLayoutPosition() == getItemCount()-1) {
                tv_today.setVisibility(View.VISIBLE);
                tv_date.setVisibility(View.GONE);
                return;
            }
        }
        tv_today.setVisibility(View.GONE);
        tv_date.setVisibility(View.VISIBLE);
        String[] split = item.getDate().split(" ");
        SimpleDateFormat format = new SimpleDateFormat("yyyy MM/dd");
        int dayForWeek = 0;
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(format.parse(item.getDate()));
            if (c.get(Calendar.DAY_OF_WEEK) == 1) {
                dayForWeek = 7;
            } else {
                dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String week = "";
        switch (dayForWeek) {
            case 1:
                week = "星期一";
                break;
            case 2:
                week = "星期二";
                break;
            case 3:
                week = "星期三";
                break;
            case 4:
                week = "星期四";
                break;
            case 5:
                week = "星期五";
                break;
            case 6:
                week = "星期六";
                break;
            case 7:
                week = "星期日";
                break;
        }
        tv_date.setText(split[1] + "\n" + week);
    }
}
