package com.onecric.live.adapter;

import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.TaskBean;
import com.onecric.live.model.TaskInnerBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/1/11
 */
public class CreatorTaskAdapter extends BaseQuickAdapter<TaskBean, BaseViewHolder> {
    public CreatorTaskAdapter(int layoutResId, @Nullable List<TaskBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, TaskBean item) {
        TextView tv_name = helper.getView(R.id.tv_name);
        if (!TextUtils.isEmpty(item.getTask())) {
            tv_name.setText(item.getTask());
        }else {
            tv_name.setText("");
        }
        List<TaskInnerBean> list = item.getList();
        if (list == null) {
            list = new ArrayList<>();
        }
        RecyclerView recyclerView = helper.getView(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(new CreatorTaskInnerAdapter(R.layout.item_creator_task_inner, list));
        TextView tv_finish = helper.getView(R.id.tv_finish);
        if (item.getFinish_status() == 1) {
            tv_finish.setText(mContext.getString(R.string.finished));
            tv_finish.setTextColor(mContext.getResources().getColor(R.color.c_4D87390E));
            tv_finish.setBackgroundResource(R.drawable.bg_finish_task_selected);
        }else {
            tv_finish.setText(mContext.getString(R.string.to_finish));
            tv_finish.setTextColor(mContext.getResources().getColor(R.color.c_87390E));
            tv_finish.setBackgroundResource(R.drawable.bg_finish_task_unselect);
        }
        helper.addOnClickListener(R.id.tv_finish);
    }
}
