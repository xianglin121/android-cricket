package com.onecric.live.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.BasketballEventBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class BasketballEventLiveInnerAdapter extends BaseQuickAdapter<BasketballEventBean, BaseViewHolder> {
    public BasketballEventLiveInnerAdapter(int layoutResId, @Nullable List<BasketballEventBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, BasketballEventBean item) {
        LinearLayout rootView = helper.getView(R.id.rootView);
        ImageView iv_label = helper.getView(R.id.iv_label);
        if (helper.getLayoutPosition()%2 == 0) {
            rootView.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
        }else {
            rootView.setBackgroundColor(mContext.getResources().getColor(R.color.c_FFFBF6));
        }
        if ("0".equals(item.getGoal_type())) {
            iv_label.setVisibility(View.GONE);
        }else if ("1".equals(item.getGoal_type())) {
            iv_label.setVisibility(View.VISIBLE);
            iv_label.setBackground(mContext.getResources().getDrawable(R.drawable.shape_blue_team_half));
        }else if ("2".equals(item.getGoal_type())) {
            iv_label.setVisibility(View.VISIBLE);
            iv_label.setBackground(mContext.getResources().getDrawable(R.drawable.shape_red_team_half));
        }
        if (!TextUtils.isEmpty(item.getConducttime())) {
            helper.setText(R.id.tv_time, item.getConducttime());
        }else {
            helper.setText(R.id.tv_time, "");
        }
        if (!TextUtils.isEmpty(item.getInfo())) {
            helper.setText(R.id.tv_content, item.getInfo());
        }else {
            helper.setText(R.id.tv_content, "");
        }
    }
}
