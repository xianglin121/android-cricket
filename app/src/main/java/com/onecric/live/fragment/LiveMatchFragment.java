package com.onecric.live.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.activity.LiveDetailActivity;
import com.onecric.live.activity.LoginActivity;
import com.onecric.live.adapter.LiveAnchorAdapter;
import com.onecric.live.adapter.LiveRecommendAdapter;
import com.onecric.live.adapter.decoration.GridDividerItemDecoration;
import com.onecric.live.fragment.dialog.LoginDialog;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.LiveBean;
import com.onecric.live.presenter.live.LiveMatchPresenter;
import com.onecric.live.util.SpUtil;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.live.LiveMatchView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class LiveMatchFragment extends MvpFragment<LiveMatchPresenter> implements LiveMatchView {
    public static LiveMatchFragment newInstance(int type) {
        LiveMatchFragment fragment = new LiveMatchFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    private int mMatchType;
    private RecyclerView rv_anchor;
    private LiveAnchorAdapter mAnchorAdapter;
    private SmartRefreshLayout smart_rl;
    private RecyclerView rv_live;
    private LiveRecommendAdapter mAdapter;

    private int mPage = 1;
    public LoginDialog loginDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_live_match;
    }

    @Override
    protected LiveMatchPresenter createPresenter() {
        return new LiveMatchPresenter(this);
    }

    @Override
    protected void initUI() {
        mMatchType = getArguments().getInt("type");
        rv_anchor = rootView.findViewById(R.id.rv_anchor);
        smart_rl = rootView.findViewById(R.id.smart_rl);
        rv_live = rootView.findViewById(R.id.rv_live);
    }

    @Override
    protected void initData() {
        mAnchorAdapter = new LiveAnchorAdapter(R.layout.item_live_anchor, new ArrayList<>());
        mAnchorAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken()) && SpUtil.getInstance().getBooleanValue(SpUtil.VIDEO_OVERTIME)){
                    if(loginDialog!=null){
                        loginDialog.show();
                    }else{
                        ToastUtil.show(getString(R.string.please_login));
                    }
                }else if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken()) && SpUtil.getInstance().getBooleanValue(SpUtil.VIDEO_OVERTIME)){
                    if(loginDialog!=null){
                        loginDialog.show();
                    }else{
                        ToastUtil.show(getString(R.string.please_login));
                    }
                }else{
                    LiveDetailActivity.forward(getContext(), mAdapter.getItem(position).getUid(), mAdapter.getItem(position).getType(), mAdapter.getItem(position).getMatch_id());
                }
            }
        });
//        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.layout_common_empty, null, false);
//        inflate.findViewById(R.id.ll_empty).setVisibility(View.VISIBLE);
//        mAnchorAdapter.setEmptyView(inflate);
        rv_anchor.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rv_anchor.setAdapter(mAnchorAdapter);

        MaterialHeader materialHeader = new MaterialHeader(getContext());
        materialHeader.setColorSchemeColors(getContext().getResources().getColor(R.color.c_DC3C23));
        smart_rl.setRefreshHeader(materialHeader);
        smart_rl.setRefreshFooter(new ClassicsFooter(getContext()));
        smart_rl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getList(false, mMatchType, mPage);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getList(true, mMatchType, 1);
            }
        });
        mAdapter = new LiveRecommendAdapter(R.layout.item_live_recommend, new ArrayList<>());
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(mAdapter.getItem(position).getIslive() == 0){
                    ToastUtil.show("The broadcast has not started");
                }else if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken()) && SpUtil.getInstance().getBooleanValue(SpUtil.VIDEO_OVERTIME)){
                    if(loginDialog!=null){
                        loginDialog.show();
                    }else{
                        ToastUtil.show(getString(R.string.please_login));
                    }
                }else{
                    LiveDetailActivity.forward(getContext(), mAdapter.getItem(position).getUid(), mAdapter.getItem(position).getType(), mAdapter.getItem(position).getMatch_id());
                }
            }
        });
        View inflate2 = LayoutInflater.from(getContext()).inflate(R.layout.layout_common_empty, null, false);
        inflate2.findViewById(R.id.ll_empty).setVisibility(View.VISIBLE);
        mAdapter.setEmptyView(inflate2);
        rv_live.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rv_live.addItemDecoration(new GridDividerItemDecoration(getContext(), 10, 2));
        rv_live.setAdapter(mAdapter);

        smart_rl.autoRefresh();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataSuccess(boolean isRefresh, List<LiveBean> list) {
        if (isRefresh) {
            smart_rl.finishRefresh();
            mPage = 2;
            if (list != null) {
                mAdapter.setNewData(list);
                mAnchorAdapter.setNewData(list);
            }
        } else {
            mPage++;
            if (list != null && list.size() > 0) {
                smart_rl.finishLoadMore();
                mAdapter.addData(list);
            } else {
                smart_rl.finishLoadMoreWithNoMoreData();
            }
        }
    }

    @Override
    public void getDataSuccess(JsonBean model) {

    }

    @Override
    public void getDataFail(String msg) {
        smart_rl.finishRefresh();
        smart_rl.finishLoadMore();
    }
}
