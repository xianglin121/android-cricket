package com.onecric.live.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.onecric.live.R;
import com.tencent.liteav.demo.superplayer.LivePlayerView;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public abstract class BannerGameLiveImageAdapter<T> extends BannerAdapter<T, BannerGameLiveImageAdapter.BannerRoundLiveImageHolder> {
    public BannerGameLiveImageAdapter(List<T> mData) {
        super(mData);
    }

    @Override
    public BannerGameLiveImageAdapter.BannerRoundLiveImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner_game, parent, false);
        return new BannerGameLiveImageAdapter.BannerRoundLiveImageHolder(view);
    }

    public class BannerRoundLiveImageHolder extends RecyclerView.ViewHolder {
        public RoundedImageView imageView;
        public ImageView iv_live_status,iv_avatar;
        public TextView tv_eyes_num,tv_title,tv_bottom;
        public LivePlayerView playerView;
        public BannerRoundLiveImageHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.iv_bg);
            this.iv_live_status = itemView.findViewById(R.id.iv_live_status);
            this.tv_eyes_num = itemView.findViewById(R.id.tv_eyes_num);
            this.iv_avatar = itemView.findViewById(R.id.iv_avatar);
            this.tv_title = itemView.findViewById(R.id.tv_title);
            this.tv_bottom = itemView.findViewById(R.id.tv_bottom);
            this.playerView = itemView.findViewById(R.id.playerView);

        }
    }
}