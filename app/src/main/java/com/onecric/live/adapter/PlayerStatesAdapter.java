package com.onecric.live.adapter;

import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.custom.BottomDotLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/27
 */
public class PlayerStatesAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public PlayerStatesAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        if (helper.getLayoutPosition() % 2 == 0) {
            helper.getView(R.id.ll_bg).setBackgroundColor(Color.WHITE);
        }else {
            helper.getView(R.id.ll_bg).setBackgroundColor(mContext.getResources().getColor(R.color.c_F0F0F0));
        }
        BottomDotLayout text_layout = helper.getView(R.id.text_layout);
        List<String> list = new ArrayList<>();
        list.add("29");
        list.add("29");
        list.add("29");
        list.add("109");
        list.add("29");
        text_layout.setData(list);
    }
}
