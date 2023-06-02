package com.onecric.live.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.VideoShowBean;
import com.onecric.live.util.GlideUtil;

import java.util.List;

public class ShowVideoAdapter extends BaseQuickAdapter<VideoShowBean, BaseViewHolder> {
    public ShowVideoAdapter(int layoutResId, @Nullable List<VideoShowBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, VideoShowBean item) {
        ImageView iv_cover = helper.getView(R.id.iv_thumb);
        if (item.video != null) {
            GlideUtil.loadVideoImageDefault(mContext, item.img, iv_cover);
        } else {
            iv_cover.setImageResource(R.mipmap.img_video_default);
        }
    }
}
