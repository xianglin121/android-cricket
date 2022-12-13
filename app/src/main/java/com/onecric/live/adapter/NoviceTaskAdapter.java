package com.onecric.live.adapter;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.TaskBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/1/11
 */
public class NoviceTaskAdapter extends BaseQuickAdapter<TaskBean, BaseViewHolder> {
    public NoviceTaskAdapter(int layoutResId, @Nullable List<TaskBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, TaskBean item) {
        TextView tv_name = helper.getView(R.id.tv_name);
        if (!TextUtils.isEmpty(item.getTask())) {
            String task = item.getTask() + "(";
            String nowValue = String.valueOf(item.getNow_value());
            String count = nowValue + "/" + item.getValue() + ")";
            SpannableStringBuilder builder = new SpannableStringBuilder(task + count);
            builder.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.c_E3AC72)), task.length(), task.length()+nowValue.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            tv_name.setText(builder);
        }else {
            tv_name.setText("");
        }
        helper.setText(R.id.tv_count, "+" + item.getLz_num() + mContext.getString(R.string.diamond));
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
