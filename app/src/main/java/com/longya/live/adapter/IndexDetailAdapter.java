package com.longya.live.adapter;

import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.model.BasketballIndexDetailBean;

import java.math.BigDecimal;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class IndexDetailAdapter extends BaseQuickAdapter<BasketballIndexDetailBean, BaseViewHolder> {
    public IndexDetailAdapter(int layoutResId, @Nullable List<BasketballIndexDetailBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, BasketballIndexDetailBean item) {
        TextView tv_host = helper.getView(R.id.tv_host);
        TextView tv_index = helper.getView(R.id.tv_index);
        TextView tv_guest = helper.getView(R.id.tv_guest);
        TextView tv_date = helper.getView(R.id.tv_date);
        if (!TextUtils.isEmpty(item.getHost())) {
            tv_host.setText(item.getHost());
        }else {
            tv_host.setText("");
        }
        if (!TextUtils.isEmpty(item.getEarly())) {
            tv_index.setText(item.getEarly());
        }else {
            tv_index.setText("");
        }
        if (!TextUtils.isEmpty(item.getGuest())) {
            tv_guest.setText(item.getGuest());
        }else {
            tv_guest.setText("");
        }
        if ((helper.getLayoutPosition()+1) < getItemCount()) {
            BasketballIndexDetailBean nextItem = getItem(helper.getLayoutPosition() + 1);
            //主
            if (!TextUtils.isEmpty(item.getHost()) && !TextUtils.isEmpty(nextItem.getHost())) {
                int flag = new BigDecimal(item.getHost()).compareTo(new BigDecimal(nextItem.getHost()));
                if (flag > 0) {
                    tv_host.setTextColor(mContext.getResources().getColor(R.color.c_E92525));
                }else if (flag < 0) {
                    tv_host.setTextColor(mContext.getResources().getColor(R.color.c_44AC3D));
                }else {
                    tv_host.setTextColor(mContext.getResources().getColor(R.color.c_999999));
                }
            }else {
                tv_host.setTextColor(mContext.getResources().getColor(R.color.c_999999));
            }
            //盘口
            if (!TextUtils.isEmpty(item.getEarly()) && !TextUtils.isEmpty(nextItem.getEarly())) {
                int flag = new BigDecimal(item.getEarly()).compareTo(new BigDecimal(nextItem.getEarly()));
                if (flag > 0) {
                    tv_index.setTextColor(mContext.getResources().getColor(R.color.c_44AC3D));
                }else if (flag < 0) {
                    tv_index.setTextColor(mContext.getResources().getColor(R.color.c_E92525));
                }else {
                    tv_index.setTextColor(mContext.getResources().getColor(R.color.c_999999));
                }
            }else {
                tv_index.setTextColor(mContext.getResources().getColor(R.color.c_999999));
            }
            //客
            if (!TextUtils.isEmpty(item.getGuest()) && !TextUtils.isEmpty(nextItem.getGuest())) {
                int flag = new BigDecimal(item.getGuest()).compareTo(new BigDecimal(nextItem.getGuest()));
                if (flag > 0) {
                    tv_guest.setTextColor(mContext.getResources().getColor(R.color.c_E92525));
                }else if (flag < 0) {
                    tv_guest.setTextColor(mContext.getResources().getColor(R.color.c_44AC3D));
                }else {
                    tv_guest.setTextColor(mContext.getResources().getColor(R.color.c_999999));
                }
            }else {
                tv_guest.setTextColor(mContext.getResources().getColor(R.color.c_999999));
            }
        }else {
            tv_host.setTextColor(mContext.getResources().getColor(R.color.c_999999));
            tv_index.setTextColor(mContext.getResources().getColor(R.color.c_999999));
            tv_guest.setTextColor(mContext.getResources().getColor(R.color.c_999999));
        }
        String date = "";
        if (!TextUtils.isEmpty(item.getDate())) {
            date = date + item.getDate();
        }
        if (!TextUtils.isEmpty(item.getChange_time())) {
            date = date + "\n" + item.getChange_time();
        }
        tv_date.setText(date);
    }
}
