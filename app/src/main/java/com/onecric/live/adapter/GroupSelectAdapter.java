package com.onecric.live.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

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
public class GroupSelectAdapter extends BaseQuickAdapter<ThemeClassifyBean, BaseViewHolder> {
    public GroupSelectAdapter(int layoutResId, @Nullable List<ThemeClassifyBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ThemeClassifyBean item) {
        ImageView iv_icon = helper.getView(R.id.iv_icon);
        GlideUtil.loadImageDefault(mContext, item.getIcon(), iv_icon);
        if (!TextUtils.isEmpty(item.getName())) {
            helper.setText(R.id.tv_name, item.getName());
        }else {
            helper.setText(R.id.tv_name, "");
        }
        if (helper.getLayoutPosition() == getItemCount()-1) {
            helper.getView(R.id.line).setVisibility(View.INVISIBLE);
        }else {
            helper.getView(R.id.line).setVisibility(View.VISIBLE);
        }
    }
}
