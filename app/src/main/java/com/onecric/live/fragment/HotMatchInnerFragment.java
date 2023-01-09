package com.onecric.live.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.Constant;
import com.onecric.live.R;
import com.onecric.live.activity.BasketballMatchDetailActivity;
import com.onecric.live.activity.FootballMatchDetailActivity;
import com.onecric.live.adapter.MatchSecondAdapter;
import com.onecric.live.model.Channel;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.MatchListBean;
import com.onecric.live.model.MatchSocketBean;
import com.onecric.live.presenter.match.HotMatchInnerPresenter;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.match.HotMatchInnerView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HotMatchInnerFragment extends MvpFragment<HotMatchInnerPresenter> implements HotMatchInnerView {

    public static HotMatchInnerFragment newInstance(Channel channel) {
        HotMatchInnerFragment fragment = new HotMatchInnerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.DATA_CHANNEL, channel);
        fragment.setArguments(bundle);
        return fragment;
    }

    private Channel mChannel;
    private SmartRefreshLayout smart_rl;
    private RecyclerView recyclerview;
    private LinearLayoutManager mLayoutManager;
    private MatchSecondAdapter mAdapter;

    private int mPage = 1;
    private Map<String, Integer> mValueMap = new ArrayMap<>();
    private Map<String, Integer> mAddDateMap = new ArrayMap<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hot_match_inner;
    }

    @Override
    protected HotMatchInnerPresenter createPresenter() {
        return new HotMatchInnerPresenter(this);
    }

    @Override
    protected void initUI() {
        mChannel = (Channel) getArguments().getSerializable(Constant.DATA_CHANNEL);

        smart_rl = rootView.findViewById(R.id.smart_rl);
        recyclerview = rootView.findViewById(R.id.recyclerview);
        TextView textview = rootView.findViewById(R.id.textview);
        if (mChannel != null) {
            if (!TextUtils.isEmpty(mChannel.short_name_zh)) {
                textview.setText(mChannel.short_name_zh);
            }
        }

        findViewById(R.id.iv_today).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeNow = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
                Integer position = mAddDateMap.get(timeNow);
                if (position != null) {
                    if (position < mAdapter.getItemCount()){
                        int first = mLayoutManager.findFirstVisibleItemPosition();
                        if (position > first) {
                            recyclerview.scrollToPosition(position);
                            recyclerview.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    int firstItem = mLayoutManager.findFirstVisibleItemPosition();
                                    int lastItem = mLayoutManager.findLastVisibleItemPosition();
                                    if(recyclerview.getChildAt(lastItem-firstItem)==null){

                                    }else{
                                        int offSet = recyclerview.getChildAt(lastItem - firstItem).getTop();
                                        recyclerview.smoothScrollBy(0,offSet);
                                    }
                                }
                            }, 50);
                        }else if (position == first) {
                            ToastUtil.show(getString(R.string.tip_scroll_to_today));
                        }else {
                            recyclerview.scrollToPosition(position);
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
        List<MatchListBean> list = new ArrayList<>();
//        MatchListBean one = new MatchListBean();
//        one.setItemType(MatchListBean.HEAD);
//        list.add(one);
//        MatchListBean two = new MatchListBean();
//        two.setItemType(MatchListBean.CONTENT);
//        list.add(two);
//        MatchListBean three = new MatchListBean();
//        three.setItemType(MatchListBean.CONTENT);
//        list.add(three);
//        mAdapter = new MatchSecondAdapter(list);
//        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
////                FootballMatchDetailActivity.forward(getContext());
//            }
//        });
        MaterialHeader materialHeader = new MaterialHeader(getContext());
        materialHeader.setColorSchemeColors(getContext().getResources().getColor(R.color.c_DC3C23));
        smart_rl.setRefreshHeader(materialHeader);
        smart_rl.setRefreshFooter(new ClassicsFooter(getContext()));
        smart_rl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getList(false, mChannel, mPage);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getList(true, mChannel, 1);
            }
        });
        mAdapter = new MatchSecondAdapter(new ArrayList<>());
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mAdapter.getItem(position).getType() == 1) {
                    BasketballMatchDetailActivity.forward(getContext(), mAdapter.getItem(position).getSourceid());
                }else {
                    FootballMatchDetailActivity.forward(getContext(), mAdapter.getItem(position).getSourceid());
                }
            }
        });
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerview.setLayoutManager(mLayoutManager);
        recyclerview.setAdapter(mAdapter);

        smart_rl.autoRefresh();
    }

    public void updateData(MatchSocketBean dataBean) {
        if (mAdapter != null) {
            for (int i = 0; i < mAdapter.getItemCount(); i++) {
                MatchListBean item = mAdapter.getItem(i);
                if (item.getSourceid() == dataBean.getId()) {
                    item.setStatus_id(dataBean.getStatus());
                    if("basketball_match".equals(dataBean.getType())) {
                        item.setHome_scores(dataBean.getHome_scores_num());
                        item.setAway_scores(dataBean.getAway_scores_num());
                    }else {
                        item.setHome_scores(dataBean.getHome_score());
                        item.setAway_scores(dataBean.getAway_score());
                    }
                    item.setMatch_str(dataBean.getMatch_str());
                    mAdapter.notifyItemChanged(i);
                    break;
                }
            }
        }
    }

    public List<MatchListBean> getData() {
        List<MatchListBean> list = new ArrayList();
        if (mAdapter != null) {
            list.addAll(mAdapter.getData());
        }
        return list;
    }

//    public void updateTime() {
//        if (mAdapter != null) {
//            for (int i = 0; i < mAdapter.getItemCount(); i++) {
//                MatchListBean item = mAdapter.getItem(i);
//                if (!TextUtils.isEmpty(item.getMatch_time())) {
//                    Long match_time = Long.parseLong(item.getMatch_time());
//                    if (match_time != null) {
//                        if (item.getStatus_id() == 2) {
//                            match_time = System.currentTimeMillis()/1000 - match_time;
//                            if (match_time < (45*60)) {
//                                if ((match_time + 60) < (45*60)) {
//                                    match_time = match_time + 60;
//                                    item.setMatch_str((match_time/60) + "");
//                                }else {
//                                    item.setMatch_str("45+");
//                                }
//                            }
//                        }else if (item.getStatus_id() == 4) {
//                            match_time = System.currentTimeMillis()/1000 - match_time + (45*60);
//                            if (match_time < (90*60)) {
//                                if ((match_time + 60) < (90*60)) {
//                                    match_time = match_time + 60;
//                                    item.setMatch_str((match_time/60) + "");
//                                }else {
//                                    item.setMatch_str("90+");
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            mAdapter.notifyDataSetChanged();
//        }
//    }

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
    public void getDataSuccess(boolean isRefresh, List<MatchListBean> list) {
        if (isRefresh) {
            smart_rl.finishRefresh();
            mPage = 2;
            if (list != null) {
                if (list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        MatchListBean matchListBean = list.get(i);
                        if (!mValueMap.containsKey(matchListBean.getMatch_date())) {
                            mValueMap.put(matchListBean.getMatch_date(), i);
                        }
                    }
                    int headCount = 0;
                    for (String date : mValueMap.keySet()) {
                        Integer position = mValueMap.get(date);
                        MatchListBean head = new MatchListBean();
                        head.setItemType(MatchListBean.HEAD);
                        head.setMatch_date(date);
                        list.add(position+headCount, head);
                        mAddDateMap.put(date, position+headCount);
                        headCount++;
                    }
                }
                mAdapter.setNewData(list);
            }
        }else {
            mPage++;
            if (list != null && list.size() > 0) {
                smart_rl.finishLoadMore();
                for (int i = 0; i < list.size(); i++) {
                    MatchListBean matchListBean = list.get(i);
                    if (!mValueMap.containsKey(matchListBean.getMatch_date())) {
                        mValueMap.put(matchListBean.getMatch_date(), i);
                    }
                }
                int headCount = 0;
                for (String date : mValueMap.keySet()) {
//                    boolean has = false;
//                    for (int i = 0; i < mAddDateList.size(); i++) {
//                        if (date.equals(mAddDateList.get(i))) {
//                            has = true;
//                            break;
//                        }
//                    }
                    if (!mAddDateMap.containsKey(date)) {
                        Integer position = mValueMap.get(date);
                        MatchListBean head = new MatchListBean();
                        head.setItemType(MatchListBean.HEAD);
                        head.setMatch_date(date);
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
    public void getDataFail(String msg) {

    }
}
