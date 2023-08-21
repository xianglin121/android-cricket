package com.onecric.live.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.onecric.live.R;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public abstract class BannerRoundLiveImage2Adapter<T> extends BannerAdapter<T, BannerRoundLiveImage2Adapter.BannerRoundLiveImageHolder> {

    public BannerRoundLiveImage2Adapter(List<T> mData) {
        super(mData);
    }

    @Override
    public BannerRoundLiveImage2Adapter.BannerRoundLiveImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner_img2, parent, false);
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner_live, parent, false);
//        RoundedImageView imageView = new RoundedImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT);
//        imageView.setLayoutParams(params);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setCornerRadius(DpUtil.dp2px(6));
        return new BannerRoundLiveImage2Adapter.BannerRoundLiveImageHolder(view);
    }

    public class BannerRoundLiveImageHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
//        public TextView tv_banner_btn,tv_banner_into,tv_banner_title;

        public BannerRoundLiveImageHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.iv_bg);
//            this.tv_banner_btn = itemView.findViewById(R.id.tv_banner_btn);
//            this.tv_banner_into = itemView.findViewById(R.id.tv_banner_into);
//            this.tv_banner_title = itemView.findViewById(R.id.tv_banner_title);
        }
    }
}