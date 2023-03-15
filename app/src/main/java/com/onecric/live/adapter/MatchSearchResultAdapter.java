package com.onecric.live.adapter;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.MatchSearchBean;

import java.util.List;

public class MatchSearchResultAdapter extends BaseQuickAdapter<MatchSearchBean, BaseViewHolder> {
    public MatchSearchResultAdapter(int layoutResId, @Nullable List<MatchSearchBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MatchSearchBean item) {
        helper.setText(R.id.tv_left, TextUtils.isEmpty(item.title)?item.content:item.title);
        helper.setText(R.id.tv_bottom,TextUtils.isEmpty(item.name)?item.content:item.name);
        if(helper.getView(R.id.tv_status) != null){
            if(item.status == 0){
                helper.setText(R.id.tv_status,"UPCOMING");
            }else if(item.status == 1){
                helper.setText(R.id.tv_status,"LIVE");
            }else{
                helper.setText(R.id.tv_status,"COMPLETED");
            }

        }

    }
}
