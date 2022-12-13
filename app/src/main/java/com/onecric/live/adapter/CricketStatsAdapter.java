package com.onecric.live.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.coorchice.library.SuperTextView;
import com.onecric.live.R;
import com.onecric.live.activity.CricketStatsActivity;
import com.onecric.live.model.CricketStatsListBean;
import com.onecric.live.util.GlideUtil;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/31
 */
public class CricketStatsAdapter extends BaseQuickAdapter<CricketStatsListBean, BaseViewHolder> {
    public CricketStatsAdapter(int layoutResId, @Nullable List<CricketStatsListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CricketStatsListBean item) {
        ImageView iv_avatar = helper.getView(R.id.iv_avatar);
        GlideUtil.loadUserImageDefault(mContext, item.getImg(), iv_avatar);
        SuperTextView tv_number = helper.getView(R.id.tv_number);
        if (helper.getLayoutPosition() == 0) {
            helper.getView(R.id.cl_root).setBackgroundColor(Color.parseColor("#FFE6E2"));
            tv_number.setSolid(mContext.getResources().getColor(R.color.c_DC3C23));
            tv_number.setTextColor(Color.WHITE);
        }else {
            helper.getView(R.id.cl_root).setBackgroundColor(Color.WHITE);
            tv_number.setSolid(mContext.getResources().getColor(R.color.c_E5E5E5));
            tv_number.setTextColor(mContext.getResources().getColor(R.color.c_999999));
        }
        if (helper.getLayoutPosition() == getItemCount()-2) {
            helper.getView(R.id.line).setVisibility(View.INVISIBLE);
        }else {
            helper.getView(R.id.line).setVisibility(View.VISIBLE);
        }
        helper.setText(R.id.tv_number, String.valueOf(helper.getLayoutPosition()+1));
        if (!TextUtils.isEmpty(item.getName())) {
            helper.setText(R.id.tv_name, item.getName());
        }else {
            helper.setText(R.id.tv_name, "");
        }
        if (!TextUtils.isEmpty(item.getAbbreviation())) {
            helper.setText(R.id.tv_team_name, item.getAbbreviation());
        }else {
            helper.setText(R.id.tv_team_name, "");
        }
        TextView tv_one = helper.getView(R.id.tv_one);
        TextView tv_two = helper.getView(R.id.tv_two);
        TextView tv_three = helper.getView(R.id.tv_three);
        TextView tv_four = helper.getView(R.id.tv_four);
        if (mContext instanceof CricketStatsActivity) {
            if (((CricketStatsActivity)mContext).mPosition == 0) {
                tv_one.setText(item.getB());
                tv_two.setText(item.getSr());
                tv_three.setText(TextUtils.isEmpty(item.getVscname())?"":item.getVscname());
                tv_four.setText(item.getRuns());
            }else if (((CricketStatsActivity)mContext).mPosition == 1) {
                tv_one.setText(item.getM());
                tv_two.setText(item.getInn());
                tv_three.setText(item.getAvg());
                tv_four.setText(item.getRuns());
            }else if (((CricketStatsActivity)mContext).mPosition == 2) {
                tv_one.setText(item.getM());
                tv_two.setText(item.getInn());
                tv_three.setText(item.getRuns());
                tv_four.setText(item.getAvg());
            }else if (((CricketStatsActivity)mContext).mPosition == 3) {
                tv_one.setText(item.getM());
                tv_two.setText(item.getInn());
                tv_three.setText(item.getRuns());
                tv_four.setText(item.getSr());
            }else if (((CricketStatsActivity)mContext).mPosition == 4) {
                tv_one.setText(item.getM());
                tv_two.setText(item.getInn());
                tv_three.setText(item.getRuns());
                tv_four.setText(item.getSixs());
            }else if (((CricketStatsActivity)mContext).mPosition == 5) {
                tv_one.setText(item.getM());
                tv_two.setText(item.getO());
                tv_three.setText(item.getAvg());
                tv_four.setText(item.getW());
            }else if (((CricketStatsActivity)mContext).mPosition == 6) {
                tv_one.setText(item.getM());
                tv_two.setText(item.getO());
                tv_three.setText(item.getW());
                tv_four.setText(item.getAvg());
            }else if (((CricketStatsActivity)mContext).mPosition == 7) {
                tv_one.setText(item.getM());
                tv_two.setText(item.getO());
                tv_three.setText(item.getW());
                tv_four.setText(item.getEco());
            }else if (((CricketStatsActivity)mContext).mPosition == 8) {
                tv_one.setText(item.getM());
                tv_two.setText(item.getCt());
            }else {
                tv_one.setText("");
                tv_two.setText("");
                if (tv_three != null) {
                    tv_three.setText("");
                }
                if (tv_four != null) {
                    tv_four.setText("");
                }
            }
        }else {
            tv_one.setText("");
            tv_two.setText("");
            if (tv_three != null) {
                tv_three.setText("");
            }
            if (tv_four != null) {
                tv_four.setText("");
            }
        }
    }
}
