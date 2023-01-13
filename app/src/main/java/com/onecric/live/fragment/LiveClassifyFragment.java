package com.onecric.live.fragment;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.R;
import com.onecric.live.activity.BasketballMatchDetailActivity;
import com.onecric.live.activity.FootballMatchDetailActivity;
import com.onecric.live.adapter.LiveClassifyTwoAdapter;
import com.onecric.live.adapter.MatchSelectDateAdapter;
import com.onecric.live.model.DateBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.LiveClassifyBean;
import com.onecric.live.presenter.live.LiveClassifyPresenter;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.live.LiveClassifyView;
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

public class LiveClassifyFragment extends MvpFragment<LiveClassifyPresenter> implements LiveClassifyView, View.OnClickListener {

    private RecyclerView rv_date;
    private MatchSelectDateAdapter mDateAdapter;
    private List<String> mDateList;
    private int mSelectPosition = 0;//当前选中的日期

    private TextView tv_football, tv_basketball;
    private int mType = 1;

    private SmartRefreshLayout smart_rl;
    private RecyclerView recyclerview;
    private LiveClassifyTwoAdapter mAdapter;
    private int mPage = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_live_classify;
    }

    @Override
    protected LiveClassifyPresenter createPresenter() {
        return new LiveClassifyPresenter(this);
    }

    @Override
    protected void initUI() {
        mDateList = new ArrayList<>();

        rv_date = rootView.findViewById(R.id.rv_date);
        tv_football = rootView.findViewById(R.id.tv_football);
        tv_basketball = rootView.findViewById(R.id.tv_basketball);
        smart_rl = rootView.findViewById(R.id.smart_rl);
        recyclerview = rootView.findViewById(R.id.recyclerview);

        tv_football.setOnClickListener(this);
        tv_basketball.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        tv_football.setSelected(true);
        List<DateBean> dates = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            DateBean dateBean = new DateBean();
            dateBean.setDate(getFutureDate(i));
            if (i == 0) {
                mSelectPosition = 0;
                dateBean.setSelect(true);
            }
            dates.add(dateBean);
        }
        mDateAdapter = new MatchSelectDateAdapter(R.layout.item_match_select_date, dates, 2);
        mDateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mDateAdapter.getItem(mSelectPosition).setSelect(false);
                mDateAdapter.getItem(position).setSelect(true);
                mSelectPosition = position;
                mDateAdapter.notifyDataSetChanged();
                smart_rl.autoRefresh();
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
                mvpPresenter.getList(false, mType, mDateList.get(mSelectPosition), mPage);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getList(true, mType, mDateList.get(mSelectPosition), 1);
            }
        });
        mAdapter = new LiveClassifyTwoAdapter(new ArrayList<>());
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mAdapter.getItem(position).getType() == 0) {
                    FootballMatchDetailActivity.forward(getContext(), mAdapter.getItem(position).getSourceid());
                }else if (mAdapter.getItem(position).getType() == 1) {
                    BasketballMatchDetailActivity.forward(getContext(), mAdapter.getItem(position).getSourceid());
                }
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_state) {
                    if (mAdapter.getItem(position).getStatus_type() == 0 && mAdapter.getItem(position).getReserve() == 0) {
                        mvpPresenter.doReserve(position, mAdapter.getItem(position).getSourceid(), mAdapter.getItem(position).getType());
                    }
                }
            }
        });
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setAdapter(mAdapter);
        smart_rl.autoRefresh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_football:
                if (tv_football.isSelected()) {
                    return;
                }
                mType = 1;
                tv_football.setSelected(true);
                tv_football.setTextColor(getContext().getResources().getColor(R.color.white));
                tv_football.setBackgroundResource(R.drawable.bg_live_classify);
                tv_basketball.setSelected(false);
                tv_basketball.setTextColor(getContext().getResources().getColor(R.color.c_666666));
                tv_basketball.setBackground(null);
                smart_rl.autoRefresh();
                break;
            case R.id.tv_basketball:
                if (tv_basketball.isSelected()) {
                    return;
                }
                mType = 2;
                tv_basketball.setSelected(true);
                tv_basketball.setTextColor(getContext().getResources().getColor(R.color.white));
                tv_basketball.setBackgroundResource(R.drawable.bg_live_classify);
                tv_football.setSelected(false);
                tv_football.setTextColor(getContext().getResources().getColor(R.color.c_666666));
                tv_football.setBackground(null);
                smart_rl.autoRefresh();
                break;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataSuccess(boolean isRefresh, List<LiveClassifyBean> list) {
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
    public void getDataSuccess(JsonBean model) {

    }

    @Override
    public void getDataFail(String msg) {
        smart_rl.finishRefresh();
        smart_rl.finishLoadMore();
    }

    @Override
    public void doReserveSuccess(int position) {
        LiveClassifyBean item = mAdapter.getItem(position);
        if (item.getReserve() == 0) {
            item.setReserve(1);
        }else {
            item.setReserve(0);
        }
        mAdapter.notifyItemChanged(position);
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
}
