package com.onecric.live.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.RankingBean;
import com.onecric.live.util.GlideUtil;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/23
 */
public class LiveRankingAdapter extends BaseQuickAdapter<RankingBean, BaseViewHolder> {
    public LiveRankingAdapter(int layoutResId, @Nullable List<RankingBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, RankingBean item) {
        TextView tv_position = helper.getView(R.id.tv_position);
        if (helper.getLayoutPosition() == 0) {
            tv_position.setBackgroundResource(R.mipmap.icon_live_ranking_one);
            tv_position.setText("");
        }else if (helper.getLayoutPosition() == 1) {
            tv_position.setBackgroundResource(R.mipmap.icon_live_ranking_two);
            tv_position.setText("");
        }else if (helper.getLayoutPosition() == 2) {
            tv_position.setBackgroundResource(R.mipmap.icon_live_ranking_three);
            tv_position.setText("");
        }else {
            tv_position.setBackground(null);
            tv_position.setText(String.valueOf(helper.getLayoutPosition() + 1));
        }
        ImageView iv_avatar = helper.getView(R.id.iv_avatar);
        GlideUtil.loadUserImageDefault(mContext, item.getAvatar(), iv_avatar);
        if (!TextUtils.isEmpty(item.getUser_nickname())) {
            helper.setText(R.id.tv_name, item.getUser_nickname());
        }else {
            helper.setText(R.id.tv_name, "");
        }
        helper.setText(R.id.tv_cost, item.getAmount());
        ImageView iv_arrow = helper.getView(R.id.iv_arrow);
        if (item.getOrder() == 1) {
            iv_arrow.setVisibility(View.VISIBLE);
            iv_arrow.setBackgroundResource(R.mipmap.icon_live_ranking_up);
        }else if (item.getOrder() == 2) {
            iv_arrow.setVisibility(View.VISIBLE);
            iv_arrow.setBackgroundResource(R.mipmap.icon_live_ranking_down);
        }else {
            iv_arrow.setVisibility(View.INVISIBLE);
        }
    }
}
