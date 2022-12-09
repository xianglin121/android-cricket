package com.onecric.live.adapter;

import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.MovingBean;
import com.onecric.live.util.GlideUtil;
import com.tencent.qcloud.tuikit.tuichat.component.face.FaceManager;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/23
 */
public class AnchorMovingReplyAdapter extends BaseQuickAdapter<MovingBean, BaseViewHolder> {
    public AnchorMovingReplyAdapter(int layoutResId, @Nullable List<MovingBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MovingBean item) {
        helper.addOnClickListener(R.id.ll_like);
        ImageView iv_avatar = helper.getView(R.id.iv_avatar);
        GlideUtil.loadUserImageDefault(mContext, item.getAvatar(), iv_avatar);
        if (!TextUtils.isEmpty(item.getUser_nickname())) {
            helper.setText(R.id.tv_name, item.getUser_nickname());
        }else {
            helper.setText(R.id.tv_name, "");
        }
        if (!TextUtils.isEmpty(item.getContent())) {
            SpannableStringBuilder sp = FaceManager.handlerEmojiText(item.getContent());
            helper.setText(R.id.tv_content, sp);
        }else {
            helper.setText(R.id.tv_content, "");
        }
        ImageView iv_like = helper.getView(R.id.iv_like);
        if (item.getIs_likes() == 0) {
            iv_like.setSelected(false);
        }else {
            iv_like.setSelected(true);
        }
        helper.setText(R.id.tv_like, String.valueOf(item.getLike()));
    }

    @Override
    protected void convertPayloads(@NonNull BaseViewHolder helper, MovingBean item, @NonNull List<Object> payloads) {
        if (payloads != null && payloads.size() > 0) {
            ImageView iv_like = helper.getView(R.id.iv_like);
            if (item.getIs_likes() == 0) {
                iv_like.setSelected(false);
            }else {
                iv_like.setSelected(true);
            }
            helper.setText(R.id.tv_like, String.valueOf(item.getLike()));
        }
    }
}
