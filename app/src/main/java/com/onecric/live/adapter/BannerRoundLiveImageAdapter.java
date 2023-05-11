package com.onecric.live.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.onecric.live.R;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public abstract class BannerRoundLiveImageAdapter<T> extends BannerAdapter<T, BannerRoundLiveImageAdapter.BannerRoundLiveImageHolder> {

    public BannerRoundLiveImageAdapter(List<T> mData) {
        super(mData);
    }

    @Override
    public BannerRoundLiveImageAdapter.BannerRoundLiveImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner_live, parent, false);
//        RoundedImageView imageView = new RoundedImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT);
//        imageView.setLayoutParams(params);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setCornerRadius(DpUtil.dp2px(6));
        return new BannerRoundLiveImageAdapter.BannerRoundLiveImageHolder(view);
    }

    public class BannerRoundLiveImageHolder extends RecyclerView.ViewHolder {
        public RoundedImageView imageView;
        public TextView textView;

        public BannerRoundLiveImageHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.iv_bg);
            this.textView = itemView.findViewById(R.id.tv_title);
        }
    }
}