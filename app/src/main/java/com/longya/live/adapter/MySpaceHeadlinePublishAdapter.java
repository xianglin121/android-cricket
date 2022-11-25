package com.longya.live.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.model.HeadlineBean;
import com.longya.live.util.GlideUtil;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class MySpaceHeadlinePublishAdapter extends BaseQuickAdapter<HeadlineBean, BaseViewHolder> {
    public MySpaceHeadlinePublishAdapter(int layoutResId, @Nullable List<HeadlineBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, HeadlineBean item) {
        if (!TextUtils.isEmpty(item.getTitle())) {
            helper.setText(R.id.tv_title, item.getTitle());
        }else {
            helper.setText(R.id.tv_title, "");
        }
        ImageView iv_cover = helper.getView(R.id.iv_cover);
        GlideUtil.loadImageDefault(mContext, item.getImg(), iv_cover);
        if (item.getComment_count() > 0) {
            helper.getView(R.id.tv_comment).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_comment, String.valueOf(item.getComment_count()));
        }else {
            helper.getView(R.id.tv_comment).setVisibility(View.GONE);
        }
        if (helper.getLayoutPosition() == (getItemCount()-1)) {
            helper.getView(R.id.view_line).setVisibility(View.INVISIBLE);
        }else {
            helper.getView(R.id.view_line).setVisibility(View.VISIBLE);
        }
        helper.addOnClickListener(R.id.iv_delete);
    }
}
