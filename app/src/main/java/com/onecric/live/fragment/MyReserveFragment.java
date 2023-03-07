package com.onecric.live.fragment;

import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.R;
import com.onecric.live.adapter.MyReserveAdapter;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.ReserveMatchBean;
import com.onecric.live.presenter.user.MyReservePresenter;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.user.MyReserveView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/1/3
 */
public class MyReserveFragment extends MvpFragment<MyReservePresenter> implements MyReserveView, View.OnClickListener {

    public static MyReserveFragment newInstance() {
        MyReserveFragment fragment = new MyReserveFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private SmartRefreshLayout smart_rl;
    private RecyclerView recyclerview;
    private MyReserveAdapter mAdapter;

    private int mPage = 1;
    private Map<String, Integer> mValueMap = new ArrayMap<>();
    private List<String> mAddDateList = new ArrayList<>();

    @Override
    protected MyReservePresenter createPresenter() {
        return new MyReservePresenter(this);
    }

    @Override
    protected void initUI() {
        smart_rl = findViewById(R.id.smart_rl);
        recyclerview = findViewById(R.id.recyclerview);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my_reserve;
    }

    @Override
    protected void initData() {
        MaterialHeader materialHeader = new MaterialHeader(getContext());
        materialHeader.setColorSchemeColors(getContext().getResources().getColor(R.color.c_DC3C23));
        smart_rl.setRefreshHeader(materialHeader);
        smart_rl.setRefreshFooter(new ClassicsFooter(getContext()));
        smart_rl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getList(false, mPage);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getList(true, 1);
            }
        });

        mAdapter = new MyReserveAdapter(new ArrayList<>());
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_reserve) {
                    mvpPresenter.doReserve(position, mAdapter.getItem(position).getMatch_id(), mAdapter.getItem(position).getType());
                }
            }
        });
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setAdapter(mAdapter);

        smart_rl.autoRefresh();
    }

    @Override
    public void getDataSuccess(boolean isRefresh, List<ReserveMatchBean> list) {
        if (isRefresh) {
            smart_rl.finishRefresh();
            mPage = 2;
            if (list != null) {
                if (list.size() > 0) {
                    hideEmptyView();
                    for (int i = 0; i < list.size(); i++) {
                        ReserveMatchBean reserveMatchBean = list.get(i);
                        if (!mValueMap.containsKey(reserveMatchBean.getStart2())) {
                            mValueMap.put(reserveMatchBean.getStart2(), i);
                        }
                    }
                    int headCount = 0;
                    for (String date : mValueMap.keySet()) {
                        Integer position = mValueMap.get(date);
                        ReserveMatchBean head = new ReserveMatchBean();
                        head.setItemType(ReserveMatchBean.HEAD);
                        head.setStart2(date);
                        list.add(position+headCount, head);
                        headCount++;
                    }
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
            if (list != null && list.size() > 0) {
                smart_rl.finishLoadMore();
                for (int i = 0; i < list.size(); i++) {
                    ReserveMatchBean reserveMatchBean = list.get(i);
                    if (!mValueMap.containsKey(reserveMatchBean.getStart2())) {
                        mValueMap.put(reserveMatchBean.getStart2(), i);
                    }
                }
                int headCount = 0;
                for (String date : mValueMap.keySet()) {
                    boolean has = false;
                    for (int i = 0; i < mAddDateList.size(); i++) {
                        if (date.equals(mAddDateList.get(i))) {
                            has = true;
                            break;
                        }
                    }
                    if (!has) {
                        Integer position = mValueMap.get(date);
                        ReserveMatchBean head = new ReserveMatchBean();
                        head.setItemType(ReserveMatchBean.HEAD);
                        head.setStart2(date);
                        list.add(position+headCount, head);
                        headCount++;
                    }
                }
                mAdapter.addData(list);
            }else {
                smart_rl.finishLoadMoreWithNoMoreData();
            }
        }
    }

    @Override
    public void doReserveSuccess(int position) {
        mAdapter.remove(position);
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
        smart_rl.finishLoadMore();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
