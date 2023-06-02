package com.onecric.live.adapter;

import static com.onecric.live.util.DateUtil.getRelativeLocalDate;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.LiveFiltrateBean;
import com.onecric.live.util.GlideUtil;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class LiveFiltrateAdapter extends BaseQuickAdapter<LiveFiltrateBean, BaseViewHolder> {
    private SimpleDateFormat sfdate2 = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);

    public LiveFiltrateAdapter(int layoutResId, @Nullable List<LiveFiltrateBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, LiveFiltrateBean item) {
        ImageView iv_cover = helper.getView(R.id.iv_cover);
        ImageView iv_avatar = helper.getView(R.id.iv_avatar);
        ImageView iv_live = helper.getView(R.id.iv_live);
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_num = helper.getView(R.id.tv_num);
        TextView tv_time = helper.getView(R.id.tv_time);
        ImageView iv_hot = helper.getView(R.id.iv_hot);

        ImageView iv_home = helper.getView(R.id.iv_home_logo);
        ImageView iv_away = helper.getView(R.id.iv_away_logo);

        if(!TextUtils.isEmpty(item.getAwayLogo()) && !TextUtils.isEmpty(item.getHomeLogo())){
            GlideUtil.loadLiveImageDefault(mContext, item.getBottom(), iv_cover);
            iv_home.setVisibility(View.VISIBLE);
            iv_away.setVisibility(View.VISIBLE);
            GlideUtil.loadTeamCircleImageDefault(mContext, item.getHomeLogo(), iv_home);
            GlideUtil.loadTeamCircleImageDefault(mContext, item.getAwayLogo(), iv_away);
        }else{
            iv_home.setVisibility(View.GONE);
            iv_away.setVisibility(View.GONE);
            GlideUtil.loadLiveImageDefault(mContext, item.getThumb(), iv_cover);
        }

        GlideUtil.loadUserImageDefault(mContext, item.getAvatar(), iv_avatar);
        if (!TextUtils.isEmpty(item.getTitle())) {
            tv_title.setText(item.getTitle());
        }else {
            tv_title.setText("");
        }
        if (!TextUtils.isEmpty(item.getUserNickname())) {
            tv_name.setText(item.getUserNickname());
        }else {
            tv_name.setText("");
        }

        tv_num.setText(item.getHeat() > 1000 ? String.format("%.1f",(float)item.getHeat()/1000) + "K" :item.getHeat()+"");
        if(item.getIslive() == 0){
            iv_live.setVisibility(View.GONE);
            tv_time.setVisibility(View.VISIBLE);
//            iv_cover.setColorFilter(Color.parseColor("#40000000"));
            tv_time.setText(mContext.getString(R.string.watch_live_at)+" "+getRelativeLocalDate(sfdate2,item.getStarttime()));
            iv_hot.setVisibility(View.GONE);
            tv_num.setVisibility(View.GONE);
        }else{
            iv_cover.setColorFilter(null);
            iv_live.setVisibility(View.VISIBLE);
            tv_time.setVisibility(View.GONE);
            iv_hot.setVisibility(View.VISIBLE);
            tv_num.setVisibility(View.VISIBLE);
        }
    }

}
