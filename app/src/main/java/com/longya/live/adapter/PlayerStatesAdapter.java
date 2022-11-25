package com.longya.live.adapter;

import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.activity.CricketDetailActivity;
import com.longya.live.custom.BottomDotLayout;
import com.longya.live.custom.ItemDecoration;

import java.util.ArrayList;
import java.util.Arrays;
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
