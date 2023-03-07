package com.onecric.live.adapter;

import static com.onecric.live.util.ToolUtil.getFirstBitmap;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.HistoryLiveBean;
import com.onecric.live.model.LiveBean;
import com.onecric.live.util.GlideUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class LiveRecommendHistoryAdapter extends BaseQuickAdapter<HistoryLiveBean, BaseViewHolder> {
    public LiveRecommendHistoryAdapter(int layoutResId, @Nullable List<HistoryLiveBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, HistoryLiveBean item) {
        ImageView iv_cover = helper.getView(R.id.iv_cover);
        ImageView iv_avatar = helper.getView(R.id.iv_avatar);
        ImageView iv_live = helper.getView(R.id.iv_history_live);
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_num = helper.getView(R.id.tv_num);
        TextView tv_time = helper.getView(R.id.tv_time);
//        Glide.with(mContext).load(getFirstBitmap(mContext,item.getMediaUrl(),false)).into(iv_cover);
        Glide.with(mContext).load(item.getImg()).placeholder(R.mipmap.bg_team_comparison_head).into(iv_cover);
        GlideUtil.loadUserImageDefault(mContext, item.getUserHead(), iv_avatar);
        if (!TextUtils.isEmpty(item.getName())) {
            tv_title.setText(item.getName());
        } else {
            tv_title.setText("");
        }
        if (!TextUtils.isEmpty(item.getUserName())) {
            tv_name.setText(item.getUserName());
        } else {
            tv_name.setText("");
        }
        tv_num.setText(item.getViewers() > 1000 ? String.format("%.1f",(float)item.getViewers()/1000) + "K" :item.getViewers()+"");
        iv_cover.setColorFilter(null);
        iv_live.setVisibility(View.VISIBLE);
    }

}
