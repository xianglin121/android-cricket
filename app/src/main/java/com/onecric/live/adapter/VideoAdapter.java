package com.onecric.live.adapter;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.ShortVideoBean;
import com.onecric.live.util.GlideUtil;
import com.tencent.qcloud.tuikit.tuichat.component.face.FaceManager;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class VideoAdapter extends BaseQuickAdapter<ShortVideoBean, BaseViewHolder> {
    public VideoAdapter(int layoutResId, @Nullable List<ShortVideoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ShortVideoBean item) {
        ImageView iv_cover = helper.getView(R.id.iv_cover);
//        ProgressBar progressBar = (ProgressBar) helper.getView(R.id.progress);
        if (item.getVideo() != null && item.getVideo().size() > 0) {
            GlideUtil.loadVideoImageDefault(mContext, item.getVideo().get(0).getImg(), iv_cover);
        } else {
            iv_cover.setImageResource(R.mipmap.img_video_default);
        }
        if (!TextUtils.isEmpty(item.getTitle())) {
            helper.setText(R.id.tv_title, FaceManager.handlerEmojiText(item.getTitle()));
        } else {
            helper.setText(R.id.tv_title, "");
        }
        helper.setText(R.id.tv_play_count, item.getClick()>1000?(float)item.getClick()/1000+"K":item.getClick()+"");
        helper.setText(R.id.tv_like_count, item.getLikes()>1000?(float)item.getLikes()/1000+"K":item.getLikes()+"");
    }
}
