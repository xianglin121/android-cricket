package com.onecric.live.adapter;

import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.activity.PersonalHomepageActivity;
import com.onecric.live.activity.VideoCompletePlayActivity;
import com.onecric.live.model.CommunityBean;
import com.onecric.live.util.GlideUtil;
import com.tencent.qcloud.tuikit.tuichat.component.face.FaceManager;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/23
 */
public class CommunityCommentAdapter extends BaseQuickAdapter<CommunityBean, BaseViewHolder> {
    public CommunityCommentAdapter(int layoutResId, @Nullable List<CommunityBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CommunityBean item) {
        helper.addOnClickListener(R.id.tv_reply);
        helper.addOnClickListener(R.id.ll_like);
        ImageView iv_avatar = helper.getView(R.id.iv_avatar);
        iv_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonalHomepageActivity.forward(mContext, item.getUid() + "");
            }
        });
        GlideUtil.loadUserImageDefault(mContext, item.getAvatar(), iv_avatar);
        if (!TextUtils.isEmpty(item.getUser_nickname())) {
            helper.setText(R.id.tv_name, item.getUser_nickname());
        }else {
            helper.setText(R.id.tv_name, "");
        }
        helper.setText(R.id.tv_like, String.valueOf(item.getLike()));
        ImageView iv_like = helper.getView(R.id.iv_like);
        if (item.getIs_likes() == 0) {
            iv_like.setSelected(false);
        }else {
            iv_like.setSelected(true);
        }
        if (!TextUtils.isEmpty(item.getContent())) {
            SpannableStringBuilder msg = FaceManager.handlerEmojiText(item.getContent());
            helper.setText(R.id.tv_content, msg);
        }else {
            helper.setText(R.id.tv_content, "");
        }
        ImageView iv_cover = helper.getView(R.id.iv_cover);
        ImageView iv_icon = helper.getView(R.id.iv_icon);
        RecyclerView rv_image = helper.getView(R.id.rv_image);
        if (item.getIs_flie_type() == 0) {
            iv_cover.setVisibility(View.GONE);
            iv_icon.setVisibility(View.GONE);
            rv_image.setVisibility(View.VISIBLE);
            if (item.getImg() != null && item.getImg().size() > 0) {
                rv_image.setLayoutManager(new GridLayoutManager(mContext, 3));
                rv_image.setAdapter(new ImageAdapter(mContext, item.getImg()));
            }
        }else {
            iv_cover.setVisibility(View.VISIBLE);
            iv_icon.setVisibility(View.VISIBLE);
            rv_image.setVisibility(View.GONE);
            if (item.getVideo() != null && item.getVideo().size() > 0) {
                GlideUtil.loadImageDefault(mContext, item.getVideo().get(0).getImg(), iv_cover);
            }
            iv_cover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VideoCompletePlayActivity.forward(mContext, item.getVideo().get(0).getVideo());
                }
            });
            iv_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VideoCompletePlayActivity.forward(mContext, item.getVideo().get(0).getVideo());
                }
            });
        }
        helper.setText(R.id.tv_reply, item.getComment() + " " + mContext.getString(R.string.replies));
    }

    @Override
    protected void convertPayloads(@NonNull BaseViewHolder helper, CommunityBean item, @NonNull List<Object> payloads) {
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
