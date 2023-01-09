package com.onecric.live.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.HeadlineBean;
import com.onecric.live.util.GlideUtil;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class ThemeHeadlineAdapter extends BaseQuickAdapter<HeadlineBean, BaseViewHolder> {
    public ThemeHeadlineAdapter(int layoutResId, @Nullable List<HeadlineBean> data) {
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
//        GlideUtil.loadImageDefault(mContext, item.getImg(), iv_cover);
        Glide.with(mContext).load(item.getImg()).placeholder(R.mipmap.img_updates_default).into(iv_cover);
        if (item.getIs_top() == 1) {
            helper.getView(R.id.tv_top).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.tv_top).setVisibility(View.GONE);
        }

        helper.setVisible(R.id.iv_hot,false);
        helper.getView(R.id.tv_comment).setVisibility(View.GONE);
        if (item.getComment_count() > 0) {
            helper.getView(R.id.tv_comment).setVisibility(View.VISIBLE);
            //评论数>=百万时加上热门图标并用百万为单位 1M+ 2M+
            if(item.getComment_count() >= 1000000){
                helper.setVisible(R.id.iv_hot,true);
                helper.setText(R.id.tv_comment, String.valueOf( item.getComment_count()/1000000 + "M+"));
            }else{
                helper.setText(R.id.tv_comment, String.valueOf(item.getComment_count()));
            }
        }

        if (helper.getLayoutPosition() == (getItemCount()-1)) {
            helper.getView(R.id.view_line).setVisibility(View.INVISIBLE);
        }else {
            helper.getView(R.id.view_line).setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(item.getAddtime())) {
            helper.setText(R.id.tv_time, item.getAddtime());
        }else {
            helper.setText(R.id.tv_time, "");
        }
    }
}
