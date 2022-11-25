package com.longya.live.adapter;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.model.LiveBean;
import com.longya.live.util.GlideUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class LiveRecommendAdapter extends BaseQuickAdapter<LiveBean, BaseViewHolder> {
    public LiveRecommendAdapter(int layoutResId, @Nullable List<LiveBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, LiveBean item) {
        ImageView iv_cover = helper.getView(R.id.iv_cover);
        ImageView iv_avatar = helper.getView(R.id.iv_avatar);
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_num = helper.getView(R.id.tv_num);

        GlideUtil.loadImageDefault(mContext, item.getThumb(), iv_cover);
        GlideUtil.loadUserImageDefault(mContext, item.getAvatar(), iv_avatar);
        if (!TextUtils.isEmpty(item.getTitle())) {
            tv_title.setText(item.getTitle());
        }else {
            tv_title.setText("");
        }
        if (!TextUtils.isEmpty(item.getUser_nickname())) {
            tv_name.setText(item.getUser_nickname());
        }else {
            tv_name.setText("");
        }
        if (item.getHeat() > 10000) {
            String heat = new BigDecimal(item.getHeat()).divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
            tv_num.setText(heat + "w");
        }else {
            tv_num.setText(String.valueOf(item.getHeat()));
        }
    }
}
