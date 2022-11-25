package com.longya.live.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class MyLevelAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public MyLevelAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        ImageView iv_icon = helper.getView(R.id.iv_icon);
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_content = helper.getView(R.id.tv_content);
        if (helper.getLayoutPosition() == 0) {
            iv_icon.setImageResource(R.mipmap.icon_my_level_one);
            tv_title.setText("赠送礼物");
            tv_content.setText("狂刷礼物 飙升经验");
        }else if (helper.getLayoutPosition() == 1) {
            iv_icon.setImageResource(R.mipmap.icon_my_level_two);
            tv_title.setText("充值龙钻");
            tv_content.setText("充值不停 快感不止");
        }else if (helper.getLayoutPosition() == 2) {
            iv_icon.setImageResource(R.mipmap.icon_my_level_three);
            tv_title.setText("购买贵族");
            tv_content.setText("贵族尊享 经验加成");
        }else if (helper.getLayoutPosition() == 3) {
            iv_icon.setImageResource(R.mipmap.icon_my_level_four);
            tv_title.setText("贵族礼物");
            tv_content.setText("龙钻礼物 增值经验");
        }else if (helper.getLayoutPosition() == 4) {
            iv_icon.setImageResource(R.mipmap.icon_my_level_five);
            tv_title.setText("分享邀请");
            tv_content.setText("分享直播 经验不停");
        }else if (helper.getLayoutPosition() == 5) {
            iv_icon.setImageResource(R.mipmap.icon_my_level_six);
            tv_title.setText("活动任务");
            tv_content.setText("活动专享 经验翻倍");
        }
    }
}
