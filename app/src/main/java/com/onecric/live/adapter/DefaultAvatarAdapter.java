package com.onecric.live.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.AvatarBean;
import com.onecric.live.util.GlideUtil;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class DefaultAvatarAdapter extends BaseQuickAdapter<AvatarBean, BaseViewHolder> {
    public DefaultAvatarAdapter(int layoutResId, @Nullable List<AvatarBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, AvatarBean item) {
        ImageView iv_avatar = helper.getView(R.id.iv_avatar);
        GlideUtil.loadUserImageDefault(mContext, item.getAvatar(), iv_avatar);
        if (item.isSelect()) {
            helper.getView(R.id.iv_tick).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.iv_tick).setVisibility(View.GONE);
        }
    }
}
