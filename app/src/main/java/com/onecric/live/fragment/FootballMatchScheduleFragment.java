package com.onecric.live.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.activity.FootballMatchDetailActivity;
import com.onecric.live.adapter.FootballMatchInnerAdapter;
import com.onecric.live.adapter.MatchSelectDateAdapter;
import com.onecric.live.model.DateBean;
import com.onecric.live.model.FootballMatchBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.MatchSocketBean;
import com.onecric.live.presenter.match.FootballMatchSchedulePresenter;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.match.FootballMatchScheduleView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FootballMatchScheduleFragment extends MvpFragment<FootballMatchSchedulePresenter> implements FootballMatchScheduleView {

    public static FootballMatchScheduleFragment newInstance(int type) {
        FootballMatchScheduleFragment fragment = new FootballMatchScheduleFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    private int mType;
    private RecyclerView rv_date;
    private MatchSelectDateAdapter mDateAdapter;
    private SmartRefreshLayout smart_rl;
    private RecyclerView recyclerview;
    private FootballMatchInnerAdapter mAdapter;

    private List<String> mDateList;
    private int mSelectPosition = 0;//当前选中的日期

    private int mPage = 1;
    private String mId;

    public void updateId(String id) {
        mId = id;
        if (smart_rl != null) {
            smart_rl.autoRefresh();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_football_match_schedule;
    }

    @Override
    protected FootballMatchSchedulePresenter createPresenter() {
        return new FootballMatchSchedulePresenter(this);
    }

    @Override
    protected void initUI() {
        mDateList = new ArrayList<>();
        mType = getArguments().getInt("type");
        rv_date = rootView.findViewById(R.id.rv_date);
        smart_rl = rootView.findViewById(R.id.smart_rl);
        recyclerview = rootView.findViewById(R.id.recyclerview);
    }

    @Override
    protected void initData() {
        List<DateBean> dates = new ArrayList<>();
        if (mType == 2) {
            for (int i = 0; i < 6; i++) {
                DateBean dateBean = new DateBean();
                dateBean.setDate(getFutureDate(i));
                if (i == 0) {
                    mSelectPosition = 0;
                    dateBean.setSelect(true);
                }
                dates.add(dateBean);
            }
        }else {
            for (int i = 0; i < 6; i++) {
                DateBean dateBean = new DateBean();
                dateBean.setDate(getPastDate(i));
                if (i == 0) {
                    mSelectPosition = 5;
                    dateBean.setSelect(true);
                }
                dates.add(0, dateBean);
            }
        }

        mDateAdapter = new MatchSelectDateAdapter(R.layout.item_match_select_date, dates, mType);
        mDateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mDateAdapter.getItem(mSelectPosition).setSelect(false);
                mDateAdapter.getItem(position).setSelect(true);
                mSelectPosition = position;
                mDateAdapter.notifyDataSetChanged();
                mvpPresenter.getList(true, mType, mDateList.get(mSelectPosition), 1, mId);
            }
        });
        rv_date.setLayoutManager(new GridLayoutManager(getContext(), 6));
        rv_date.setAdapter(mDateAdapter);

        MaterialHeader materialHeader = new MaterialHeader(getContext());
        materialHeader.setColorSchemeColors(getContext().getResources().getColor(R.color.c_DC3C23));
        smart_rl.setRefreshHeader(materialHeader);
        smart_rl.setRefreshFooter(new ClassicsFooter(getContext()));
        smart_rl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getList(false, mType, mDateList.get(mSelectPosition), mPage, mId);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getList(true, mType, mDateList.get(mSelectPosition), 1, mId);
            }
        });
        mAdapter = new FootballMatchInnerAdapter(new ArrayList<>());
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                FootballMatchDetailActivity.forward(getContext(), mAdapter.getItem(position).getId());
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_collect) {
                    if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                        ToastUtil.show(getActivity().getString(R.string.please_login));
                        return;
                    }
                    FootballMatchBean item = mAdapter.getItem(position);
                    if (item.getIs_collect() == 1) {
                        item.setIs_collect(0);
                    }else {
                        item.setIs_collect(1);
                    }
                    mvpPresenter.doCollect(2, item.getId());
                    mAdapter.notifyItemChanged(position);
                }
            }
        });
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setAdapter(mAdapter);

        smart_rl.autoRefresh();
    }

    public void updateData(MatchSocketBean dataBean) {
        if (mAdapter != null) {
            for (int i = 0; i < mAdapter.getItemCount(); i++) {
                FootballMatchBean item = mAdapter.getItem(i);
                if (item.getId() == dataBean.getId()) {
                    item.setStatus(dataBean.getStatus());
                    item.setHome_score(String.valueOf(dataBean.getHome_score()));
                    item.setAway_score(String.valueOf(dataBean.getAway_score()));
                    item.setHome_yellow_card(dataBean.getHome_yellow_card());
                    item.setAway_yellow_card(dataBean.getAway_yellow_card());
                    item.setHome_red_card(dataBean.getHome_red_card());
                    item.setAway_red_card(dataBean.getAway_red_card());
                    item.setHalf_score(dataBean.getHalf_score());
                    item.setCorner_kick(dataBean.getCorner_kick());
                    item.setMatch_str(dataBean.getMatch_str());
                    mAdapter.notifyItemChanged(i);
                    break;
                }
            }
        }
    }

    public void updateTime() {
        if (mAdapter != null) {
            for (int i = 0; i < mAdapter.getItemCount(); i++) {
                FootballMatchBean item = mAdapter.getItem(i);
                Long match_time = item.getMatch_time();
                if (match_time != null) {
                    if (item.getStatus() == 2) {
                        match_time = System.currentTimeMillis()/1000 - match_time;
                        if (match_time < (45*60)) {
                            if ((match_time + 60) < (45*60)) {
                                match_time = match_time + 60;
                                item.setMatch_str((match_time/60) + "");
                            }else {
                                item.setMatch_str("45+");
                            }
                        }
                    }else if (item.getStatus() == 4) {
                        match_time = System.currentTimeMillis()/1000 - match_time + (45*60);
                        if (match_time < (90*60)) {
                            if ((match_time + 60) < (90*60)) {
                                match_time = match_time + 60;
                                item.setMatch_str((match_time/60) + "");
                            }else {
                                item.setMatch_str("90+");
                            }
                        }
                    }
                }
            }
            mAdapter.notifyDataSetChanged();
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
    public void getDataSuccess(boolean isRefresh, List<FootballMatchBean> list) {
        if (isRefresh) {
            smart_rl.finishRefresh();
            mPage = 2;
            if (list != null) {
                mAdapter.setNewData(list);
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
    public void getDataFail(String msg) {

    }

    public List<FootballMatchBean> getData() {
        List<FootballMatchBean> list = new ArrayList();
        if (mAdapter != null) {
            list.addAll(mAdapter.getData());
        }
        return list;
    }

    /**
     * 获取未来 第 past 天的日期
     * @param past
     * @return
     */
    public String getFutureDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy MM/dd");
        String result = format.format(today);
        //保存需要传到后台的日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        mDateList.add(dateFormat.format(today));
        return result;
    }

    /**
     * 获取过去 第 past 天的日期
     * @param past
     * @return
     */
    public String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy MM/dd");
        String result = format.format(today);
        //保存需要传到后台的日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        mDateList.add(0, dateFormat.format(today));
        return result;
    }

    public void refreshData() {
        if (smart_rl != null) {
            smart_rl.autoRefresh();
        }
    }
}
