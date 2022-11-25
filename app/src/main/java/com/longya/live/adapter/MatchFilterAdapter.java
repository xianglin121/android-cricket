package com.longya.live.adapter;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.model.FilterBean;
import com.longya.live.model.FilterInnerBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/18
 */
public class MatchFilterAdapter extends BaseQuickAdapter<FilterBean, BaseViewHolder> {
    public MatchFilterAdapter(int layoutResId, @Nullable List<FilterBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, FilterBean item) {
        if (!TextUtils.isEmpty(item.getName())) {
            helper.setText(R.id.tv_name, item.getName());
        }else {
            helper.setText(R.id.tv_name, "");
        }

        RecyclerView rv_inner = helper.getView(R.id.rv_inner);
        List<FilterInnerBean> list = item.getList();
        if (list == null) {
            list = new ArrayList<>();
        }
        MatchFilterInnerAdapter innerAdapter = new MatchFilterInnerAdapter(R.layout.item_match_filter_one_inner, list);
        innerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mOnToggleListener != null) {
                    mOnToggleListener.onToggle(innerAdapter.getItem(position).isCheck());
                }
                innerAdapter.getItem(position).setCheck(!innerAdapter.getItem(position).isCheck());
                innerAdapter.notifyItemChanged(position);
            }
        });
        rv_inner.setLayoutManager(new GridLayoutManager(mContext, 3));
        rv_inner.setAdapter(innerAdapter);
    }

    private OnToggleListener mOnToggleListener;

    public void setOnToggleListener(OnToggleListener onToggleListener) {
        mOnToggleListener = onToggleListener;
    }

    public interface OnToggleListener {
        void onToggle(boolean isSelect);
    }
}
