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
import com.onecric.live.model.AnchorBean;
import com.onecric.live.util.GlideUtil;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class MyFollow2Adapter extends BaseQuickAdapter<AnchorBean, BaseViewHolder> {
    public MyFollow2Adapter(int layoutResId, @Nullable List<AnchorBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, AnchorBean item) {
        ImageView iv_avatar = helper.getView(R.id.iv_avatar);
        GlideUtil.loadUserImageDefault(mContext, item.avatar, iv_avatar);
        iv_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonalHomepageActivity.forward(mContext, item.id + "");
            }
        });
        int is_anchor = item.isAnchor;
        if (is_anchor != 1) {
            helper.getView(R.id.iv_live).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.iv_live).setVisibility(View.VISIBLE);
        }
        if (item.isLive == 0) {
            helper.getView(R.id.iv_live).setSelected(false);
        } else {
            helper.getView(R.id.iv_live).setSelected(true);
        }
        if (!TextUtils.isEmpty(item.userNickname)) {
            helper.setText(R.id.tv_name, item.userNickname);
        } else {
            helper.setText(R.id.tv_name, "");
        }
        if (!TextUtils.isEmpty(item.onlineTime)) {
            helper.setText(R.id.tv_online_time, item.onlineTime);
        } else {
            helper.setText(R.id.tv_online_time, "");
        }
        /*ImageView iv_level = helper.getView(R.id.iv_level);
        GlideUtil.loadImageDefault(mContext, item.getExp_icon(), iv_level);*/
        helper.setText(R.id.tv_fans_count, mContext.getString(R.string.fans) + item.attention);
        ButtonFollowView buttonFollowView = helper.getView(R.id.iv_follow);
        buttonFollowView.setFollow(item.isAttention==1?true:false);
        helper.addOnClickListener(R.id.iv_follow);
    }

    @Override
    protected void convertPayloads(@NonNull BaseViewHolder helper, AnchorBean item, @NonNull List<Object> payloads) {
        if (payloads != null && payloads.size() > 0) {
            ButtonFollowView iv_follow = helper.getView(R.id.iv_follow);
            iv_follow.setFollow(item.isAttention == 0?false:true);
        }
    }
}
