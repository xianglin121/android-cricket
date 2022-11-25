package com.longya.live.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.model.CricketPlayerBean;
import com.longya.live.model.CricketTeamBean;
import com.longya.live.util.GlideUtil;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/27
 */
public class CricketTeamsSecondAdapter extends BaseQuickAdapter<CricketPlayerBean, BaseViewHolder> {
    public CricketTeamsSecondAdapter(int layoutResId, @Nullable List<CricketPlayerBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CricketPlayerBean item) {
        ImageView iv_logo = helper.getView(R.id.iv_logo);
        GlideUtil.loadUserImageDefault(mContext, item.getPlayers_logo(), iv_logo);
        if (!TextUtils.isEmpty(item.getPlayers_name())) {
            helper.setText(R.id.tv_name, item.getPlayers_name());
        }else {
            helper.setText(R.id.tv_name, "");
        }
        if (!TextUtils.isEmpty(item.getType())) {
            if ("batsman".equals(item.getType())) {
                if (!TextUtils.isEmpty(item.getBatting_style())) {
                    helper.setText(R.id.tv_abbreviation, item.getBatting_style());
                }else {
                    helper.setText(R.id.tv_abbreviation, "");
                }
            }else if ("bowler".equals(item.getType())) {
                if (!TextUtils.isEmpty(item.getBowling_style())) {
                    helper.setText(R.id.tv_abbreviation, item.getBowling_style());
                }else {
                    helper.setText(R.id.tv_abbreviation, "");
                }
            }else if ("all_rounder".equals(item.getType())) {
                String text = "";
                if (!TextUtils.isEmpty(item.getBatting_style())) {
                    text = text + item.getBatting_style() + ",";
                }
                if (!TextUtils.isEmpty(item.getBowling_style())) {
                    text = text + item.getBowling_style();
                }
                helper.setText(R.id.tv_abbreviation, text);
            }else {
                helper.setText(R.id.tv_abbreviation, "");
            }
        }else {
            helper.setText(R.id.tv_abbreviation, "");
        }
    }
}
