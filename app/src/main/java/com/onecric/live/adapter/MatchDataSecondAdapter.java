package com.onecric.live.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.MatchDataSecondBean;
import com.onecric.live.util.GlideUtil;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/23
 */
public class MatchDataSecondAdapter extends BaseQuickAdapter<MatchDataSecondBean, BaseViewHolder> {
    public boolean mIsEdit;

    public MatchDataSecondAdapter(int layoutResId, @Nullable List<MatchDataSecondBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MatchDataSecondBean item) {
        if (!TextUtils.isEmpty(item.getName_zh())) {
            helper.setText(R.id.tv_name, item.getName_zh());
        }else {
            helper.setText(R.id.tv_name, "");
        }
        ImageView iv_logo = helper.getView(R.id.iv_logo);
        GlideUtil.loadTeamImageDefault(mContext, item.getLogo(), iv_logo);
        ImageView iv_state = helper.getView(R.id.iv_state);
        if (item.isFollow()) {
            iv_state.setBackgroundResource(R.mipmap.icon_match_data_subtract);
        }else {
            iv_state.setBackgroundResource(R.mipmap.icon_match_data_plus);
        }
        if (mIsEdit) {
            iv_state.setVisibility(View.VISIBLE);
        }else {
            iv_state.setVisibility(View.GONE);
        }
    }
}
