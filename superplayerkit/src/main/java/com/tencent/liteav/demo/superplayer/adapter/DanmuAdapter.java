package com.tencent.liteav.demo.superplayer.adapter;

import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tencent.liteav.demo.superplayer.R;
import com.tencent.liteav.demo.superplayer.model.DanmuBean;
import com.tencent.qcloud.tuikit.tuichat.component.face.FaceManager;

import java.util.List;

public class DanmuAdapter extends BaseQuickAdapter<DanmuBean, BaseViewHolder> {
    public DanmuAdapter(int layoutResId, @Nullable List<DanmuBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DanmuBean item) {
        switch (item.type){
            case 0:
                helper.setBackgroundRes(R.id.tv_danmu,R.drawable.bg_50black_15);
                helper.setTextColor(R.id.tv_danmu, Color.WHITE);
                break;
            case 1:
                helper.setBackgroundRes(R.id.tv_danmu,R.drawable.bg_50blue_15);
                helper.setTextColor(R.id.tv_danmu, Color.WHITE);
                break;
            case 2:
                helper.setBackgroundRes(R.id.tv_danmu,R.drawable.bg_50black_15);
                helper.setTextColor(R.id.tv_danmu, Color.parseColor("#DC3C23"));
                break;
            default:;
        }

        if (!TextUtils.isEmpty(item.context)) {
            SpannableStringBuilder msg = FaceManager.handlerEmojiText(item.context);
            helper.setText(R.id.tv_danmu, msg);
        }else {
            helper.setText(R.id.tv_danmu, "");
        }



    }
}
