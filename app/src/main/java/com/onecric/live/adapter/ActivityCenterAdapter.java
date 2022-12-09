package com.onecric.live.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.ActivityBean;
import com.onecric.live.util.DpUtil;
import com.onecric.live.util.GlideUtil;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class ActivityCenterAdapter extends BaseQuickAdapter<ActivityBean, BaseViewHolder> {
    public ActivityCenterAdapter(int layoutResId, @Nullable List<ActivityBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ActivityBean item) {
        ImageView iv_cover = helper.getView(R.id.iv_cover);
        GlideUtil.loadRoundImageDefault(mContext, item.getImg(), iv_cover, DpUtil.dp2px(6));
        if (!TextUtils.isEmpty(item.getTitle())) {
            helper.setText(R.id.tv_title, item.getTitle());
        }else {
            helper.setText(R.id.tv_title, "");
        }
        if (!TextUtils.isEmpty(item.getDescribe())) {
            helper.setText(R.id.tv_time, item.getDescribe());
        }else {
            helper.setText(R.id.tv_time, "");
        }
    }
}
