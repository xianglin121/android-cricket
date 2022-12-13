package com.onecric.live.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.CricketPointsBean;
import com.onecric.live.model.CricketPointsInnerBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/27
 */
public class CricketPointsAdapter extends BaseQuickAdapter<CricketPointsBean, BaseViewHolder> {
    public CricketPointsAdapter(int layoutResId, @Nullable List<CricketPointsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CricketPointsBean item) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            ((ViewGroup)helper.getView(R.id.ll_root)).getLayoutTransition()
//                    .enableTransitionType(LayoutTransition.CHANGE_APPEARING);
//        }
        helper.setText(R.id.tv_number, String.valueOf(helper.getLayoutPosition()+1));
        if (!TextUtils.isEmpty(item.getCompetitor())) {
            helper.setText(R.id.tv_name, item.getCompetitor());
        }else {
            helper.setText(R.id.tv_name, "");
        }
        helper.setText(R.id.tv_p, String.valueOf(item.getP()));
        helper.setText(R.id.tv_w, String.valueOf(item.getW()));
        helper.setText(R.id.tv_l, String.valueOf(item.getL()));
        helper.setText(R.id.tv_nr, String.valueOf(item.getNr()));
        helper.setText(R.id.tv_nrr, String.valueOf(item.getNrr()));
        helper.setText(R.id.tv_rts, String.valueOf(item.getPts()));

        RecyclerView rv_inner = helper.getView(R.id.rv_inner);
        List<CricketPointsInnerBean> matches = item.getMatch();
        if (matches == null) {
            matches = new ArrayList<>();
        }
        CricketPointsInnerAdapter innerAdapter = new CricketPointsInnerAdapter(R.layout.item_cricket_points_inner, matches);
        rv_inner.setLayoutManager(new LinearLayoutManager(mContext));
        rv_inner.setAdapter(innerAdapter);
        if (item.isExpand()) {
            helper.getView(R.id.ll_bottom).setVisibility(View.VISIBLE);
            helper.getView(R.id.ll_top).setBackgroundColor(mContext.getResources().getColor(R.color.c_1D2550));
            helper.getView(R.id.iv_arrow).setBackgroundResource(R.mipmap.icon_arrow_up_two);
            helper.setTextColor(R.id.tv_name, Color.WHITE);
            helper.setTextColor(R.id.tv_rts, Color.WHITE);
        }else {
            helper.getView(R.id.ll_bottom).setVisibility(View.GONE);
            helper.getView(R.id.ll_top).setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
            helper.getView(R.id.iv_arrow).setBackgroundResource(R.mipmap.icon_arrow_down_four);
            helper.setTextColor(R.id.tv_name, mContext.getResources().getColor(R.color.c_333333));
            helper.setTextColor(R.id.tv_rts, mContext.getResources().getColor(R.color.c_333333));
        }
    }
}
