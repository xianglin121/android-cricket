package com.onecric.live.adapter;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.ThemeClassifyBean;
import com.onecric.live.util.GlideUtil;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class PersonalPostThemeAdapter extends BaseQuickAdapter<ThemeClassifyBean, BaseViewHolder> {
    public PersonalPostThemeAdapter(int layoutResId, @Nullable List<ThemeClassifyBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ThemeClassifyBean item) {
        TextView tv_name = helper.getView(R.id.tv_name);
//        ImageView iv_cover = helper.getView(R.id.iv_cover);
//        GlideUtil.loadImageDefault(mContext, item.getIcon(), iv_cover);
        if (item.isSelected()) {
            tv_name.setSelected(true);
        } else {
            tv_name.setSelected(false);
        }
        if (!TextUtils.isEmpty(item.getName())) {
            helper.setText(R.id.tv_name, item.getName());
        } else {
            helper.setText(R.id.tv_name, "");
        }
    }
}
