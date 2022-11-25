package com.longya.live.adapter;

import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.model.TaskBean;
import com.longya.live.model.TaskInnerBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/1/11
 */
public class CreatorTaskInnerAdapter extends BaseQuickAdapter<TaskInnerBean, BaseViewHolder> {
    public CreatorTaskInnerAdapter(int layoutResId, @Nullable List<TaskInnerBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, TaskInnerBean item) {
        TextView tv_name = helper.getView(R.id.tv_name);
        if (!TextUtils.isEmpty(item.getStr())) {
            tv_name.setText(item.getStr());
        }else {
            tv_name.setText("");
        }
    }
}
