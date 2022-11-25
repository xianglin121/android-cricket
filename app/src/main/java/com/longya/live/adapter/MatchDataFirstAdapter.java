package com.longya.live.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.coorchice.library.SuperTextView;
import com.longya.live.R;
import com.longya.live.model.MatchDataClassifyBean;
import com.longya.live.model.MatchDataFirstBean;
import com.longya.live.util.GlideUtil;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/23
 */
public class MatchDataFirstAdapter extends BaseQuickAdapter<MatchDataFirstBean, BaseViewHolder> {
    public boolean mIsEdit;

    public MatchDataFirstAdapter(int layoutResId, @Nullable List<MatchDataFirstBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MatchDataFirstBean item) {
        if (!TextUtils.isEmpty(item.getName_zh())) {
            helper.setText(R.id.tv_name, item.getName_zh());
        }else {
            helper.setText(R.id.tv_name, "");
        }
        ImageView iv_logo = helper.getView(R.id.iv_logo);
        GlideUtil.loadTeamImageDefault(mContext, item.getLogo(), iv_logo);
        ImageView iv_arrow = helper.getView(R.id.iv_arrow);
        RecyclerView rv_data = helper.getView(R.id.rv_data);
        if (item.isExpand()) {
            iv_arrow.setBackgroundResource(R.mipmap.icon_arrow_up);
            rv_data.setVisibility(View.VISIBLE);
        }else {
            iv_arrow.setBackgroundResource(R.mipmap.icon_arrow_down_two);
            rv_data.setVisibility(View.GONE);
        }
        if (item.getList() != null) {
            rv_data.setLayoutManager(new LinearLayoutManager(mContext));
            MatchDataSecondAdapter secondAdapter = new MatchDataSecondAdapter(R.layout.item_match_data_second, item.getList());
            secondAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (onChildClickListener != null) {
                        onChildClickListener.onClick(helper.getLayoutPosition(), position);
                    }
                }
            });
            secondAdapter.mIsEdit = mIsEdit;
            rv_data.setAdapter(secondAdapter);
        }
    }

    public void setOnChildClickListener(OnChildClickListener onChildClickListener) {
        this.onChildClickListener = onChildClickListener;
    }

    private OnChildClickListener onChildClickListener;

    public interface OnChildClickListener {
        void onClick(int parentPosition, int childPosition);
    }
}
