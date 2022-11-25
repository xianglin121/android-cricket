package com.longya.live.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;

import net.lucode.hackware.magicindicator.NavigatorHelper;

import java.util.Arrays;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/23
 */
public class LiveReplyAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public LiveReplyAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        if (helper.getLayoutPosition() == 0) {
            helper.getView(R.id.line_one).setVisibility(View.VISIBLE);
            helper.getView(R.id.line_two).setVisibility(View.INVISIBLE);
        }else {
            helper.getView(R.id.line_one).setVisibility(View.INVISIBLE);
            helper.getView(R.id.line_two).setVisibility(View.VISIBLE);
        }
        RecyclerView rv_reply_inner = helper.getView(R.id.rv_reply_inner);
        LiveReplyInnerAdapter innerAdapter = new LiveReplyInnerAdapter(R.layout.item_live_reply_inner, Arrays.asList("", "", ""));
        rv_reply_inner.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rv_reply_inner.setAdapter(innerAdapter);
    }
}
