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
import com.onecric.live.activity.LiveDetailActivity;
import com.onecric.live.activity.LiveMoreActivity;
import com.onecric.live.activity.LiveNotStartDetailActivity;
import com.onecric.live.activity.OneLogInActivity;
import com.onecric.live.model.LiveVideoBean;
import com.onecric.live.util.SpUtil;
import com.onecric.live.util.ToastUtil;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class LiveVideoAdapter extends BaseQuickAdapter<LiveVideoBean, BaseViewHolder> {

    public LiveVideoAdapter(int layoutResId, @Nullable List<LiveVideoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, LiveVideoBean item) {
        TextView tv_recommend_name = helper.getView(R.id.tv_recommend_name);
        TextView tv_see_all = helper.getView(R.id.tv_see_all);
        RecyclerView rv_live = helper.getView(R.id.rv_live);

        if (!TextUtils.isEmpty(item.name)) {
            tv_recommend_name.setText(item.name);
        } else {
            tv_recommend_name.setText("");
        }

        tv_see_all.setOnClickListener(v -> {
            LiveMoreActivity.forward(mContext, item.name);
        });

        if (item.list != null && item.list.size() <= 1) {
            item.list.add(new LiveVideoBean.LBean());
        }
        LiveVideoInnerAdapter mInnerAdapter = new LiveVideoInnerAdapter(R.layout.item_live_video_inner, item.list);
        mInnerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mInnerAdapter.getItem(position).id == 0) {
                    ToastUtil.show(mContext.getString(R.string.stay_tuned_for_broadcasts));
                    return;
                }

                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken()) && SpUtil.getInstance().getBooleanValue(SpUtil.VIDEO_OVERTIME) && SpUtil.getInstance().getIntValue(SpUtil.LOGIN_REMIND) != 0) {
                    OneLogInActivity.forward(mContext);
                } else if (mInnerAdapter.getItem(position).islive == 0) {
                    LiveNotStartDetailActivity.forward(mContext, mInnerAdapter.getItem(position).uid,
                            mInnerAdapter.getItem(position).matchId, mInnerAdapter.getItem(position).liveId);
                } else {
                    LiveDetailActivity.forward(mContext, mInnerAdapter.getItem(position).uid,
                            mInnerAdapter.getItem(position).matchId, mInnerAdapter.getItem(position).liveId);
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
