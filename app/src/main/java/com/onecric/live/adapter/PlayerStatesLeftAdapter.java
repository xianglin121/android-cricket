package com.onecric.live.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/27
 */
public class PlayerStatesLeftAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public PlayerStatesLeftAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        if (helper.getLayoutPosition() % 2 == 0) {
            helper.getView(R.id.ll_bg).setBackgroundResource(R.mipmap.bg_table_header_one);
        }else {
            helper.getView(R.id.ll_bg).setBackgroundResource(R.mipmap.bg_table_header_two);
        }
    }
}
