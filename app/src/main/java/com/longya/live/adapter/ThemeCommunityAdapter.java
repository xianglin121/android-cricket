package com.longya.live.adapter;

import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.activity.VideoCompletePlayActivity;
import com.longya.live.custom.FoldTextView;
import com.longya.live.model.CommunityBean;
import com.longya.live.model.MovingBean;
import com.longya.live.util.GlideUtil;
import com.tencent.qcloud.tuikit.tuichat.component.face.FaceManager;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class ThemeCommunityAdapter extends BaseQuickAdapter<CommunityBean, BaseViewHolder> {
    public ThemeCommunityAdapter(int layoutResId, @Nullable List<CommunityBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CommunityBean item) {
//        helper.addOnClickListener(R.id.ll_comment);
        helper.addOnClickListener(R.id.ll_like);
        ImageView iv_avatar = helper.getView(R.id.iv_avatar);
        GlideUtil.loadUserImageDefault(mContext, item.getAvatar(), iv_avatar);
        if (!TextUtils.isEmpty(item.getUser_nickname())) {
            helper.setText(R.id.tv_name, item.getUser_nickname());
        }else {
            helper.setText(R.id.tv_name, "");
        }
        if (!TextUtils.isEmpty(item.getAddtime())) {
            helper.setText(R.id.tv_time, item.getAddtime());
        }else {
            helper.setText(R.id.tv_time, "");
        }
//        FoldTextView tv_content = helper.getView(R.id.tv_content);
//        TextView tv_full_text = helper.getView(R.id.tv_full_text);
        if (!TextUtils.isEmpty(item.getTitle())) {
            SpannableStringBuilder msg = FaceManager.handlerEmojiText(item.getTitle());
            helper.setText(R.id.tv_content, msg);
        }else {
            helper.setText(R.id.tv_content, "");
        }
//        tv_content.post(new Runnable() {
//            @Override
//            public void run() {
//                Layout layout = tv_content.getLayout();
//                if (layout != null) {
//                    if (layout.getLineCount() > 3) {
//                        tv_content.setMaxLines(3);
//                        tv_content.setEllipsize(TextUtils.TruncateAt.END);
//                        tv_content.setMaxEms(300);
//                        tv_full_text.setVisibility(View.VISIBLE);
//                    }else {
//                        tv_content.setMaxLines(Integer.MAX_VALUE);
//                        tv_content.setEllipsize(null);
//                        tv_content.setMaxEms(Integer.MAX_VALUE);
//                        tv_full_text.setVisibility(View.GONE);
//                    }
//                }
//            }
//        });
//        tv_full_text.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (tv_full_text.isSelected()) {
//                    tv_content.setMaxLines(3);
//                    tv_content.setEllipsize(TextUtils.TruncateAt.END);
//                    tv_full_text.setText(mContext.getString(R.string.full_text));
//                }else {
//                    tv_content.setMaxLines(Integer.MAX_VALUE);
//                    tv_content.setEllipsize(null);
//                    tv_full_text.setText(mContext.getString(R.string.part_text));
//                }
//                tv_full_text.setSelected(!tv_full_text.isSelected());
//            }
//        });
        ImageView iv_cover = helper.getView(R.id.iv_cover);
        ImageView iv_icon = helper.getView(R.id.iv_icon);
        RecyclerView rv_image = helper.getView(R.id.rv_image);
        if (item.getIs_flie_type() == 0) {
            iv_cover.setVisibility(View.GONE);
            iv_icon.setVisibility(View.GONE);
            rv_image.setVisibility(View.VISIBLE);
            if (item.getImg() != null) {
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
        helper.setText(R.id.tv_comment, String.valueOf(item.getComment()));
        helper.setText(R.id.tv_like, String.valueOf(item.getLikes()));
        ImageView iv_like = helper.getView(R.id.iv_like);
        if (item.getIs_likes() == 0) {
            iv_like.setSelected(false);
        }else {
            iv_like.setSelected(true);
        }
        ImageView iv_community_icon = helper.getView(R.id.iv_community_icon);
        GlideUtil.loadImageDefault(mContext, item.getClassification_icon(), iv_community_icon);
        if (!TextUtils.isEmpty(item.getClassification_name())) {
            helper.setText(R.id.tv_community_name, item.getClassification_name());
        }else {
            helper.setText(R.id.tv_community_name, "");
        }
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
            helper.setText(R.id.tv_like, String.valueOf(item.getLikes()));
        }
    }
}
