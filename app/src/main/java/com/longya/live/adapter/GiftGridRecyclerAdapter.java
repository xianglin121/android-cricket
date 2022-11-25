package com.longya.live.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.model.GiftBean;
import com.longya.live.util.GlideUtil;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/11/22
 */
public class GiftGridRecyclerAdapter extends BaseQuickAdapter<GiftBean, BaseViewHolder> {
    public GiftGridRecyclerAdapter(int layoutResId, @Nullable List<GiftBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, GiftBean item) {
        ImageView iv_rect = helper.getView(R.id.iv_rect);
        ImageView iv_gift = helper.getView(R.id.iv_gift);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_cost = helper.getView(R.id.tv_cost);
        if (item.isSelect()) {
            iv_rect.setVisibility(View.VISIBLE);
        }else {
            iv_rect.setVisibility(View.INVISIBLE);
        }
        GlideUtil.loadImageDefault(mContext, item.getGifticon(), iv_gift);
        if (!TextUtils.isEmpty(item.getGiftname())) {
            tv_name.setText(item.getGiftname());
        }else {
            tv_name.setText("");
        }
        tv_cost.setText(item.getNeedcoin() + mContext.getString(R.string.diamond));
    }
}
