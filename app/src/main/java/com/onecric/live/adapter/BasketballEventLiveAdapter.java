package com.onecric.live.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.BasketballEventBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class BasketballEventLiveAdapter extends BaseQuickAdapter<List<BasketballEventBean>, BaseViewHolder> {
    public BasketballEventLiveAdapter(int layoutResId, @Nullable List<List<BasketballEventBean>> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, List<BasketballEventBean> item) {
        RecyclerView rv_part = helper.getView(R.id.rv_part);
        BasketballEventLiveInnerAdapter innerAdapter = new BasketballEventLiveInnerAdapter(R.layout.item_basketball_event_live_inner, item);
        rv_part.setLayoutManager(new LinearLayoutManager(mContext));
        rv_part.setAdapter(innerAdapter);
        helper.getView(R.id.cl_head).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.get(0).setHide(!item.get(0).isHide());
                if (item.get(0).isHide()) {
                    rv_part.setVisibility(View.GONE);
                }else {
                    rv_part.setVisibility(View.VISIBLE);
                }
            }
        });
        switch (getItemCount()-helper.getLayoutPosition()) {
            case 4:
                helper.setText(R.id.tv_part, "第四节");
                break;
            case 3:
                helper.setText(R.id.tv_part, "第三节");
                break;
            case 2:
                helper.setText(R.id.tv_part, "第二节");
                break;
            case 1:
                helper.setText(R.id.tv_part, "第一节");
                break;
        }
    }
}
