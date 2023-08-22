package com.onecric.live.adapter;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.GameHistoryBean;
import com.onecric.live.util.GlideUtil;
import com.tencent.qcloud.tuicore.util.DateTimeUtil;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class LiveGameAllHistoryAdapter extends BaseQuickAdapter<GameHistoryBean, BaseViewHolder> {
    private SimpleDateFormat sfdate2 = new SimpleDateFormat("hh:mm a,dd MMM", Locale.ENGLISH);
    public LiveGameAllHistoryAdapter(int layoutResId, @Nullable List<GameHistoryBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, GameHistoryBean item) {
        TextView tv_title = helper.getView(R.id.tv_title);
        ImageView iv_cover = helper.getView(R.id.iv_cover);

        int width = UIUtil.getScreenWidth(mContext);
        android.view.ViewGroup.LayoutParams pp = iv_cover.getLayoutParams();
        pp.height = (int)((width) * 0.447);
        iv_cover.setLayoutParams(pp);
        GlideUtil.loadLiveImageDefault(mContext, item.thumb, iv_cover);
        if (!TextUtils.isEmpty(item.title)) {
            tv_title.setText(item.title);
        }else {
            tv_title.setText("");
        }

        try{
            helper.setText(R.id.tv_time,sfdate2.format(DateTimeUtil.getStringToDate(item.createdAt, "yyyy-MM-dd HH:mm:ss"))+"");
        }catch (Exception e){
            helper.setText(R.id.tv_time,"");
        }

    }

}
