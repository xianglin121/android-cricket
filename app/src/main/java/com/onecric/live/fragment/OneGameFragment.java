package com.onecric.live.fragment;

import static com.onecric.live.HttpConstant.SHARE_LIVE_URL;
import static com.onecric.live.util.TimeUtil.toToday;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.PowerManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.Constant;
import com.onecric.live.R;
import com.onecric.live.activity.AnchorListActivity;
import com.onecric.live.activity.LiveDetailActivity2;
import com.onecric.live.activity.LiveMoreActivity;
import com.onecric.live.activity.LiveNotStartDetailActivity;
import com.onecric.live.activity.OneLogInActivity;
import com.onecric.live.activity.PersonalHomepageActivity;
import com.onecric.live.adapter.BannerGameLiveImageAdapter;
import com.onecric.live.adapter.BannerRoundLiveImage2Adapter;
import com.onecric.live.adapter.LiveAuthorAdapter;
import com.onecric.live.adapter.LiveGameHistoryAdapter;
import com.onecric.live.model.BannerBean;
import com.onecric.live.model.GameBannerBean;
import com.onecric.live.model.GameHistoryBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.LiveAuthorBean;
import com.onecric.live.presenter.live.OneGamePresenter;
import com.onecric.live.util.DialogUtil;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.util.ShareUtil;
import com.onecric.live.util.SpUtil;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.live.OneGameView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.tencent.liteav.demo.superplayer.LivePlayerView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class OneGameFragment extends MvpFragment<OneGamePresenter> implements OneGameView, View.OnClickListener{
    private Banner mBanner,mBannerAdvert;
    private TextView tv_see_all;
    private RecyclerView rv_author_live,rv_history;
    private SmartRefreshLayout smart_rl;
    private BannerRoundLiveImage2Adapter bannerAdapter;
    private BannerGameLiveImageAdapter bannerRoundLiveImageAdapter;
    private LiveAuthorAdapter mAnchorAdapter;
    private LiveGameHistoryAdapter mHistoryAdapter;
    private Timer mTimer;
    private int mPage = 1;
    public LivePlayerView showPlayerView;
    public StandardGSYVideoPlayer history_video_view;
    public boolean bannerCheckedIsLive;
    private List<GameBannerBean> bannerList;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_view_all_history:
                LiveMoreActivity.forward(getContext(), 3);
                break;
            case R.id.tv_author_see_more:
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                    ToastUtil.show(getString(R.string.please_login));
                    OneLogInActivity.forward(getContext());
                } else {
                    AnchorListActivity.forward(getContext());
                }
                break;
        }
    }

    @Override
    protected void initUI() {
        mBanner = findViewById(R.id.banner_live);
        mBannerAdvert = findViewById(R.id.banner_advert);
        tv_see_all = findViewById(R.id.tv_see_all);
        rv_history = findViewById(R.id.rv_history);
        rv_author_live = findViewById(R.id.rv_author_live);
        smart_rl = findViewById(R.id.smart_rl);
        findViewById(R.id.tv_view_all_history).setOnClickListener(this);
        findViewById(R.id.tv_author_see_more).setOnClickListener(this);

        int width = UIUtil.getScreenWidth(getContext());
        android.view.ViewGroup.LayoutParams pp = mBanner.getLayoutParams();
//        pp.height = (int)((width*0.9-20) * 0.48);//0.78
        pp.height = (int)((width-60) * 0.5625);
        mBanner.setLayoutParams(pp);

        android.view.ViewGroup.LayoutParams pp4 = mBannerAdvert.getLayoutParams();
        pp4.height = (int) (UIUtil.getScreenWidth(getContext())/4.09);//4.09:1
//        mBannerAdvert.setLayoutParams(pp4);

        //rv
        MaterialHeader materialHeader = new MaterialHeader(getContext());
        materialHeader.setColorSchemeColors(getResources().getColor(R.color.c_DC3C23));
        smart_rl.setRefreshHeader(materialHeader);
        smart_rl.setRefreshFooter(new ClassicsFooter(getContext()));
        smart_rl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getHistoryList(false, mPage);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (!bannerCheckedIsLive && history_video_view!=null) {
                    GSYVideoManager.releaseAllVideos();
                    GSYVideoManager.instance().clearAllDefaultCache(getContext());
                    history_video_view = null;
                }
                if (bannerCheckedIsLive && showPlayerView!=null) {
                    showPlayerView.resetPlayer();
                    showPlayerView = null;
                }
                mvpPresenter.getAllData();
                mvpPresenter.getHistoryList(true,1);
            }
        });

        initList();
        smart_rl.autoRefresh();
    }

    private void initList(){
        mAnchorAdapter = new LiveAuthorAdapter(R.layout.item_live_anchor_2, new ArrayList<>());
        mAnchorAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PersonalHomepageActivity.forward(getContext(), ((LiveAuthorBean)(adapter.getItem(position))).id + "");
            }
        });
        rv_author_live.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rv_author_live.setAdapter(mAnchorAdapter);


        mHistoryAdapter = new LiveGameHistoryAdapter(R.layout.item_game_history,new ArrayList<>());
        View inflate3 = LayoutInflater.from(getContext()).inflate(R.layout.item_empty_error3, null, false);
        mHistoryAdapter.setEmptyView(inflate3);
        mHistoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GameHistoryBean bean = mHistoryAdapter.getItem(position);
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken()) && SpUtil.getInstance().getBooleanValue(SpUtil.VIDEO_OVERTIME) && SpUtil.getInstance().getIntValue(SpUtil.LOGIN_REMIND) != 0){
                    OneLogInActivity.forward(getContext());
                } else if(!TextUtils.isEmpty(bean.video)){
                    LiveDetailActivity2.forward(getContext(), bean.uid,bean.video,bean.liveId);
                }else{
                    //fixme 待开播怎么区分？
                    LiveNotStartDetailActivity.forward(getContext(), bean.uid,0,bean.id);
                }
            }
        });
        mHistoryAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                GameHistoryBean item = mHistoryAdapter.getItem(position);
                switch (view.getId()){
                    case R.id.ll_heart:
                        if (item.isLikes == 0) {
                            item.isLikes = 1;
                            item.like+=1;
                        } else {
                            item.isLikes = 0;
                            item.like-=1;
                        }
                        mHistoryAdapter.notifyItemChanged(position, Constant.PAYLOAD);
                        mvpPresenter.doLike(item.id, item.isLikes);
                        break;
                    case R.id.iv_follow:
                        if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                            ToastUtil.show(getString(R.string.please_login));
                            OneLogInActivity.forward(getActivity());
                            return;
                        }
                        mvpPresenter.doFollow(item.uid,item.isAttention == 0?true:false);
//                        mHistoryAdapter.forFollowedStatus(item.uid,item.isAttention==0?1:0);
                        mHistoryAdapter.notifyItemChanged(position, Constant.PAYLOAD);
                        break;
                    case R.id.ll_share:
                        if(TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())){
                            DialogUtil.showSimpleTransDialog(getContext(),getString(R.string.not_login_tip),false,true);
                        }else{
                            ShareUtil.shareText(getContext(), "", SHARE_LIVE_URL + "pages/Live/live-detail?id=" + item.uid + "&ID=" + item.liveId);
                        }
                        break;
                    case R.id.iv_avatar:
                        PersonalHomepageActivity.forward(getContext(), item.uid + "");
                        break;
                }

            }
        });
        rv_history.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_history.setAdapter(mHistoryAdapter);
    }

    @Override
    protected void initData() {
//        refreshTodayData();
    }

    @Override
    public void getAllDataSuccess(List<LiveAuthorBean> aList) {
        smart_rl.finishRefresh();
        if (aList != null && aList.size()>0) {
            mAnchorAdapter.setNewData(aList);
        }else{
            mAnchorAdapter.setNewData(new ArrayList<>());
        }
    }

    @Override
    public void getBannerSuccess(List<GameBannerBean> list) {
        if (list != null && list.size() > 0) {
            mBanner.setVisibility(View.VISIBLE);
            mBanner.isAutoLoop(false);
            //添加画廊效果
            mBanner.setBannerGalleryMZ(10, 0);

            bannerList = list;
            bannerRoundLiveImageAdapter = new BannerGameLiveImageAdapter(getActivity(),bannerList) {
                @Override
                public void onBindView(Object holder, Object data, int position, int size) {
                    GameBannerBean bannerBean = (GameBannerBean) data;
                    Glide.with(getContext()).load(bannerBean.thumb).priority(Priority.HIGH).into(((BannerRoundLiveImageHolder) holder).imageView);
                    GlideUtil.loadUserImageDefault(getContext(),bannerBean.thumb,((BannerRoundLiveImageHolder) holder).iv_avatar);
                    ((BannerRoundLiveImageHolder) holder).iv_live_status.setVisibility(View.VISIBLE);
                    int eyeNum = bannerBean.viewers;
                    ((BannerRoundLiveImageHolder) holder).tv_eyes_num.setText(eyeNum > 1000 ? String.format("%.1f", (float) eyeNum / 1000) + "K" : eyeNum + "");
                    ((BannerRoundLiveImageHolder) holder).tv_title.setText(bannerBean.title);
                    ((BannerRoundLiveImageHolder) holder).tv_bottom.setText(bannerBean.userNickname+"・"+(bannerBean.addtime==0?"":toToday(bannerBean.addtime)));

                    ((BannerRoundLiveImageHolder) holder).imageView.setVisibility(View.VISIBLE);
                    ((BannerRoundLiveImageHolder) holder).playerView.setVisibility(View.GONE);
                    ((BannerRoundLiveImageHolder) holder).videoView.setVisibility(View.GONE);
                }
            };

            mBanner.getViewPager2().registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            bannerRoundLiveImageAdapter.setmHolder(position);
                            if(bannerList == null || position>bannerList.size()-1){
                                return;
                            }
                            int index = position <= 0?bannerList.size()-1:position-1;
                            GameBannerBean bannerBean = bannerList.get(index);
                            bannerCheckedIsLive = bannerBean.islive==1?true:false;
                            if(bannerCheckedIsLive){
                                if (!TextUtils.isEmpty(bannerBean.pull)) {
                                    showPlayerView = bannerRoundLiveImageAdapter.getPlayerView(index,bannerBean.pull);
                                }else{
                                    showPlayerView = null;
                                }
                            }else{
                                if (!TextUtils.isEmpty(bannerBean.videoUrl)) {
                                    history_video_view = bannerRoundLiveImageAdapter.getVideoView(getContext(),index,bannerBean.videoUrl);
                                }else{
                                    history_video_view = null;
                                }
                            }
                        }}, 1000);
                }

            });
            bannerRoundLiveImageAdapter.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(Object data, int position) {
                    GameBannerBean bannerBean = (GameBannerBean) data;
                    if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken()) && SpUtil.getInstance().getBooleanValue(SpUtil.VIDEO_OVERTIME) && SpUtil.getInstance().getIntValue(SpUtil.LOGIN_REMIND) != 0){
                        OneLogInActivity.forward(getContext());
                    } else if(bannerBean.islive == 1){
                        LiveDetailActivity2.forward(getContext(), bannerBean.uid,bannerBean.id);
                    }else if(bannerBean.videoUrl == null || TextUtils.isEmpty(bannerBean.videoUrl)){
                        LiveNotStartDetailActivity.forward(getContext(), bannerBean.uid,0,bannerBean.id);
                    }else{
                        LiveDetailActivity2.forward(getContext(), bannerBean.uid,bannerBean.videoUrl,bannerBean.id);
                    }

                }
            });
            mBanner.setAdapter(bannerRoundLiveImageAdapter);
            mBanner.addBannerLifecycleObserver(this);
        }else{
            mBanner.setVisibility(View.GONE);
        }
    }

    @Override
    public void getDataHistorySuccess(boolean isRefresh,List<GameHistoryBean> list) {
        if (isRefresh) {
            smart_rl.finishRefresh();
            mPage = 2;
            if (list != null) {
                mHistoryAdapter.setNewData(list);
            }
        }else {
            mPage++;
            if (list != null && list.size() > 0) {
                smart_rl.finishLoadMore();
                mHistoryAdapter.addData(list);
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
        smart_rl.finishLoadMoreWithNoMoreData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_one_game;
    }

    @Override
    protected OneGamePresenter createPresenter() {
        return new OneGamePresenter(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mBanner != null) {
            mBanner.start();
        }
        if (mBannerAdvert != null) {
            mBannerAdvert.start();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mBanner != null) {
            mBanner.stop();
        }
        if (mBannerAdvert != null) {
            mBannerAdvert.stop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBanner != null) {
            mBanner.destroy();
        }
        if (mBannerAdvert != null) {
            mBannerAdvert.destroy();
        }
        if(mTimer != null){
            mTimer.cancel();
        }
    }

    /**
     * 每10s刷新
     */
    private void refreshTodayData(){
        final Handler handler = new Handler();
        mTimer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        if(getActivity()!=null && ((PowerManager)((getActivity().getSystemService(Context.POWER_SERVICE)))).isScreenOn() &&
                                getActivity().getWindow().getDecorView().getVisibility() == View.VISIBLE ){
                            //fixme 有无定期刷新数据

                        }
                    }
                });
            }
        };
        mTimer.schedule(doAsynchronousTask, 500, 10000);
    }

    @Override
    public void getAdvertSuccess(List<BannerBean> list){
        if (list != null && list.size() > 0) {
            mBannerAdvert.setVisibility(View.VISIBLE);
//            mBannerAdvert.setIndicator(new RectangleIndicator(getContext()));
            bannerAdapter = new BannerRoundLiveImage2Adapter(list) {
                @Override
                public void onBindView(Object holder, Object data, int position, int size) {
                    BannerBean bannerBean = (BannerBean) data;
                    if(bannerBean.getImg().indexOf(".gif")!=-1){
                        Glide.with(getActivity()).asGif().priority(Priority.HIGH).load(bannerBean.getImg()).into(((BannerRoundLiveImageHolder) holder).imageView);
                    }else{
                        Glide.with(getActivity()).load(bannerBean.getImg()).priority(Priority.HIGH).into(((BannerRoundLiveImageHolder) holder).imageView);
                    }

                }
            };
            bannerAdapter.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(Object data, int position) {
                    BannerBean bannerBean = (BannerBean) data;
                    if(!TextUtils.isEmpty(bannerBean.getUrl())){
                        mvpPresenter.clickAdvert(8,bannerBean.getId(),bannerBean.getUrl());
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(bannerBean.getUrl());
                        intent.setData(content_url);
                        startActivity(intent);
                    }

                }
            });

            if (mBannerAdvert.getAdapter() == null) {
                mBannerAdvert.setAdapter(bannerAdapter);
                mBannerAdvert.addBannerLifecycleObserver(this);
            } else {
                mBannerAdvert.getAdapter().notifyDataSetChanged();
            }
        }else{
            mBannerAdvert.setVisibility(View.GONE);
        }
    }


    @Override
    public void doFollowSuccess(int id,boolean isFollow) {
        //fixme 测试：便利adapter相同作者id的关注状态
        mHistoryAdapter.forFollowedStatus(id,isFollow?1:0);
//        mHistoryAdapter.notifyItemChanged(position, Constant.PAYLOAD);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!bannerCheckedIsLive && history_video_view!=null) {
            history_video_view.onVideoPause();
        }
        if (bannerCheckedIsLive && showPlayerView!=null) {
            showPlayerView.onPause();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (!bannerCheckedIsLive && history_video_view!=null) {
            GSYVideoManager.releaseAllVideos();
            GSYVideoManager.instance().clearAllDefaultCache(getContext());
            history_video_view = null;
        }
        if (bannerCheckedIsLive && showPlayerView!=null) {
            showPlayerView.resetPlayer();
            showPlayerView = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!bannerCheckedIsLive && history_video_view!=null) {
            GSYVideoManager.instance().setNeedMute(true);
            history_video_view.onVideoResume();
        }
        if (bannerCheckedIsLive && showPlayerView!=null) {
            showPlayerView.setMute(true);
            showPlayerView.onResume();
        }
    }

    public void updateUserInfo() {
        mPage = 1;
        mvpPresenter.getHistoryList(true,mPage);
    }

}
