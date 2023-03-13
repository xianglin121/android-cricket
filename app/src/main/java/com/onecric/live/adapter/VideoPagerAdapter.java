package com.onecric.live.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.activity.LiveDetailActivity;
import com.onecric.live.activity.PersonalHomepageActivity;
import com.onecric.live.custom.ButtonFollowView2;
import com.onecric.live.model.ShortVideoBean;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.view.MvpActivity;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.tencent.qcloud.tuikit.tuichat.component.face.FaceManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2018
 * 版权所有
 * <p>
 * 作者：东莞市梦幻科技有限公司
 * 创建时间：2018/10/29
 * <p>
 * 修改人：
 * 修改描述：
 * 修改日期
 */
public class VideoPagerAdapter extends RecyclerView.Adapter<VideoPagerAdapter.VideoPagerHolder> {

    private List<ShortVideoBean> videoBeans;
    private Activity activity;

    public VideoPagerAdapter(Activity activity) {
        videoBeans = new ArrayList<>();
        this.activity = activity;
    }

    public void setBeans(List<ShortVideoBean> videoBeans, boolean isRefresh) {
        if (isRefresh)
            this.videoBeans.clear();
        if (videoBeans != null)
            this.videoBeans.addAll(videoBeans);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VideoPagerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VideoPagerHolder videoPagerHolder = new VideoPagerHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_video_pager,
                        parent,
                        false));
        return videoPagerHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VideoPagerHolder holder, int position) {
        VideoPagerHolder viewHolder = holder;
        viewHolder.position = position;
//        GSYVideoManager videoManager = (GSYVideoManager) holder.videoView.getGSYVideoManager();
//        videoManager.setNeedMute(true);
        ShortVideoBean bean = videoBeans.get(position);
        if (bean.isSilence()) {
            holder.rl_silence.setVisibility(View.VISIBLE);
        } else {
            holder.rl_silence.setVisibility(View.GONE);
        }
        holder.rl_silence.setOnClickListener(v -> {
            holder.rl_silence.setVisibility(View.GONE);
            GSYVideoManager videoManager = (GSYVideoManager) holder.videoView.getGSYVideoManager();
            videoManager.setNeedMute(false);
//            bean.setSilence(false);
            for (ShortVideoBean item : videoBeans) {
                item.setSilence(false);
            }
        });
//        if (bean.getVideo() != null && bean.getVideo().size() > 0) {
//            GlideUtil.loadImageDefault(activity, bean.getVideo().get(0).getImg(), holder.coverImage);
//        }
        if (bean.getVideo() != null && bean.getVideo().size() > 0) {
//            GlideUtil.loadImageDefault(activity, bean.getVideo().get(0).getImg(), holder.coverImage);
            ImageView imageView = new ImageView(activity);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            GlideUtil.loadImageDefault(activity, bean.getVideo().get(0).getImg(), imageView);
            holder.videoView.setThumbImageView(imageView);
        }
        GlideUtil.loadUserImageDefault(activity, bean.getAvatar(), holder.iv_avatar);
        holder.iv_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!((MvpActivity) activity).isFastDoubleClick())
                    PersonalHomepageActivity.forward(activity, bean.getUid() + "");
            }
        });
        if (!TextUtils.isEmpty(bean.getUser_nickname())) {
            holder.tv_name.setText(bean.getUser_nickname());
        } else {
            holder.tv_name.setText("");
        }
        if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getUid())) {
            if (bean.getUid() == Integer.valueOf(CommonAppConfig.getInstance().getUid())) {
                holder.iv_follow.setVisibility(View.GONE);
            } else {
                holder.iv_follow.setVisibility(View.VISIBLE);
            }
        } else {
            holder.iv_follow.setVisibility(View.VISIBLE);
        }
        if (bean.getIs_attention() == 1) {
            holder.iv_follow.setFollow(true);
        } else {
            holder.iv_follow.setFollow(false);
        }
        if (!TextUtils.isEmpty(bean.getTitle())) {
            holder.tv_content.setText(FaceManager.handlerEmojiText(bean.getTitle()));
        } else {
            holder.tv_content.setText("");
        }
        if (bean.getIs_likes() == 1) {
            holder.iv_like.setSelected(true);
        } else {
            holder.iv_like.setSelected(false);
        }
        holder.tv_like_count.setText(bean.getLikes() > 1000 ? String.format("%.1f", (float) bean.getLikes() / 1000) + "K" : bean.getLikes() + "");
        holder.tv_comment_count.setText(String.valueOf(bean.getComment_count()));
    }

    @Override
    public int getItemCount() {
        return videoBeans.size();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull VideoPagerHolder holder) {
        resetViewHolder(holder);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull VideoPagerHolder holder) {
        resetViewHolder(holder);
    }

    @Override
    public void onViewRecycled(@NonNull VideoPagerHolder holder) {
        resetViewHolder(holder);
    }

    public final void resetViewHolder(VideoPagerHolder holder) {
        if (holder.videoView.getGSYVideoManager().isPlaying())
            holder.videoView.getGSYVideoManager().stop();
        holder.videoView.setTag(null);
//        holder.clickView.setOnClickListener(null);
        holder.iv_follow.setOnClickListener(null);
        holder.iv_like.setOnClickListener(null);
        holder.iv_comment.setOnClickListener(null);
        holder.iv_more.setOnClickListener(null);
//        holder.mPauseIv.setVisibility(View.GONE);
//        holder.coverImage.setVisibility(View.VISIBLE);
    }

    public ShortVideoBean getItem(int position) {
        return videoBeans.get(position);
    }

    public void play() {

    }

    public void showLoadingDialog() {

    }

    public void dismissLoadingDialog() {

    }

    public class VideoPagerHolder extends RecyclerView.ViewHolder {

        public StandardGSYVideoPlayer videoView;
        //        public ImageView coverImage;
        //        public View clickView;
//        public ImageView mPauseIv;
        public RelativeLayout rl_silence;
        public ImageView iv_avatar;
        public TextView tv_name;
        public ButtonFollowView2 iv_follow;
        public TextView tv_content;
        public ImageView iv_like;
        public TextView tv_like_count;
        public ImageView iv_comment;
        public TextView tv_comment_count;
        public ImageView iv_more;

        public int position;

        VideoPagerHolder(View itemView) {

            super(itemView);

//            clickView = itemView.findViewById(R.id.click_view);
            videoView = itemView.findViewById(R.id.video_view);
            rl_silence = itemView.findViewById(R.id.rl_silence);
//            coverImage = itemView.findViewById(R.id.cover_iv);
//            mPauseIv = itemView.findViewById(R.id.pause_iv);
            iv_avatar = itemView.findViewById(R.id.iv_avatar);
            tv_name = itemView.findViewById(R.id.tv_name);
            iv_follow = itemView.findViewById(R.id.iv_follow);
            tv_content = itemView.findViewById(R.id.tv_content);
            iv_like = itemView.findViewById(R.id.iv_like);
            tv_like_count = itemView.findViewById(R.id.tv_like_count);
            iv_comment = itemView.findViewById(R.id.iv_comment);
            tv_comment_count = itemView.findViewById(R.id.tv_comment_count);
            iv_more = itemView.findViewById(R.id.iv_more);
        }
    }
}