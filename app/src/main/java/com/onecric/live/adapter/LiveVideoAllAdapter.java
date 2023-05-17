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

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class LiveVideoAllAdapter extends BaseQuickAdapter<LiveVideoBean.LBean, BaseViewHolder> {
    private SimpleDateFormat sfdate2 = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
    public LiveVideoAllAdapter(int layoutResId, @Nullable List<LiveVideoBean.LBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, LiveVideoBean.LBean item) {
        TextView tv_title = helper.getView(R.id.tv_title);
        ImageView iv_cover = helper.getView(R.id.iv_cover);

        ImageView iv_home = helper.getView(R.id.iv_home_logo);
        ImageView iv_away = helper.getView(R.id.iv_away_logo);
        int width = UIUtil.getScreenWidth(mContext);
        android.view.ViewGroup.LayoutParams pp = iv_cover.getLayoutParams();
        pp.height = (int)((width) * 0.447);
        iv_cover.setLayoutParams(pp);

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

        if (!TextUtils.isEmpty(item.tournament)) {
            helper.setText(R.id.tv_time,item.tournament+"");
        } else {
            helper.setText(R.id.tv_time,"");
        }

        if(item.islive == 1){
            helper.setText(R.id.tv_status,mContext.getString(R.string.live));
        }else{
            helper.setText(R.id.tv_status,mContext.getString(R.string.match));
        }

    }
}
