package com.onecric.live.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.ActivityBean;
import com.onecric.live.model.CricketFiltrateBean;
import com.onecric.live.util.DpUtil;
import com.onecric.live.util.GlideUtil;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class CricketFiltrateAdapter extends BaseQuickAdapter<CricketFiltrateBean, BaseViewHolder> {
    public CricketFiltrateAdapter(int layoutResId, @Nullable List<CricketFiltrateBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CricketFiltrateBean item) {
        helper.getView(R.id.tv_name).setSelected(item.isCheck);
        if (!TextUtils.isEmpty(item.name)) {
            helper.setText(R.id.tv_name, item.name);
        }
        helper.addOnClickListener(R.id.tv_name);
    }
}
