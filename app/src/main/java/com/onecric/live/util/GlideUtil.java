package com.onecric.live.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.onecric.live.R;
import com.onecric.live.common.GlideRoundTransform;

/**
 * Glide 工具类
 */

public class GlideUtil {

    //设置用户图像的加载中以及加载失败图片
    public static void loadUserImageDefault(Context mContext, String path, ImageView mImageView) {
        if (mContext != null) {
            Glide.with(mContext).load(path).placeholder(R.mipmap.bg_avatar_default).dontAnimate().error(R.mipmap.bg_avatar_default)
                    .into(mImageView);
        }
    }

    //设置球队头像的加载中以及加载失败图片
    public static void loadTeamImageDefault(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).placeholder(R.mipmap.img_team_logo_default).dontAnimate().error(R.mipmap.img_team_logo_default)
                .into(mImageView);
    }

    //设置球队头像的加载中以及加载失败图片
    public static void loadUpdatesImageDefault(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).placeholder(R.mipmap.img_updates_default).dontAnimate().error(R.mipmap.img_updates_default)
                .into(mImageView);
    }


    //设置图像的加载中以及加载失败图片
    public static void loadImageDefault(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).dontAnimate().into(mImageView);
    }


    //设置video封面图片的加载中以及加载失败图片
    public static void loadVideoImageDefault(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).placeholder(R.mipmap.img_video_default).dontAnimate().error(R.mipmap.img_video_default).into(mImageView);
    }

    //加载圆角图片
    public static void loadRoundImageDefault(Context mContext, String path, ImageView mImageView, int radius) {
        Glide.with(mContext).load(path).transform(new CenterCrop(), new GlideRoundTransform(radius)).into(mImageView);
    }

    //设置图像的加载中以及加载失败图片
    public static void loadLiveImageDefault(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).placeholder(R.mipmap.ball_live_bg).error(R.mipmap.ball_live_bg).dontAnimate().into(mImageView);
    }
}