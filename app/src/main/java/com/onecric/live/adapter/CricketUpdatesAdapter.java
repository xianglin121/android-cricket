package com.onecric.live.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.UpdatesBean;
import com.onecric.live.util.GlideUtil;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/9/5
 */
public class CricketUpdatesAdapter extends BaseMultiItemQuickAdapter<UpdatesBean, BaseViewHolder> {

    public CricketUpdatesAdapter(List<UpdatesBean> data) {
        super(data);
        addItemType(UpdatesBean.HEAD, R.layout.item_cricket_updates_head);
        addItemType(UpdatesBean.CONTENT, R.layout.item_cricket_updates_content);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, UpdatesBean item) {
        ImageView iv_cover = helper.getView(R.id.iv_cover);
        GlideUtil.loadUpdatesImageDefault(mContext, item.getImg(), iv_cover);
        if (!TextUtils.isEmpty(item.getTitle())) {
            helper.setText(R.id.tv_title, item.getTitle());
        }else {
            helper.setText(R.id.tv_title, "");
        }
        String str = "";
        if (!TextUtils.isEmpty(item.getPlayer())) {
            str = str + item.getPlayer() + " • ";
        }
        if (!TextUtils.isEmpty(item.getAddtime())) {
            str = str + item.getAddtime();
        }
        helper.setText(R.id.tv_date, str);
    }
}
