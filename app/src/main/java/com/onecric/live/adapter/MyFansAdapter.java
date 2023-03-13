package com.onecric.live.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.activity.PersonalHomepageActivity;
import com.onecric.live.custom.ButtonFollowView;
import com.onecric.live.model.UserBean;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.view.MvpActivity;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class MyFansAdapter extends BaseQuickAdapter<UserBean, BaseViewHolder> {
    public MyFansAdapter(int layoutResId, @Nullable List<UserBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, UserBean item) {
        ImageView iv_avatar = helper.getView(R.id.iv_avatar);
        iv_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!((MvpActivity) mContext).isFastDoubleClick())
                PersonalHomepageActivity.forward(mContext, item.getUid() + "");
            }
        });
        GlideUtil.loadUserImageDefault(mContext, item.getAvatar(), iv_avatar);
        int is_anchor = item.getIs_anchor();
        if (is_anchor != 1) {
            helper.getView(R.id.iv_live).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.iv_live).setVisibility(View.VISIBLE);
        }
        if (item.getIs_live() == 0) {
            helper.getView(R.id.iv_live).setSelected(false);
        } else {
            helper.getView(R.id.iv_live).setSelected(true);
        }
        if (!TextUtils.isEmpty(item.getUser_nickname())) {
            helper.setText(R.id.tv_name, item.getUser_nickname());
        } else {
            helper.setText(R.id.tv_name, "");
        }
        if (!TextUtils.isEmpty(item.getOnlineTime())) {
            helper.setText(R.id.tv_online_time, item.getOnlineTime());
        } else {
            helper.setText(R.id.tv_online_time, "");
        }
        ImageView iv_level = helper.getView(R.id.iv_level);
        GlideUtil.loadImageDefault(mContext, item.getExp_icon(), iv_level);
        helper.setText(R.id.tv_fans_count, mContext.getString(R.string.fans) + item.getAttention());
        ButtonFollowView buttonFollowView = helper.getView(R.id.iv_follow);
        if (item.isIs_attention() == 1)
            buttonFollowView.setFollow(true);
        else {
            buttonFollowView.setFollow(false);
        }
        helper.addOnClickListener(R.id.iv_follow);
    }
}
