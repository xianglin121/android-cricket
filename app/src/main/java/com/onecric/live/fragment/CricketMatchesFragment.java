package com.onecric.live.fragment;

import static com.onecric.live.util.TimeUtil.getStringTimes;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.R;
import com.onecric.live.activity.CricketDetailActivity;
import com.onecric.live.adapter.CricketDetailAdapter;
import com.onecric.live.model.CricketMatchBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.presenter.cricket.CricketMatchesPresenter;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.cricket.CricketMatchesView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/26
 */
public class CricketMatchesFragment extends MvpFragment<CricketMatchesPresenter> implements CricketMatchesView {
    public static CricketMatchesFragment newInstance(int id) {
        CricketMatchesFragment fragment = new CricketMatchesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    private int mId;
    private SmartRefreshLayout smart_rl;
    private RecyclerView recyclerView;
    private CricketDetailAdapter mAdapter;
    private int mPage = 1;
    private ProgressBar progress_bar;
    private int todayPosition = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cricket_matches;
    }

    @Override
    protected CricketMatchesPresenter createPresenter() {
        return new CricketMatchesPresenter(this);
    }

    @Override
    protected void initUI() {
        mId = getArguments().getInt("id");
        smart_rl = findViewById(R.id.smart_rl);
        recyclerView = findViewById(R.id.recyclerView);
        progress_bar = findViewById(R.id.progress_bar);
    }

    @Override
    protected void initData() {
        MaterialHeader materialHeader = new MaterialHeader(getContext());
        materialHeader.setColorSchemeColors(getResources().getColor(R.color.c_DC3C23));
//        smart_rl.setRefreshHeader(materialHeader);
        smart_rl.setEnableLoadMore(false);
        smart_rl.setEnableRefresh(false);
//        smart_rl.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                mvpPresenter.getMatchList(true, mId, 1);
//            }
//        });
        mAdapter = new CricketDetailAdapter(R.layout.item_cricket_detail, new ArrayList<>());
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CricketDetailActivity.forward(getContext(), mAdapter.getItem(position).getId());
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition
                    int lastVisibleItem = manager.findLastVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();
                    // 判断是否滚动到底部
                    if (lastVisibleItem == (totalItemCount - 1)) {
                        //加载更多功能的代码
                        mvpPresenter.getMatchList(false, mId, mPage);
                    }
                }
            }
        });
        progress_bar.setVisibility(View.VISIBLE);
        mvpPresenter.getMatchList(true, mId, 1);
    }

    @Override
    public void getDataSuccess(boolean isRefresh, List<CricketMatchBean> list) {
        progress_bar.setVisibility(View.GONE);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//要转换的时间格式
        todayPosition = list.size()-1;
        try {
            for(int i=0;i<list.size();i++){
                if(getStringTimes(list.get(i).getScheduled().substring(0,10),"yyyy-MM-dd") >= (sdf.parse(sdf.format(new Date())).getTime())/1000){
                    todayPosition = i;
                    break;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

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
                recyclerView.scrollToPosition(todayPosition);
            }else {
                mAdapter.setNewData(new ArrayList<>());
                showEmptyView();
            }
        }else {
            mPage++;
            if (list != null && list.size() > 0) {
                smart_rl.finishLoadMore();
                mAdapter.addData(list);
                recyclerView.scrollToPosition(todayPosition);
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
//        smart_rl.finishRefresh();
//        smart_rl.finishLoadMore();
        progress_bar.setVisibility(View.GONE);
        mPage--;
    }
}
