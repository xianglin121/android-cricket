package com.onecric.live.adapter;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.RankingUserBean;
import com.onecric.live.util.GlideUtil;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/23
 */
public class RankingAdapter extends BaseQuickAdapter<RankingUserBean, BaseViewHolder> {
    public RankingAdapter(int layoutResId, @Nullable List<RankingUserBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, RankingUserBean item) {
        TextView tv_position = helper.getView(R.id.tv_position);
        tv_position.setText(String.valueOf(helper.getLayoutPosition() + 4));
        ImageView iv_avatar = helper.getView(R.id.iv_avatar);
        GlideUtil.loadUserImageDefault(mContext, item.getAvatar(), iv_avatar);
        ImageView iv_level = helper.getView(R.id.iv_level);
        if (iv_level != null) {
            GlideUtil.loadImageDefault(mContext, item.getVotestotal_icon(), iv_level);
            if (!TextUtils.isEmpty(item.getAmount())) {
                helper.setText(R.id.tv_popular, "人气" + item.getAmount());
            }else {
                helper.setText(R.id.tv_popular, "");
            }
        }else {
            if (!TextUtils.isEmpty(item.getAmount())) {
                helper.setText(R.id.tv_popular, "壕气" + item.getAmount());
            }else {
                helper.setText(R.id.tv_popular, "");
            }
        }
        if (!TextUtils.isEmpty(item.getUser_nickname())) {
            helper.setText(R.id.tv_name, item.getUser_nickname());
        }else {
            helper.setText(R.id.tv_name, "");
        }
        ImageView iv_state = helper.getView(R.id.iv_state);
        if (item.getIs_live() == 1) {
            iv_state.setBackgroundResource(R.mipmap.icon_state_living);
        }else {
            iv_state.setBackgroundResource(R.drawable.selector_headline_user_follow);
            if (item.getIs_watchlist() == 1) {
                iv_state.setSelected(true);
            }else {
                iv_state.setSelected(false);
            }
        }
        helper.addOnClickListener(R.id.iv_state);
    }
}
