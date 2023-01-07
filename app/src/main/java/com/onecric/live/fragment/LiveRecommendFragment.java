package com.onecric.live.fragment;

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
import com.onecric.live.activity.BasketballMatchDetailActivity;
import com.onecric.live.activity.CricketDetailActivity;
import com.onecric.live.activity.FootballMatchDetailActivity;
import com.onecric.live.activity.LiveDetailActivity;
import com.onecric.live.activity.LiveMoreActivity;
import com.onecric.live.activity.VideoPagerActivity;
import com.onecric.live.activity.VideoSingleActivity;
import com.onecric.live.adapter.BannerRoundImageAdapter;
import com.onecric.live.adapter.LiveRecommendAdapter;
import com.onecric.live.adapter.LiveRecommendHistoryAdapter;
import com.onecric.live.adapter.LiveRecommendMatchAdapter;
import com.onecric.live.adapter.decoration.GridDividerItemDecoration;
import com.onecric.live.model.BannerBean;
import com.onecric.live.model.HistoryLiveBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.LiveBean;
import com.onecric.live.model.LiveMatchBean;
import com.onecric.live.presenter.live.LiveRecommendPresenter;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.live.LiveRecommendView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.youth.banner.Banner;
import com.youth.banner.indicator.RectangleIndicator;
import com.youth.banner.listener.OnBannerListener;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.ArrayList;
import java.util.List;

public class LiveRecommendFragment extends MvpFragment<LiveRecommendPresenter> implements LiveRecommendView, View.OnClickListener {

    private Banner mBanner;
    private RecyclerView rv_match;
    private LiveRecommendMatchAdapter mMatchAdapter;
    private SmartRefreshLayout smart_rl;
    private RecyclerView rv_live;
    private LiveRecommendAdapter mAdapter;
    private RecyclerView rv_today;
    private LiveRecommendAdapter mTodayAdapter;
    private RecyclerView rv_history;
    private LiveRecommendHistoryAdapter mHistoryAdapter;

    //    private int mPage = 1;
    private int mTodayPage = 1;
    private int mHistoryPage = 1;
    private BannerRoundImageAdapter bannerAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_live_recommend;
    }

    @Override
    protected LiveRecommendPresenter createPresenter() {
        return new LiveRecommendPresenter(this);
    }

    @Override
    protected void initUI() {
        mBanner = rootView.findViewById(R.id.banner_live);
        rv_match = rootView.findViewById(R.id.rv_match);
        smart_rl = rootView.findViewById(R.id.smart_rl);
        rv_live = rootView.findViewById(R.id.rv_live);
        rv_today = rootView.findViewById(R.id.rv_today);
        rv_history = rootView.findViewById(R.id.rv_history);
        int width = UIUtil.getScreenWidth(getContext());
        android.view.ViewGroup.LayoutParams pp = mBanner.getLayoutParams();
        pp.height = (int) ((width - UIUtil.dip2px(getContext(), 24)) * 0.6);
        mBanner.setLayoutParams(pp);
//        findViewById(R.id.tv_see_more_one).setOnClickListener(this);
//        findViewById(R.id.tv_see_more_two).setOnClickListener(this);
//        findViewById(R.id.tv_see_more_three).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mMatchAdapter = new LiveRecommendMatchAdapter(R.layout.item_live_recommend_match, new ArrayList<>());
        mMatchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mMatchAdapter.getItem(position).getType() == 0) {
                    FootballMatchDetailActivity.forward(getContext(), mMatchAdapter.getItem(position).getSourceid());
                } else if (mMatchAdapter.getItem(position).getType() == 1) {
                    BasketballMatchDetailActivity.forward(getContext(), mMatchAdapter.getItem(position).getSourceid());
                }
            }
        });
        mMatchAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_reserve) {
                    if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                        if (mMatchAdapter.getItem(position).getReserve() == 0) {
                            mvpPresenter.doReserve(position, mMatchAdapter.getItem(position).getSourceid(), mMatchAdapter.getItem(position).getType());
                        }
                    }
                }
            }
        });
        rv_match.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rv_match.setAdapter(mMatchAdapter);

        smart_rl.setRefreshHeader(new ClassicsHeader(getContext()));
        smart_rl.setRefreshFooter(new ClassicsFooter(getContext()));
        smart_rl.setEnableLoadMore(false);
        smart_rl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                mvpPresenter.getList(false, -1, mPage);
                mvpPresenter.getHistoryList(false, mHistoryPage);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                 mvpPresenter.getAllList();
                mvpPresenter.getList(true, 1);
                mvpPresenter.getHistoryList(true, 1);
                if(bannerAdapter == null){
                    mvpPresenter.getBannerList(-1);
                }
            }
        });
        //FreeLive
/*        mAdapter = new LiveRecommendAdapter(R.layout.item_live_recommend, new ArrayList<>());
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                LiveDetailActivity.forward(getContext(), mAdapter.getItem(position).getUid(), mAdapter.getItem(position).getType(), mAdapter.getItem(position).getMatch_id());
            }
        });
        rv_live.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rv_live.addItemDecoration(new GridDividerItemDecoration(getContext(), 10, 2));
        rv_live.setAdapter(mAdapter);*/
        //TodayLive
        mTodayAdapter = new LiveRecommendAdapter(R.layout.item_live_recommend, new ArrayList<>());
        mTodayAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mTodayAdapter.getItem(position).getIslive() == 0) {
                    ToastUtil.show("The broadcast has not started");
                } else {
                    LiveDetailActivity.forward(getContext(), mTodayAdapter.getItem(position).getUid(), mTodayAdapter.getItem(position).getType(), mTodayAdapter.getItem(position).getMatch_id());
                }
            }
        });
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.layout_common_empty, null, false);
        inflate.findViewById(R.id.ll_empty).setVisibility(View.VISIBLE);
        mTodayAdapter.setEmptyView(inflate);
        rv_today.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rv_today.addItemDecoration(new GridDividerItemDecoration(getContext(), 10, 2));
        rv_today.setAdapter(mTodayAdapter);
        //HistoryLive
        mHistoryAdapter = new LiveRecommendHistoryAdapter(R.layout.item_live_recommend, new ArrayList<>());
        mHistoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //fixme 播放视频、缺少封面
                String url = mHistoryAdapter.getItem(position).getMediaUrl();
                if (!TextUtils.isEmpty(url)) {
                    VideoSingleActivity.forward(getContext(), mHistoryAdapter.getItem(position).getMediaUrl(), null);
                }
            }
        });
        View inflate2 = LayoutInflater.from(getContext()).inflate(R.layout.layout_common_empty, null, false);
        inflate2.findViewById(R.id.ll_empty).setVisibility(View.VISIBLE);
        mHistoryAdapter.setEmptyView(inflate2);
        rv_history.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rv_history.addItemDecoration(new GridDividerItemDecoration(getContext(), 10, 2));
        rv_history.setAdapter(mHistoryAdapter);

        smart_rl.autoRefresh();
        mvpPresenter.getRecommendList();
//        mvpPresenter.getBannerList(-1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_see_more_one:
                LiveMoreActivity.forward(getContext(), 0);
                break;
            case R.id.tv_see_more_two:
                LiveMoreActivity.forward(getContext(), 1);
                break;
            case R.id.tv_see_more_three:
                LiveMoreActivity.forward(getContext(), 2);
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
    public void getDataSuccess(JsonBean jsonBean) {

    }

    @Override
    public void getDataSuccess(boolean isRefresh, List<LiveBean> list) {
        if (isRefresh) {
            smart_rl.finishRefresh();
            mTodayPage = 2;
            if (list != null) {
                mTodayAdapter.setNewData(list);
            }
        } else if (list != null && list.size() > 0) {
            mTodayPage++;
            mTodayAdapter.addData(list);
        }
    }

    @Override
    public void getDataHistorySuccess(boolean isRefresh, List<HistoryLiveBean> list) {
        if (isRefresh) {
            smart_rl.finishRefresh();
            mHistoryPage = 2;
            if (list != null) {
                mHistoryAdapter.setNewData(list);
            }
        } else if (list != null && list.size() > 0) {
            smart_rl.finishLoadMore();
            mHistoryPage++;
            mHistoryAdapter.addData(list);
        } else {
            smart_rl.finishLoadMoreWithNoMoreData();
        }
    }

    @Override
    public void getDataSuccess(List<LiveMatchBean> list) {
        if (list != null) {
            mMatchAdapter.setNewData(list);
        }
    }

    @Override
    public void getDataSuccess(List<LiveBean> freeList, List<LiveBean> todayList, List<LiveBean> historyList) {
        smart_rl.finishRefresh();
/*        if (freeList == null) {
            freeList = new ArrayList<>();
        }
        if (todayList == null) {
            todayList = new ArrayList<>();
        }
        if (historyList == null) {
            historyList = new ArrayList<>();
        }
//        mAdapter.setNewData(freeList);

        mTodayAdapter.setNewData(todayList);
        mHistoryAdapter.setNewData(historyList);*/
    }

    @Override
    public void doReserveSuccess(int position) {
        LiveMatchBean item = mMatchAdapter.getItem(position);
        if (item.getReserve() == 0) {
            item.setReserve(1);
        } else {
            item.setReserve(0);
        }
        mMatchAdapter.notifyItemChanged(position);
    }

    @Override
    public void getBannerSuccess(List<BannerBean> list, int position) {
        if (list != null && list.size() > 0) {
            mBanner.setIndicator(new RectangleIndicator(getContext()));
            bannerAdapter = new BannerRoundImageAdapter(list) {
                @Override
                public void onBindView(Object holder, Object data, int position, int size) {
                    BannerBean bannerBean = (BannerBean) data;
                    GlideUtil.loadImageDefault(getContext(), bannerBean.getImg(), ((BannerRoundImageHolder) holder).imageView);
                }
            };
            bannerAdapter.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(Object data, int position) {
                    mvpPresenter.getBannerList(position);
//                    BannerBean bannerBean = (BannerBean) data;
//                    if (bannerBean.getAnchor_id() != 0) {
//                        LiveDetailActivity.forward(getContext(), bannerBean.getAnchor_id(), bannerBean.getParam_type(), bannerBean.getParam_id());
//                    } else if (bannerBean.getParam_id() != 0) {
//                        CricketDetailActivity.forward(getActivity(), bannerBean.getParam_id());
//                    }
                }
            });
            if (position != -1) {
                BannerBean bannerBean = list.get(position);
                if (bannerBean.getAnchor_id() != 0) {
                    LiveDetailActivity.forward(getContext(), bannerBean.getAnchor_id(), bannerBean.getParam_type(), bannerBean.getParam_id());
                } else if (bannerBean.getParam_id() != 0) {
                    CricketDetailActivity.forward(getActivity(), bannerBean.getParam_id());
                }
            }
            if (mBanner.getAdapter() == null) {
                mBanner.setAdapter(bannerAdapter);
                mBanner.addBannerLifecycleObserver(this);
            } else {
                mBanner.getAdapter().notifyDataSetChanged();
            }
//            mBanner.addBannerLifecycleObserver(this);
        }
    }

    @Override
    public void getDataFail(String msg) {
        ToastUtil.show(msg);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mBanner != null) {
            mBanner.start();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mBanner != null) {
            mBanner.stop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBanner != null) {
            mBanner.destroy();
        }
    }
}
