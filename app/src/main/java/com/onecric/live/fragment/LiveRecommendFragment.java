package com.onecric.live.fragment;

import static com.onecric.live.util.UiUtils.collapseView;
import static com.onecric.live.util.UiUtils.expandView;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.activity.BasketballMatchDetailActivity;
import com.onecric.live.activity.CricketDetailActivity;
import com.onecric.live.activity.FootballMatchDetailActivity;
import com.onecric.live.activity.LiveDetailActivity;
import com.onecric.live.activity.LiveMoreActivity;
import com.onecric.live.activity.LiveNotStartDetailActivity;
import com.onecric.live.activity.VideoPagerActivity;
import com.onecric.live.activity.VideoSingleActivity;
import com.onecric.live.adapter.BannerRoundImageAdapter;
import com.onecric.live.adapter.LiveMatchAdapter;
import com.onecric.live.adapter.LiveRecommendAdapter;
import com.onecric.live.adapter.LiveRecommendHistoryAdapter;
import com.onecric.live.adapter.LiveRecommendMatchAdapter;
import com.onecric.live.adapter.decoration.GridDividerItemDecoration;
import com.onecric.live.custom.ItemDecoration;
import com.onecric.live.event.ToggleTabEvent;
import com.onecric.live.fragment.dialog.LoginDialog;
import com.onecric.live.model.BannerBean;
import com.onecric.live.model.HistoryLiveBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.LiveBean;
import com.onecric.live.model.LiveMatchBean;
import com.onecric.live.model.LiveMatchListBean;
import com.onecric.live.presenter.live.LiveRecommendPresenter;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.util.SpUtil;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.live.LiveRecommendView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.youth.banner.Banner;
import com.youth.banner.indicator.RectangleIndicator;
import com.youth.banner.listener.OnBannerListener;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import org.greenrobot.eventbus.EventBus;

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
    private TextView tv_see_more_three;
    private RecyclerView rv_match_upcoming;
    private TextView tv_upcoming;
    private LinearLayout ll_today;

    //    private int mPage = 1;
    private int mTodayPage = 1;
//    private int mHistoryPage = 1;
    private BannerRoundImageAdapter bannerAdapter;
    private LoginDialog loginDialog;
    public void setLoginDialog(LoginDialog dialog){
        this.loginDialog = dialog;
    }


    private boolean isOpenUpcoming = false;
    private int rlComingHeight;
    private Drawable drawableArrUp, drawableArrDown;
    private LiveMatchAdapter mTodayMatchAdapter,mComingAdapter;

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
        ll_today = rootView.findViewById(R.id.ll_today);
        rv_history = rootView.findViewById(R.id.rv_history);
        tv_see_more_three = rootView.findViewById(R.id.tv_see_more_three);
        int width = UIUtil.getScreenWidth(getContext());
        android.view.ViewGroup.LayoutParams pp = mBanner.getLayoutParams();
        pp.height = (int) ((width - UIUtil.dip2px(getContext(), 24)) * 0.6);
        mBanner.setLayoutParams(pp);
        tv_see_more_three.setOnClickListener(this);
        rv_match_upcoming = rootView.findViewById(R.id.rv_match_upcoming);
        tv_upcoming = rootView.findViewById(R.id.tv_upcoming);
        tv_upcoming.setOnClickListener(this);
        rlComingHeight = UIUtil.dip2px(getContext(),120);

        drawableArrUp = getResources().getDrawable(R.mipmap.icon_arrow_up_four);
        drawableArrUp.setBounds(0, 0, drawableArrUp.getMinimumWidth(),drawableArrUp.getMinimumHeight());
        drawableArrDown = getResources().getDrawable(R.mipmap.icon_arrow_down_four);
        drawableArrDown.setBounds(0, 0, drawableArrDown.getMinimumWidth(),drawableArrDown.getMinimumHeight());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initData() {
        MaterialHeader materialHeader = new MaterialHeader(getContext());
        materialHeader.setColorSchemeColors(getContext().getResources().getColor(R.color.c_DC3C23));
        smart_rl.setRefreshHeader(materialHeader);
        smart_rl.setEnableLoadMore(false);
        smart_rl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getMatchList();
                mvpPresenter.getList(true, 1);
                mvpPresenter.getHistoryList(true, 1);
                if(bannerAdapter == null){
                    mvpPresenter.getBannerList(-1);
                }
            }
        });

        //TodayLive
        mTodayAdapter = new LiveRecommendAdapter(R.layout.item_live_recommend, new ArrayList<>());
        mTodayAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken()) && SpUtil.getInstance().getBooleanValue(SpUtil.VIDEO_OVERTIME) && SpUtil.getInstance().getIntValue(SpUtil.LOGIN_REMIND) != 0){
                    if(loginDialog!=null){
                        loginDialog.show();
                    }else{
                        ToastUtil.show(getString(R.string.please_login));
                    }
                }else if (mTodayAdapter.getItem(position).getIslive() == 0) {
                    LiveNotStartDetailActivity.forward(getContext(),mTodayAdapter.getItem(position).getUid(),
                            mTodayAdapter.getItem(position).getMatch_id(),mTodayAdapter.getItem(position).getLive_id());
                }else{
                    LiveDetailActivity.forward(getContext(), mTodayAdapter.getItem(position).getUid(),
                            mTodayAdapter.getItem(position).getMatch_id(),mTodayAdapter.getItem(position).getLive_id());
                }
            }
        });
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.layout_common_empty, null, false);
        inflate.findViewById(R.id.ll_empty).setVisibility(View.VISIBLE);
        mTodayAdapter.setEmptyView(inflate);
        rv_today.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rv_today.addItemDecoration(new GridDividerItemDecoration(getContext(), 10, 2));
        rv_today.setAdapter(mTodayAdapter);
        rv_today.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition
                    int lastVisibleItem = manager.findLastVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();
                    // 判断是否滚动到底部
                    if (lastVisibleItem == (totalItemCount - 1)) {
                        //加载更多功能的代码
                        mvpPresenter.getList(false, mTodayPage);
                    }
                }
            }
        });

        //HistoryLive
        mHistoryAdapter = new LiveRecommendHistoryAdapter(R.layout.item_live_history, new ArrayList<>());
        mHistoryAdapter.setOnItemClickListener((adapter, view, position) -> {
            String url = mHistoryAdapter.getItem(position).getMediaUrl();
            if (TextUtils.isEmpty(url)) {
                return;
            }
            if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken()) && SpUtil.getInstance().getBooleanValue(SpUtil.VIDEO_OVERTIME) && SpUtil.getInstance().getIntValue(SpUtil.LOGIN_REMIND) != 0){
                if(loginDialog!=null){
                    loginDialog.show();
                }else{
                    ToastUtil.show(getString(R.string.please_login));
                }
            }else{
//                VideoSingleActivity.forward(getContext(), mHistoryAdapter.getItem(position).getMediaUrl(), null);
                LiveDetailActivity.forward(getContext(),Integer.parseInt(mHistoryAdapter.getItem(position).getUid()),mHistoryAdapter.getItem(position).getMatchId(),
                        mHistoryAdapter.getItem(position).getMediaUrl(),mHistoryAdapter.getItem(position).getLive_id());
            }
        });
        View inflate2 = LayoutInflater.from(getContext()).inflate(R.layout.layout_common_empty, null, false);
        inflate2.findViewById(R.id.ll_empty).setVisibility(View.VISIBLE);
        mHistoryAdapter.setEmptyView(inflate2);
        rv_history.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rv_history.addItemDecoration(new GridDividerItemDecoration(getContext(), 10, 2));
        rv_history.setAdapter(mHistoryAdapter);

        initMatchList();

        smart_rl.autoRefresh();
//        mvpPresenter.getRecommendList();
//        mvpPresenter.getBannerList(-1);

        //预约赛事 未用到
/*        mMatchAdapter = new LiveRecommendMatchAdapter(R.layout.item_live_recommend_match, new ArrayList<>());
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
        });*/
        //FreeLive
/*        mAdapter = new LiveRecommendAdapter(R.layout.item_live_recommend, new ArrayList<>());
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken()) && SpUtil.getInstance().getBooleanValue(SpUtil.VIDEO_OVERTIME) && SpUtil.getInstance().getIntValue(SpUtil.LOGIN_REMIND) != 0){
                    LoginActivity.forward(getContext());
                }else{
                    LiveDetailActivity.forward(getContext(), mAdapter.getItem(position).getUid(), mAdapter.getItem(position).getType(), mAdapter.getItem(position).getMatch_id());
                }
            }
        });
        rv_live.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rv_live.addItemDecoration(new GridDividerItemDecoration(getContext(), 10, 2));
        rv_live.setAdapter(mAdapter);*/
    }

    private void initMatchList(){
        //今日直播赛事
        mTodayMatchAdapter = new LiveMatchAdapter(R.layout.item_live_today, new ArrayList<>());
        mTodayMatchAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken()) && SpUtil.getInstance().getBooleanValue(SpUtil.VIDEO_OVERTIME) && SpUtil.getInstance().getIntValue(SpUtil.LOGIN_REMIND) != 0){
                if(loginDialog!=null){
                    loginDialog.show();
                }else{
                    ToastUtil.show(getString(R.string.please_login));
                }
            }else{
                if (mTodayMatchAdapter.getItem(position).getIslive() == 0) {
                    LiveNotStartDetailActivity.forward(getContext(),mTodayMatchAdapter.getItem(position).getUid(),
                            mTodayMatchAdapter.getItem(position).getMatch_id(),mTodayMatchAdapter.getItem(position).getLive_id());
                }else{
                    LiveDetailActivity.forward(getContext(),mTodayMatchAdapter.getItem(position).getUid(),
                            mTodayMatchAdapter.getItem(position).getMatch_id(),mTodayMatchAdapter.getItem(position).getLive_id());
                }
            }

        });
        rv_match.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rv_match.setAdapter(mTodayMatchAdapter);

        //将来赛事
        mComingAdapter = new LiveMatchAdapter(R.layout.item_live_coming, new ArrayList<>());
        mComingAdapter.setOnItemClickListener((adapter, view, position) -> {
            if(mComingAdapter.getItem(position).getId()!=0 && mComingAdapter.getItem(position).getMatch_id()!=0){
                CricketDetailActivity.forward(getContext(), mComingAdapter.getItem(position).getMatch_id());
            }
        });

        rv_match_upcoming.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rv_match_upcoming.setAdapter(mComingAdapter);
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
            case R.id.tv_upcoming:
                //fixme 加上动画
                if(isOpenUpcoming){
                    tv_upcoming.setCompoundDrawables(null, null, drawableArrUp,null);
                    rv_match_upcoming.setVisibility(View.GONE);
                }else{
                    tv_upcoming.setCompoundDrawables(null, null, drawableArrDown,null);
                    rv_match_upcoming.setVisibility(View.VISIBLE);
                }
                isOpenUpcoming = !isOpenUpcoming;
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
            if (list != null && list.size() > 0) {
                mTodayAdapter.setNewData(list);
                ll_today.setVisibility(View.VISIBLE);
                rv_today.setVisibility(View.VISIBLE);
            }else{
                ll_today.setVisibility(View.GONE);
                rv_today.setVisibility(View.GONE);
            }
        } else if (list != null && list.size() > 0) {
            mTodayPage++;
            mTodayAdapter.addData(list);
            ll_today.setVisibility(View.VISIBLE);
            rv_today.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getDataHistorySuccess(boolean isRefresh, List<HistoryLiveBean> list) {
        smart_rl.finishRefresh();
        if (list == null) {return;}
        if(list.size()>6){
            mHistoryAdapter.setNewData(list.subList(0,6));
            tv_see_more_three.setVisibility(View.VISIBLE);
        }else{
            mHistoryAdapter.setNewData(list);
            tv_see_more_three.setVisibility(View.GONE);
        }
    }

    @Override
    public void getDataSuccess(List<LiveMatchBean> list) {

    }

    @Override
    public void getDataSuccess(List<LiveBean> freeList, List<LiveBean> todayList, List<LiveBean> historyList) {
        smart_rl.finishRefresh();
    }

    @Override
    public void doReserveSuccess(int position) {
    }

    @Override
    public void getBannerSuccess(List<BannerBean> list, int position) {
        if (list != null && list.size() > 0) {
            mBanner.setIndicator(new RectangleIndicator(getContext()));
            bannerAdapter = new BannerRoundImageAdapter(list) {
                @Override
                public void onBindView(Object holder, Object data, int position, int size) {
                    BannerBean bannerBean = (BannerBean) data;
                    Glide.with(getContext()).load(bannerBean.getImg()).into(((BannerRoundImageHolder) holder).imageView);
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
                    if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken()) && SpUtil.getInstance().getBooleanValue(SpUtil.VIDEO_OVERTIME) && SpUtil.getInstance().getIntValue(SpUtil.LOGIN_REMIND) != 0){
                        if(loginDialog!=null){
                            loginDialog.show();
                        }else{
                            ToastUtil.show(getString(R.string.please_login));
                        }
                    }else{
                        LiveDetailActivity.forward(getContext(), bannerBean.getAnchor_id(),
                                bannerBean.getParam_id(),bannerBean.getLive_id());
                    }
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
    public void getMatchSuccess(List<LiveMatchListBean.MatchItemBean> today,List<LiveMatchListBean.MatchItemBean> upcoming) {
        if (upcoming != null) {
            upcoming.add(new LiveMatchListBean.MatchItemBean());
            mComingAdapter.setNewData(upcoming);
        }
        if (today != null && today.size()>0) {
            mTodayMatchAdapter.setNewData(today);
            isOpenUpcoming = false;
            rv_match_upcoming.setVisibility(View.GONE);
            rv_match.setVisibility(View.VISIBLE);
        }else{
            //今日直播列表为空， 默认展开upComing
            isOpenUpcoming = true;
            rv_match_upcoming.setVisibility(View.VISIBLE);
            rv_match.setVisibility(View.GONE);
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
