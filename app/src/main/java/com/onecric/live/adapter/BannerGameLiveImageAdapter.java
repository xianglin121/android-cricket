package com.onecric.live.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.onecric.live.R;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.view.NotProgressVideoPlayer;
import com.shuyu.gsyvideoplayer.cache.CacheFactory;
import com.shuyu.gsyvideoplayer.listener.GSYVideoProgressListener;
import com.shuyu.gsyvideoplayer.player.PlayerFactory;
import com.tencent.liteav.demo.superplayer.LivePlayerView;
import com.youth.banner.adapter.BannerAdapter;

import java.util.ArrayList;
import java.util.List;

import tv.danmaku.ijk.media.exo2.Exo2PlayerManager;
import tv.danmaku.ijk.media.exo2.ExoPlayerCacheManager;

public abstract class BannerGameLiveImageAdapter<T> extends BannerAdapter<T, BannerGameLiveImageAdapter.BannerRoundLiveImageHolder> {
    private Activity mActivity;
    public List<T> list;
    public List<BannerGameLiveImageAdapter.BannerRoundLiveImageHolder> mHolder;
    public BannerGameLiveImageAdapter(Activity activity, List<T> mData) {
        super(mData);
        mActivity = activity;
        list = mData;
        mHolder = new ArrayList<>();
    }


    @Override
    public BannerGameLiveImageAdapter.BannerRoundLiveImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner_game, parent, false);
        if(list.size() > mHolder.size()){
            BannerRoundLiveImageHolder h = new BannerGameLiveImageAdapter.BannerRoundLiveImageHolder(view);
            mHolder.add(h);
            return h;
        }
        return new BannerGameLiveImageAdapter.BannerRoundLiveImageHolder(view);
    }

    public class BannerRoundLiveImageHolder extends RecyclerView.ViewHolder {
        public RoundedImageView imageView;
        public ImageView iv_live_status,iv_avatar;
        public TextView tv_eyes_num,tv_title,tv_bottom;
        public LivePlayerView playerView;
        public NotProgressVideoPlayer videoView;
        public FrameLayout fl_main;
        public BannerRoundLiveImageHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.iv_bg);
            this.iv_live_status = itemView.findViewById(R.id.iv_live_status);
            this.tv_eyes_num = itemView.findViewById(R.id.tv_eyes_num);
            this.iv_avatar = itemView.findViewById(R.id.iv_avatar);
            this.tv_title = itemView.findViewById(R.id.tv_title);
            this.tv_bottom = itemView.findViewById(R.id.tv_bottom);
            this.playerView = itemView.findViewById(R.id.playerView);
            this.videoView = itemView.findViewById(R.id.history_video_view);
            this.fl_main = itemView.findViewById(R.id.fl_main);
            PlayerFactory.setPlayManager(Exo2PlayerManager.class);
            CacheFactory.setCacheManager(ExoPlayerCacheManager.class);
            videoView.setLooping(true);
            videoView.getBackButton().setVisibility(View.GONE);
            //是否可以滑动调整
            videoView.setIsTouchWiget(false);
            videoView.setGSYVideoProgressListener(new GSYVideoProgressListener() {
                @Override
                public void onProgress(int progress, int secProgress, int currentPosition, int duration) {
                    if(progress>=100){
                        imageView.setVisibility(View.VISIBLE);
                        videoView.setVisibility(View.GONE);
                    }
                }
            });
            playerView.setMute(true);
            playerView.removeWindowView();
            fl_main.setClipToOutline(true);
        }
    }

    public LivePlayerView getPlayerView(int position,String pull){
        if(mHolder == null || position > mHolder.size()-1){
            return null;
        }
//        int index = position <= 0?mHolder.size()-1:position-1;
        LivePlayerView showPlayerView = mHolder.get(position).playerView;
        showPlayerView.play(pull);
        mHolder.get(position).imageView.setVisibility(View.GONE);
        mHolder.get(position).playerView.setVisibility(View.VISIBLE);
        return showPlayerView;
    }

    public NotProgressVideoPlayer getVideoView(Context context,int position, String url){
        if(getViewHolder() == null || mHolder == null || position > mHolder.size()-1){
            return null;
        }
//        int index = position <= 0?mHolder.size()-1:position-1;
        NotProgressVideoPlayer history_video_view = mHolder.get(position).videoView;
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        GlideUtil.loadImageDefault(context, url, imageView);
        history_video_view.setThumbImageView(imageView);
        history_video_view.setUp(url, true, "");

        history_video_view.startPlayLogic();
        mHolder.get(position).imageView.setVisibility(View.GONE);
        mHolder.get(position).videoView.setVisibility(View.VISIBLE);
        return history_video_view;
    }

    public void setmHolder(int position){
        if(getViewHolder() == null || mHolder.size()-1<position){
            return;
        }
        for(BannerRoundLiveImageHolder b : mHolder){
            if(getViewHolder() == b){
                return;
            }
        }
        mHolder.set(position,getViewHolder());
    }

}