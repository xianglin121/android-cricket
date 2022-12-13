package com.onecric.live.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.onecric.live.R;
import com.onecric.live.adapter.BasketballDataScheduleAdapter;
import com.onecric.live.model.DataMatchTypeBean;
import com.onecric.live.model.DataScheduleBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.presenter.match.BasketballDataSchedulePresenter;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.match.BasketballDataScheduleView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class BasketballDataScheduleFragment extends MvpFragment<BasketballDataSchedulePresenter> implements BasketballDataScheduleView, View.OnClickListener {

    public static BasketballDataScheduleFragment newInstance() {
        BasketballDataScheduleFragment fragment = new BasketballDataScheduleFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private int mId;
    private int mSeasonId;
    private TextView tv_filter;
    private SmartRefreshLayout smart_rl;
    private RecyclerView recyclerView;
    private BasketballDataScheduleAdapter mAdapter;

    private OptionsPickerView pvCustomOptions;
    private List<DataMatchTypeBean> mMatchTypeList;
    private int mMatchTypeId;
    private int mPage = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_basketball_data_schedule;
    }

    @Override
    protected BasketballDataSchedulePresenter createPresenter() {
        return new BasketballDataSchedulePresenter(this);
    }

    @Override
    protected void initUI() {
        tv_filter = findViewById(R.id.tv_filter);
        smart_rl = findViewById(R.id.smart_rl);
        recyclerView = findViewById(R.id.recyclerView);

        tv_filter.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        smart_rl.setRefreshHeader(new ClassicsHeader(getContext()));
        smart_rl.setRefreshFooter(new ClassicsFooter(getContext()));
        smart_rl.setEnableRefresh(false);
        smart_rl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getList(false, mId, mPage, mSeasonId, mMatchTypeId);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                mvpPresenter.getList(true, 1, mUid);
            }
        });

        mAdapter = new BasketballDataScheduleAdapter(R.layout.item_basketball_data_schedule, new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);
    }

    public void setData(int id, int seasonId, List<DataMatchTypeBean> matchTypeList) {
        if (matchTypeList != null && matchTypeList.size() > 0) {
            mMatchTypeList = matchTypeList;
            mMatchTypeId = matchTypeList.get(0).getId();
            tv_filter.setText(matchTypeList.get(0).getName());
            mId = id;
            mSeasonId = seasonId;
            mvpPresenter.getList(true, mId, 1, mSeasonId, mMatchTypeId);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataSuccess(boolean isRefresh, List<DataScheduleBean> list) {
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
            if (list != null && list.size() > 0) {
                smart_rl.finishLoadMore();
                mAdapter.addData(list);
            }else {
                smart_rl.finishLoadMoreWithNoMoreData();
            }
        }
    }

    @Override
    public void getDataSuccess(JsonBean model) {

    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_filter:
                if (mMatchTypeList != null && mMatchTypeList.size() > 0) {
                    List<String> list = new ArrayList<>();
                    List<Integer> idList = new ArrayList<>();
                    for (int i = 0; i < mMatchTypeList.size(); i++) {
                        list.add(mMatchTypeList.get(i).getName());
                        idList.add(mMatchTypeList.get(i).getId());
                    }
                    pvCustomOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int option2, int options3, View v) {
                            //返回的分别是三个级别的选中位置
                            tv_filter.setText(list.get(options1));
                            mMatchTypeId = idList.get(options1);
                            mvpPresenter.getList(true, mId, 1, mSeasonId, mMatchTypeId);
                        }
                    })
                            .setLayoutRes(R.layout.pickerview_choose_language, new CustomListener() {
                                @Override
                                public void customLayout(View v) {
                                    TextView tv_title = v.findViewById(R.id.tv_title);
                                    TextView tv_cancel = v.findViewById(R.id.tv_cancel);
                                    TextView tv_confirm = v.findViewById(R.id.tv_confirm);
                                    tv_title.setText("");
                                    tv_confirm.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            pvCustomOptions.returnData();
                                            pvCustomOptions.dismiss();
                                        }
                                    });

                                    tv_cancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            pvCustomOptions.dismiss();
                                        }
                                    });
                                }
                            })
                            .isDialog(false)
                            .setOutSideCancelable(true)
                            .build();

                    pvCustomOptions.setPicker(list);//添加数据
                    pvCustomOptions.show();
                }
                break;
        }
    }
}
