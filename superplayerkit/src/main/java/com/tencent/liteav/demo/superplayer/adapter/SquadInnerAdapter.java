package com.tencent.liteav.demo.superplayer.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tencent.liteav.demo.superplayer.R;
import com.tencent.liteav.demo.superplayer.model.SquadBean;

import java.util.List;

public class SquadInnerAdapter extends BaseQuickAdapter<SquadBean.SquadInnerBean, BaseViewHolder> {
    public SquadInnerAdapter(int layoutResId, @Nullable List<SquadBean.SquadInnerBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, SquadBean.SquadInnerBean item) {
        String t4s = String.valueOf(item.fours);
        String t6s = String.valueOf(item.sixes);
        String tsr = String.valueOf(item.strikeRate);
        helper.setText(R.id.tv_4s, "0".equals(t4s)?"-":t4s);
        helper.setText(R.id.tv_6s, "0".equals(t6s)?"-":t6s);
        helper.setText(R.id.tv_sr, "0".equals(tsr)?"-":tsr);
        helper.setText(R.id.tv_end, item.bat);
        helper.setText(R.id.tv_name, item.playerName);
        Glide.with(mContext.getApplicationContext()).load(item.playerLogo).placeholder(R.mipmap.bg_avatar_default).error(R.mipmap.bg_avatar_default).circleCrop()
                .into(((ImageView)helper.getView(R.id.iv_head)));
    }
}
