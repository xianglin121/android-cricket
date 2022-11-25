package com.longya.live.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.coorchice.library.SuperTextView;
import com.longya.live.R;
import com.longya.live.fragment.LiveChatFragment;
import com.longya.live.model.BoxBean;
import com.longya.live.model.MatchSocketBean;
import com.longya.live.util.DpUtil;
import com.longya.live.util.GlideUtil;
import com.longya.live.util.SpUtil;
import com.longya.live.util.StringUtil;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class TreasureChestAdapter extends BaseQuickAdapter<BoxBean, BaseViewHolder> {
    private LiveChatFragment mFragment;

    public TreasureChestAdapter(int layoutResId, @Nullable List<BoxBean> data, LiveChatFragment fragment) {
        super(layoutResId, data);
        mFragment = fragment;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, BoxBean item) {
        ImageView iv_box = helper.getView(R.id.iv_box);
        SuperTextView tv_text = helper.getView(R.id.tv_text);
        helper.addOnClickListener(R.id.tv_text);
        if (item.getStatus() == 1) {
            GlideUtil.loadImageDefault(mContext, item.getGift_img(), iv_box);
            if (!TextUtils.isEmpty(item.getGift_name())) {
                tv_text.setText(item.getNum() + item.getGift_name());
            }else {
                tv_text.setText("");
            }
            tv_text.setTextColor(mContext.getResources().getColor(R.color.c_333333));
            tv_text.setSolid(Color.TRANSPARENT);
            iv_box.setBackground(null);
        }else if (item.getStatus() == 2) {
            int position = mFragment.mPosition;
            long time = mFragment.mTime;
            if (position == helper.getLayoutPosition()) {
                tv_text.setText(StringUtil.getDurationText(time));
                tv_text.setTextColor(Color.WHITE);
                tv_text.setSolid(mContext.getResources().getColor(R.color.c_999999));
                tv_text.setCorner(DpUtil.dp2px(12));
                tv_text.setPadding(DpUtil.dp2px(9), DpUtil.dp2px(2), DpUtil.dp2px(9), DpUtil.dp2px(2));
            }else {
                tv_text.setText(mContext.getString(R.string.treasure_chest_text_one));
                tv_text.setTextColor(mContext.getResources().getColor(R.color.c_999999));
                tv_text.setSolid(Color.TRANSPARENT);
            }
            if (helper.getLayoutPosition() == (getItemCount()-1)) {
                iv_box.setBackgroundResource(R.drawable.selector_treasure_chest_two);
            }else {
                iv_box.setBackgroundResource(R.drawable.selector_treasure_chest);
            }
        }else {
            tv_text.setText(mContext.getString(R.string.treasure_chest_text_two));
            tv_text.setTextColor(Color.WHITE);
            tv_text.setSolid(mContext.getResources().getColor(R.color.c_E3AC72));
            tv_text.setCorner(DpUtil.dp2px(12));
            tv_text.setPadding(DpUtil.dp2px(6), DpUtil.dp2px(2), DpUtil.dp2px(6), DpUtil.dp2px(2));
            if (helper.getLayoutPosition() == (getItemCount()-1)) {
                iv_box.setBackgroundResource(R.drawable.selector_treasure_chest_two);
            }else {
                iv_box.setBackgroundResource(R.drawable.selector_treasure_chest);
            }
        }
    }

    @Override
    protected void convertPayloads(@NonNull BaseViewHolder helper, BoxBean item, @NonNull List<Object> payloads) {
        super.convertPayloads(helper, item, payloads);
        if (payloads != null && payloads.size() > 0) {
            ImageView iv_box = helper.getView(R.id.iv_box);
            SuperTextView tv_text = helper.getView(R.id.tv_text);
            if (item.getStatus() == 1) {
                GlideUtil.loadImageDefault(mContext, item.getGift_img(), iv_box);
                if (!TextUtils.isEmpty(item.getGift_name())) {
                    tv_text.setText(item.getNum() + item.getGift_name());
                }else {
                    tv_text.setText("");
                }
                tv_text.setTextColor(mContext.getResources().getColor(R.color.c_333333));
                tv_text.setSolid(Color.TRANSPARENT);
                iv_box.setBackground(null);
            }else if (item.getStatus() == 2) {
                int position = mFragment.mPosition;
                long time = mFragment.mTime;
                if (position == helper.getLayoutPosition()) {
                    tv_text.setText(StringUtil.getDurationText(time));
                    tv_text.setTextColor(Color.WHITE);
                    tv_text.setSolid(mContext.getResources().getColor(R.color.c_999999));
                    tv_text.setCorner(DpUtil.dp2px(12));
                    tv_text.setPadding(DpUtil.dp2px(9), DpUtil.dp2px(2), DpUtil.dp2px(9), DpUtil.dp2px(2));
                }else {
                    tv_text.setText(mContext.getString(R.string.treasure_chest_text_one));
                    tv_text.setTextColor(mContext.getResources().getColor(R.color.c_999999));
                    tv_text.setSolid(Color.TRANSPARENT);
                }
                if (helper.getLayoutPosition() == (getItemCount()-1)) {
                    iv_box.setBackgroundResource(R.drawable.selector_treasure_chest_two);
                }else {
                    iv_box.setBackgroundResource(R.drawable.selector_treasure_chest);
                }
            }else {
                tv_text.setText(mContext.getString(R.string.treasure_chest_text_two));
                tv_text.setTextColor(Color.WHITE);
                tv_text.setSolid(mContext.getResources().getColor(R.color.c_E3AC72));
                tv_text.setCorner(DpUtil.dp2px(12));
                tv_text.setPadding(DpUtil.dp2px(6), DpUtil.dp2px(2), DpUtil.dp2px(6), DpUtil.dp2px(2));
                if (helper.getLayoutPosition() == (getItemCount()-1)) {
                    iv_box.setBackgroundResource(R.drawable.selector_treasure_chest_two);
                }else {
                    iv_box.setBackgroundResource(R.drawable.selector_treasure_chest);
                }
            }
        }
    }
}
