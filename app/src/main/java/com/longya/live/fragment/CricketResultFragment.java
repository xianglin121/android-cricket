package com.longya.live.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.longya.live.R;
import com.longya.live.activity.CricketInnerActivity;
import com.longya.live.adapter.CricketAdapter;
import com.longya.live.adapter.SelectTournamentAdapter;
import com.longya.live.custom.ItemDecoration;
import com.longya.live.model.CricketMatchBean;
import com.longya.live.model.CricketTournamentBean;
import com.longya.live.model.JsonBean;
import com.longya.live.presenter.cricket.CricketPresenter;
import com.longya.live.view.BaseFragment;
import com.longya.live.view.MvpFragment;
import com.longya.live.view.cricket.CricketView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/26
 */
public class CricketResultFragment extends MvpFragment<CricketPresenter> implements CricketView, View.OnClickListener {
    public static CricketResultFragment newInstance(String timeType) {
        CricketResultFragment fragment = new CricketResultFragment();
        Bundle bundle = new Bundle();
        bundle.putString("timeType", timeType);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String mTimeType;

    private ImageView iv_streaming;
    private TextView tv_streaming;
    private SmartRefreshLayout smart_rl;
    private RecyclerView recyclerView;
    private CricketAdapter mAdapter;
    private int mPage = 1;

    private Dialog mDialog;
    private RecyclerView rv_tournament;
    private SelectTournamentAdapter mTournamentAdapter;
    private String mTournamentId = "";

    private int mStreaming = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cricket_result;
    }

    @Override
    protected CricketPresenter createPresenter() {
        return new CricketPresenter(this);
    }

    @Override
    protected void initUI() {
        mTimeType = getArguments().getString("timeType");
        iv_streaming = findViewById(R.id.iv_streaming);
        tv_streaming = findViewById(R.id.tv_streaming);
        if ("results".equals(mTimeType)) {
            tv_streaming.setText("Highlights");
        }
        smart_rl = findViewById(R.id.smart_rl);
        recyclerView = findViewById(R.id.recyclerView);

        findViewById(R.id.tv_tournament).setOnClickListener(this);
        findViewById(R.id.ll_streaming).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        MaterialHeader materialHeader = new MaterialHeader(getContext());
        materialHeader.setColorSchemeColors(getResources().getColor(R.color.c_DC3C23));
        smart_rl.setRefreshHeader(materialHeader);
        smart_rl.setEnableLoadMore(false);
        smart_rl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getCricketMatchList(true, mTimeType, mTournamentId, mStreaming, 1);
            }
        });
        mAdapter = new CricketAdapter(R.layout.item_cricket, new ArrayList<>());
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_title) {
                    CricketInnerActivity.forward(getContext(), mAdapter.getItem(position).getName(), mAdapter.getItem(position).getType(), mAdapter.getItem(position).getTournament_id());
                }
            }
        });
        recyclerView.addItemDecoration(new ItemDecoration(getContext(), getResources().getColor(R.color.c_E5E5E5), 0, 12));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition
                    int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    int totalItemCount = linearLayoutManager.getItemCount();
                    // 判断是否滚动到底部
                    if (lastVisibleItem == (totalItemCount - 1)) {
                        //加载更多功能的代码
                        mvpPresenter.getCricketMatchList(false, mTimeType, mTournamentId, mStreaming, mPage);
                    }
                }
            }
        });

        initDialog();
        mvpPresenter.getTournamentList();
        mvpPresenter.getCricketMatchList(true, mTimeType, mTournamentId, mStreaming, 1);
    }

    private void initDialog() {
        mDialog = new Dialog(getContext(), R.style.dialog);
        mDialog.setContentView(R.layout.dialog_select_tournament);
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.getWindow().setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.BOTTOM;
        mDialog.getWindow().setAttributes(params);
        rv_tournament = mDialog.findViewById(R.id.rv_tournament);
        mDialog.findViewById(R.id.iv_close).setOnClickListener(this);
        mDialog.findViewById(R.id.tv_reset).setOnClickListener(this);
        mDialog.findViewById(R.id.tv_apply).setOnClickListener(this);
    }

    @Override
    public void getDataSuccess(List<CricketTournamentBean> list) {
        if (list != null && rv_tournament != null) {
            mTournamentAdapter = new SelectTournamentAdapter(R.layout.item_select_tournament, list);
            mTournamentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    mTournamentAdapter.getItem(position).setCheck(!mTournamentAdapter.getItem(position).isCheck());
                    mTournamentAdapter.notifyItemChanged(position);
                }
            });
            rv_tournament.setLayoutManager(new LinearLayoutManager(getContext()));
            rv_tournament.setAdapter(mTournamentAdapter);
        }
    }

    @Override
    public void getDataSuccess(boolean isRefresh, int total, List<CricketTournamentBean> list) {
        if (isRefresh) {
            smart_rl.finishRefresh();
            mPage = 2;
            if (list != null) {
                if (list.size() > 0) {
                    hideEmptyView();
                }else {
                    showEmptyView();
                }
                mAdapter.setNewData(list);
            }else {
                mAdapter.setNewData(new ArrayList<>());
                showEmptyView();
            }
        }else {
            mPage++;
            if (mPage <= total) {
                smart_rl.finishLoadMore();
                mAdapter.addData(list);
            }else {
                smart_rl.finishLoadMoreWithNoMoreData();
            }
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataSuccess(JsonBean model) {

    }

    @Override
    public void getDataFail(String msg) {
        smart_rl.finishRefresh();
        showEmptyView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_tournament:
                if (mDialog != null) {
                    mDialog.show();
                }
                break;
            case R.id.ll_streaming:
                if (mStreaming == 1) {
                    mStreaming = 0;
                    iv_streaming.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.c_CCCCCC));
                    tv_streaming.setTextColor(getResources().getColor(R.color.c_333333));
                }else {
                    mStreaming = 1;
                    iv_streaming.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.c_DC3C23));
                    tv_streaming.setTextColor(getResources().getColor(R.color.c_DC3C23));
                }
                smart_rl.autoRefresh();
                break;
            case R.id.iv_close:
                if (mDialog != null) {
                    mDialog.dismiss();
                }
                break;
            case R.id.tv_reset:
                List<CricketTournamentBean> data = mTournamentAdapter.getData();
                for (int i = 0; i < data.size(); i++) {
                    data.get(i).setCheck(false);
                }
                mTournamentAdapter.notifyDataSetChanged();
                mTournamentId = "";
                break;
            case R.id.tv_apply:
                if (mDialog != null) {
                    mDialog.dismiss();
                }
                List<CricketTournamentBean> temp = mTournamentAdapter.getData();
                for (int i = 0; i < temp.size(); i++) {
                    if (temp.get(i).isCheck()) {
                        mTournamentId = mTournamentId + temp.get(i).getTournament_id() + ",";
                    }
                }
                if (mTournamentId.length() > 0) {
                    mTournamentId = mTournamentId.substring(0, mTournamentId.length()-1);
                }
                smart_rl.autoRefresh();
                break;
        }
    }
}
