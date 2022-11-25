package com.longya.live.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.model.ColorMsgBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/23
 */
public class ColorDanmuAdapter extends BaseQuickAdapter<ColorMsgBean, BaseViewHolder> {
    public ColorDanmuAdapter(int layoutResId, @Nullable List<ColorMsgBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ColorMsgBean item) {
        ImageView iv_color = helper.getView(R.id.iv_color);
        TextView tv_name = helper.getView(R.id.tv_name);
        ImageView iv_indicator = helper.getView(R.id.iv_indicator);
        if (!TextUtils.isEmpty(item.getColor())) {
            iv_color.setColorFilter(Color.parseColor(item.getColor()));
        }else {
            iv_color.setColorFilter(Color.parseColor("#1A8FFA"));
        }
        if (!TextUtils.isEmpty(item.getName())) {
            tv_name.setText(item.getName());
        }else {
            tv_name.setText("");
        }
        iv_indicator.setVisibility(item.isSelect()? View.VISIBLE:View.INVISIBLE);
    }
}
