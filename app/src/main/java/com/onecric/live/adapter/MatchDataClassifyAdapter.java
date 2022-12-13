package com.onecric.live.adapter;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.coorchice.library.SuperTextView;
import com.onecric.live.R;
import com.onecric.live.model.MatchDataClassifyBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/23
 */
public class MatchDataClassifyAdapter extends BaseQuickAdapter<MatchDataClassifyBean, BaseViewHolder> {
    public MatchDataClassifyAdapter(int layoutResId, @Nullable List<MatchDataClassifyBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MatchDataClassifyBean item) {
        SuperTextView tv_name = helper.getView(R.id.tv_name);
        if (!TextUtils.isEmpty(item.getName_zh())) {
            helper.setText(R.id.tv_name, item.getName_zh());
        }else {
            helper.setText(R.id.tv_name, "");
        }
        if (item.isSelect()) {
            tv_name.setSolid(mContext.getResources().getColor(R.color.c_FFF1E0));
            tv_name.setTextColor(mContext.getResources().getColor(R.color.c_E3AC72));
        }else {
            tv_name.setSolid(mContext.getResources().getColor(R.color.transparent));
            tv_name.setTextColor(mContext.getResources().getColor(R.color.c_666666));
        }
    }
}
