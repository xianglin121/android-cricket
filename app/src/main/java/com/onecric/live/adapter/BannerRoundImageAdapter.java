package com.onecric.live.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.onecric.live.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public abstract class BannerRoundImageAdapter<T> extends BannerAdapter<T, BannerRoundImageAdapter.BannerRoundImageHolder> {

    public BannerRoundImageAdapter(List<T> mData) {
        super(mData);
    }

    @Override
    public BannerRoundImageAdapter.BannerRoundImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner_headline, parent, false);
//        RoundedImageView imageView = new RoundedImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT);
//        imageView.setLayoutParams(params);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setCornerRadius(DpUtil.dp2px(6));
        return new BannerRoundImageAdapter.BannerRoundImageHolder(view);
    }

    public class BannerRoundImageHolder extends RecyclerView.ViewHolder {
        public RoundedImageView imageView;
        public TextView textView;

        public BannerRoundImageHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.iv_bg);
            this.textView = itemView.findViewById(R.id.tv_title);
        }
    }
}