package com.onecric.live.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.activity.OneLogInActivity;
import com.onecric.live.activity.OneVideoDetailActivity;
import com.onecric.live.activity.VideoMoreActivity;
import com.onecric.live.model.OneVideoBean;
import com.onecric.live.util.SpUtil;
import com.onecric.live.util.ToastUtil;

import java.util.List;

public class VideoCategoryAdapter extends BaseQuickAdapter<OneVideoBean.FirstCategoryBean, BaseViewHolder> {
    public VideoCategoryAdapter(int layoutResId, @Nullable List<OneVideoBean.FirstCategoryBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, OneVideoBean.FirstCategoryBean item) {
        TextView tv_recommend_name = helper.getView(R.id.tv_recommend_name);
        TextView tv_see_all = helper.getView(R.id.tv_see_all);
        RecyclerView rv_live = helper.getView(R.id.rv_live);

        if (!TextUtils.isEmpty(item.name)) {
            tv_recommend_name.setText(item.name);
        } else {
            tv_recommend_name.setText("");
        }

        tv_see_all.setOnClickListener(v -> {
            VideoMoreActivity.forward(mContext, item.name);
        });

        if (item.list != null && item.list.size() <= 1) {
            item.list.add(new OneVideoBean.SecondCategoryBean());
        }
        VideoInnerAdapter mInnerAdapter = new VideoInnerAdapter(R.layout.item_video_inner, item.name,item.list);
        mInnerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mInnerAdapter.getItem(position).id == 0) {
                    ToastUtil.show(mContext.getString(R.string.stay_tuned_for_video));
                    return;
                }

                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken()) && SpUtil.getInstance().getBooleanValue(SpUtil.VIDEO_OVERTIME) && SpUtil.getInstance().getIntValue(SpUtil.LOGIN_REMIND) != 0) {
                    OneLogInActivity.forward(mContext);
                } else {
                    OneVideoDetailActivity.forward(mContext,mInnerAdapter.getTournament(),mInnerAdapter.getItem(position).id);
                }
            }
        });

        View inflate = LayoutInflater.from(mContext).inflate(R.layout.layout_common_empty, null, false);
        inflate.findViewById(R.id.ll_empty).setVisibility(View.VISIBLE);
        mInnerAdapter.setEmptyView(inflate);
        rv_live.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
        rv_live.setAdapter(mInnerAdapter);
    }

}
