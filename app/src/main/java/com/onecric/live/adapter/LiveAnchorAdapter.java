package com.onecric.live.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.LiveBean;
import com.onecric.live.model.LiveFiltrateBean;
import com.onecric.live.util.GlideUtil;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class LiveAnchorAdapter extends BaseQuickAdapter<LiveFiltrateBean, BaseViewHolder> {
    public LiveAnchorAdapter(int layoutResId, @Nullable List<LiveFiltrateBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, LiveFiltrateBean item) {
        ImageView iv_avatar = helper.getView(R.id.iv_avatar);
        GlideUtil.loadUserImageDefault(mContext, item.getAvatar(), iv_avatar);
        if (!TextUtils.isEmpty(item.getUserNickname())) {
            helper.setText(R.id.tv_name, item.getUserNickname());
        }else {
            helper.setText(R.id.tv_name, "");
        }
    }
}
