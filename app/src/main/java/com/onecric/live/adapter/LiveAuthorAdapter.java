package com.onecric.live.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.LiveAuthorBean;
import com.onecric.live.util.GlideUtil;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class LiveAuthorAdapter extends BaseQuickAdapter<LiveAuthorBean, BaseViewHolder> {
    private List<LiveAuthorBean> list;
    public LiveAuthorAdapter(int layoutResId, @Nullable List<LiveAuthorBean> data) {
        super(layoutResId, data);
    }

    @Override
    public void setNewData(@Nullable List<LiveAuthorBean> data) {
        super.setNewData(data);
        list = data;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, LiveAuthorBean item) {
        ImageView iv_avatar = helper.getView(R.id.iv_avatar);
        TextView tv_author_name = helper.getView(R.id.tv_author_name);
        GlideUtil.loadUserImageDefault(mContext, item.avatar, iv_avatar);
        tv_author_name.setText(item.userNickname);
        iv_avatar.setSelected(item.isSelected);
        helper.getView(R.id.iv_live_tab).setSelected(item.isSelected);
    }

    public void refreshStatus(int id){
        for(LiveAuthorBean bean:list){
            if(bean.isSelected && bean.id!=id){
                bean.isSelected = false;
            }
        }
        notifyDataSetChanged();
    }
}
