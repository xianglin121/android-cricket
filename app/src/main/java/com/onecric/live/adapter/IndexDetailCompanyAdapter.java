package com.onecric.live.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.BasketballIndexListBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class IndexDetailCompanyAdapter extends BaseQuickAdapter<BasketballIndexListBean, BaseViewHolder> {
    public IndexDetailCompanyAdapter(int layoutResId, @Nullable List<BasketballIndexListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, BasketballIndexListBean item) {
        ConstraintLayout rootView = helper.getView(R.id.rootView);
        ImageView iv_label = helper.getView(R.id.iv_label);
        TextView tv_name = helper.getView(R.id.tv_name);
        if (!TextUtils.isEmpty(item.getName())) {
            tv_name.setText(item.getName());
        }else {
            tv_name.setText("");
        }
        if (item.isSelected()) {
            rootView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            iv_label.setVisibility(View.VISIBLE);
            tv_name.setTextColor(mContext.getResources().getColor(R.color.c_E3AC72));
        }else {
            rootView.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
            iv_label.setVisibility(View.GONE);
            tv_name.setTextColor(mContext.getResources().getColor(R.color.c_999999));
        }
    }
}
