package com.onecric.live.adapter;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.PlayerDataBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class PlayerMatchDataAdapter extends BaseQuickAdapter<PlayerDataBean, BaseViewHolder> {
    public PlayerMatchDataAdapter(int layoutResId, @Nullable List<PlayerDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, PlayerDataBean item) {
        helper.setText(R.id.name, item.name);
        helper.setText(R.id.role, item.role);
        helper.setText(R.id.played_match_num, item.matchNum + "");
        helper.setText(R.id.point_per_match, item.points + "");
        helper.setText(R.id.dt, item.dt + "");
//        if (item.getVideo() != null && item.getVideo().size() > 0) {
//            GlideUtil.loadVideoImageDefault(mContext, item.getVideo().get(0).getImg(), iv_cover);
//        } else {
//            iv_cover.setImageResource(R.mipmap.img_video_default);
//        }
//        if (!TextUtils.isEmpty(item.getTitle())) {
//            helper.setText(R.id.tv_title, FaceManager.handlerEmojiText(item.getTitle()));
//        } else {
//            helper.setText(R.id.tv_title, "");
//        }
//        helper.setText(R.id.tv_play_count, String.valueOf(item.getClick()));
//        helper.setText(R.id.tv_like_count, String.valueOf(item.getLikes()));
    }
}
