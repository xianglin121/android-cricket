package com.longya.live.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.model.FilterInnerBean;
import com.tencent.qcloud.tuicore.interfaces.ITUIExtension;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/18
 */
public class MatchFilterInnerAdapter extends BaseQuickAdapter<FilterInnerBean, BaseViewHolder> {
    public MatchFilterInnerAdapter(int layoutResId, @Nullable List<FilterInnerBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, FilterInnerBean item) {
        ConstraintLayout rootView = helper.getView(R.id.rootView);
        ImageView iv_check = helper.getView(R.id.iv_check);
        if (!TextUtils.isEmpty(item.getName())) {
            helper.setText(R.id.tv_name, item.getName());
        }else {
            helper.setText(R.id.tv_name, "");
        }
        helper.setText(R.id.tv_count, String.valueOf(item.getCount()));
        if (item.isCheck()) {
            rootView.setBackgroundResource(R.drawable.shape_fff1e0_6dp_rec);
            iv_check.setSelected(true);
            helper.setTextColor(R.id.tv_name, mContext.getResources().getColor(R.color.c_E3AC72));
            helper.setTextColor(R.id.tv_count, mContext.getResources().getColor(R.color.c_E3AC72));
        }else {
            rootView.setBackgroundResource(R.drawable.shape_white_6dp_rec);
            iv_check.setSelected(false);
            helper.setTextColor(R.id.tv_name, mContext.getResources().getColor(R.color.c_999999));
            helper.setTextColor(R.id.tv_count, mContext.getResources().getColor(R.color.c_999999));
        }
    }
}
