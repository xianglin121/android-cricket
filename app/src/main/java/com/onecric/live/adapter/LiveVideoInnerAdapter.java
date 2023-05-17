package com.onecric.live.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.LiveVideoBean;
import com.onecric.live.util.GlideUtil;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class LiveVideoInnerAdapter extends BaseQuickAdapter<LiveVideoBean.LBean, BaseViewHolder> {
    private SimpleDateFormat sfdate2 = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
    public LiveVideoInnerAdapter(int layoutResId, @Nullable List<LiveVideoBean.LBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, LiveVideoBean.LBean item) {
        if(item.id == 0){
            helper.getView(R.id.cl_main).setVisibility(View.GONE);
            helper.setVisible(R.id.iv_footer,true);
            return;
        }

        if(helper.getView(R.id.cl_main) != null && helper.getView(R.id.iv_footer) != null){
            helper.getView(R.id.cl_main).setVisibility(View.VISIBLE);
            helper.getView(R.id.iv_footer).setVisibility(View.GONE);
        }

        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_info = helper.getView(R.id.tv_info);
        ImageView iv_cover = helper.getView(R.id.iv_cover);
        ImageView iv_home = helper.getView(R.id.iv_home_logo);
        ImageView iv_away = helper.getView(R.id.iv_away_logo);

        if(!TextUtils.isEmpty(item.awayLogo) && !TextUtils.isEmpty(item.homeLogo)){
            GlideUtil.loadLiveImageDefault(mContext, item.bottom, iv_cover);
            iv_home.setVisibility(View.VISIBLE);
            iv_away.setVisibility(View.VISIBLE);
            GlideUtil.loadTeamCircleImageDefault(mContext, item.homeLogo, iv_home);
            GlideUtil.loadTeamCircleImageDefault(mContext, item.awayLogo, iv_away);
        }else{
            iv_home.setVisibility(View.GONE);
            iv_away.setVisibility(View.GONE);
            GlideUtil.loadLiveImageDefault(mContext, item.thumb, iv_cover);
        }

        if (!TextUtils.isEmpty(item.title)) {
            tv_title.setText(item.title);
        }else {
            tv_title.setText("");
        }

        if (!TextUtils.isEmpty(item.homeName)) {
            tv_name.setText(item.homeName);
        }else {
            tv_name.setText("");
        }

        if (!TextUtils.isEmpty(item.tournament)) {
            tv_info.setText(item.tournament);
        } else {
            tv_info.setText("");
        }

        if(item.islive == 1){
            helper.setText(R.id.tv_status,"Live");
        }else{
            helper.setText(R.id.tv_status,"Match");
        }

    }
}
