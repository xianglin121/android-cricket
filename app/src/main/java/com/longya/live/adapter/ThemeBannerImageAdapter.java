package com.longya.live.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.longya.live.R;
import com.longya.live.util.DpUtil;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.holder.BannerImageHolder;

import java.util.List;

/**
 * 话题banner
 */
public abstract class ThemeBannerImageAdapter<T> extends BannerAdapter<T, ThemeBannerImageAdapter.ThemeBannerImageHolder> {

    public ThemeBannerImageAdapter(List<T> mData) {
        super(mData);
    }

    @Override
    public ThemeBannerImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_theme_banner, parent, false);
        return new ThemeBannerImageHolder(itemView);
    }

    public class ThemeBannerImageHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;

        public ThemeBannerImageHolder(@NonNull View view) {
            super(view);
            this.imageView = view.findViewById(R.id.imageView);
            this.textView = view.findViewById(R.id.textView);
        }
    }
}