package com.tencent.liteav.demo.superplayer.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tencent.liteav.demo.superplayer.R;
import com.tencent.liteav.demo.superplayer.model.SquadBean;
import com.tencent.liteav.demo.superplayer.ui.view.VodMatchFullScreenView;

import java.util.List;

public class SquadAdapter extends BaseQuickAdapter<SquadBean, BaseViewHolder> {
    private VodMatchFullScreenView screenView;
    public SquadAdapter(VodMatchFullScreenView screenView,int layoutResId, @Nullable List<SquadBean> data) {
        super(layoutResId, data);
        this.screenView = screenView;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, SquadBean item) {
        helper.setText(R.id.tv_type, item.name);
        SquadInnerAdapter innerAdapter = new SquadInnerAdapter(R.layout.item_match_player,item.list);
        RecyclerView rv = helper.getView(R.id.rv_inner);
        rv.setLayoutManager(new LinearLayoutManager(mContext,RecyclerView.HORIZONTAL,false));
        rv.setAdapter(innerAdapter);
        innerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                screenView.forwardPlayerProfile(((SquadInnerAdapter)adapter).getItem(position).playerId);
            }
        });
    }
}
