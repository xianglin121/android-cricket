package com.onecric.live.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.GiftBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/11/22
 */
public class GiftViewPagerRecyclerAdapter extends BaseQuickAdapter<List<GiftBean>, BaseViewHolder> {
    public GiftViewPagerRecyclerAdapter(int layoutResId, @Nullable List<List<GiftBean>> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, List<GiftBean> item) {
        RecyclerView rv_content = helper.getView(R.id.rv_content);
        GiftGridRecyclerAdapter recyclerAdapter = new GiftGridRecyclerAdapter(R.layout.item_gift_grid_recycler_layout, item);
        recyclerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (int i = 0; i < getData().size(); i++) {
                    for (int j = 0; j < getData().get(i).size(); j++) {
                        if (getData().get(i).get(j).getMark() == 0) {
                            getData().get(i).get(j).setSelect(i == helper.getLayoutPosition() && j == position);
                        }
                    }
                }
                recyclerAdapter.notifyDataSetChanged();
            }
        });
        rv_content.setLayoutManager(new GridLayoutManager(mContext, 4));
        rv_content.setAdapter(recyclerAdapter);
    }

    public GiftBean getSelectBean() {
        for (int i = 0; i < getData().size(); i++) {
            for (int j = 0; j < getData().get(i).size(); j++) {
                if (getData().get(i).get(j).isSelect()) {
                    return getData().get(i).get(j);
                }
            }
        }
        return null;
    }
}
