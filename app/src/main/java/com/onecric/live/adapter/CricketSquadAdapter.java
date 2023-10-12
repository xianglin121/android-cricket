package com.onecric.live.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.CricketSquadBean;
import com.onecric.live.util.GlideUtil;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/31
 */
public class CricketSquadAdapter extends BaseQuickAdapter<CricketSquadBean, BaseViewHolder> {
    public CricketSquadAdapter(int layoutResId, @Nullable List<CricketSquadBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CricketSquadBean item) {
        if (TextUtils.isEmpty(item.getHome_player_name())) {
            helper.setVisible(R.id.cl_home,false);
        }else{
            helper.setVisible(R.id.cl_home,true);
            ImageView iv_home_logo = helper.getView(R.id.iv_home_logo);
            GlideUtil.loadUserImageDefault(mContext, item.getHome_player_logo(), iv_home_logo);
            helper.setText(R.id.tv_home_name, item.getHome_player_name());
            if (!TextUtils.isEmpty(item.getHome_type())) {
                helper.setText(R.id.tv_home_position, item.getHome_type());
            }else {
                helper.setText(R.id.tv_home_position, "");
            }
        }


        if (TextUtils.isEmpty(item.getAway_player_name())) {
            helper.setVisible(R.id.cl_away,false);
        }else{
            helper.setVisible(R.id.cl_away,true);
            ImageView iv_away_logo = helper.getView(R.id.iv_away_logo);
            GlideUtil.loadUserImageDefault(mContext, item.getAway_player_logo(), iv_away_logo);
            helper.setText(R.id.tv_away_name, item.getAway_player_name());
            if (!TextUtils.isEmpty(item.getAway_type())) {
                helper.setText(R.id.tv_away_position, item.getAway_type());
            }else {
                helper.setText(R.id.tv_away_position, "");
            }
        }

        helper.addOnClickListener(R.id.iv_home_logo, R.id.iv_away_logo);
    }
}
