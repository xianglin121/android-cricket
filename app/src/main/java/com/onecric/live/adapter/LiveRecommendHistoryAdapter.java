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
import com.onecric.live.model.HistoryLiveBean;
import com.onecric.live.util.GlideUtil;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LiveRecommendHistoryAdapter extends BaseQuickAdapter<HistoryLiveBean, BaseViewHolder> {
    private SimpleDateFormat sfdate2 = new SimpleDateFormat("hh:mm a,dd MMM", Locale.ENGLISH);
    public LiveRecommendHistoryAdapter(int layoutResId, @Nullable List<HistoryLiveBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, HistoryLiveBean item) {
        TextView tv_title = helper.getView(R.id.tv_title);
        ImageView iv_cover = helper.getView(R.id.iv_cover);

        ImageView iv_home = helper.getView(R.id.iv_home_logo);
        ImageView iv_away = helper.getView(R.id.iv_away_logo);
        int width = UIUtil.getScreenWidth(mContext);
        android.view.ViewGroup.LayoutParams pp = iv_cover.getLayoutParams();
        pp.height = (int)((width) * 0.447);
        iv_cover.setLayoutParams(pp);

        if(!TextUtils.isEmpty(item.away_logo) && !TextUtils.isEmpty(item.home_logo)){
            GlideUtil.loadLiveImageDefault(mContext, item.bottom, iv_cover);
            iv_home.setVisibility(View.VISIBLE);
            iv_away.setVisibility(View.VISIBLE);
            GlideUtil.loadTeamCircleImageDefault(mContext, item.away_logo, iv_home);
            GlideUtil.loadTeamCircleImageDefault(mContext, item.away_logo, iv_away);
        }else{
            iv_home.setVisibility(View.GONE);
            iv_away.setVisibility(View.GONE);
            GlideUtil.loadLiveImageDefault(mContext, item.bottom, iv_cover);
        }

        if (!TextUtils.isEmpty(item.getName())) {
            tv_title.setText(item.getName());
        }else {
            tv_title.setText("");
        }

        try{
            helper.setText(R.id.tv_time,sfdate2.format(new Date(item.getStart_time()*1000))+"");
        }catch (Exception e){
            helper.setText(R.id.tv_time,"");
        }

        /*ImageView iv_cover = helper.getView(R.id.iv_cover);
        ImageView iv_avatar = helper.getView(R.id.iv_avatar);
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_num = helper.getView(R.id.tv_num);
//        Glide.with(mContext).load(getFirstBitmap(mContext,item.getMediaUrl(),false)).into(iv_cover);
        ImageView iv_home = helper.getView(R.id.iv_home_logo);
        ImageView iv_away = helper.getView(R.id.iv_away_logo);

        if(!TextUtils.isEmpty(item.away_logo) && !TextUtils.isEmpty(item.home_logo)){
            GlideUtil.loadLiveImageDefault(mContext, item.bottom, iv_cover);
            iv_home.setVisibility(View.VISIBLE);
            iv_away.setVisibility(View.VISIBLE);
            GlideUtil.loadTeamCircleImageDefault(mContext, item.home_logo, iv_home);
            GlideUtil.loadTeamCircleImageDefault(mContext, item.away_logo, iv_away);
        }else{
            iv_home.setVisibility(View.GONE);
            iv_away.setVisibility(View.GONE);
            Glide.with(mContext).load(item.getImg()).placeholder(R.mipmap.bg_team_comparison_head).into(iv_cover);
        }

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

        try{
            helper.setText(R.id.tv_time,sfdate2.format(new Date(item.getStart_time()*1000))+"");
        }catch (Exception e){
            helper.setText(R.id.tv_time,"");
        }*/

    }

}
