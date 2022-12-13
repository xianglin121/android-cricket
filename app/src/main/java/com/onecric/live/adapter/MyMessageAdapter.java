package com.onecric.live.adapter;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.MessageBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class MyMessageAdapter extends BaseQuickAdapter<MessageBean, BaseViewHolder> {
    public MyMessageAdapter(int layoutResId, @Nullable List<MessageBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MessageBean item) {
        if (!TextUtils.isEmpty(item.getAddtimeStr())) {
            helper.setText(R.id.tv_time, item.getAddtimeStr());
        }else {
            helper.setText(R.id.tv_time, "");
        }
        if (!TextUtils.isEmpty(item.getContent())) {
            helper.setText(R.id.tv_content, item.getContent());
        }else {
            helper.setText(R.id.tv_content, "");
        }
    }
}
