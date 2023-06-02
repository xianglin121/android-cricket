package com.onecric.live.activity;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen;
import com.ethanhua.skeleton.Skeleton;
import com.google.android.exoplayer2.SeekParameters;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.adapter.VideoAllAdapter;
import com.onecric.live.event.UpdateLoginTokenEvent;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.VideoDetailBean;
import com.onecric.live.model.ViewMoreBean;
import com.onecric.live.presenter.video.VideoDetailPresenter;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.util.SpUtil;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.video.VideoDetailView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.cache.CacheFactory;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.player.PlayerFactory;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import tv.danmaku.ijk.media.exo2.Exo2PlayerManager;
import tv.danmaku.ijk.media.exo2.ExoPlayerCacheManager;

public class OneVideoDetailActivity extends MvpActivity<VideoDetailPresenter> implements VideoDetailView {

    public static void forward(Context context,String tname, int tid){
        Intent intent = new Intent(context,OneVideoDetailActivity.class);
        intent.putExtra("tournament",tname);
        intent.putExtra("tId",tid);
        context.startActivity(intent);
    }

    private String tournament;
    private int tId;
    private SmartRefreshLayout smart_rl;
    private RecyclerView recyclerview;
    private LinearLayout ll_title;
    private TextView tv_title;
    private ImageView iv_avatar;
    private RecyclerViewSkeletonScreen skeletonScreen;
    private VideoAllAdapter mRecommendAdapter;
    private StandardGSYVideoPlayer video_view;
    private ImageView iv_video_mute;
    private TextView tv_video_title,tv_video_time;
    private OrientationUtils orientationUtils;
    private CountDownTimer mCountDownTimer;
    private GSYVideoOptionBuilder gsyVideoOption;

    private int mPage = 2;

    @Override
    public int getLayoutId() {
        return R.layout.activity_video_detail;
    }

    @Override
    protected void initView() {
        tournament = getIntent().getStringExtra("tournament");
        tId = getIntent().getIntExtra("tId",0);
        if(tId == 0){
            finish();
        }

        int remind = SpUtil.getInstance().getIntValue(SpUtil.LOGIN_REMIND);
        if(remind != 0) {
            //未登录用户倒计时N分钟跳转登录页
            mCountDownTimer = new CountDownTimer(60000*remind, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    SpUtil.getInstance().setBooleanValue(SpUtil.VIDEO_OVERTIME, true);
                    ToastUtil.show(getString(R.string.tip_login_to_live));
                    OneLogInActivity.forward(mActivity);
                }
            };
        }

        smart_rl = findViewById(R.id.smart_rl);
        recyclerview = findViewById(R.id.rv_recommend);
        ll_title = findViewById(R.id.ll_title);
        tv_title = findViewById(R.id.tv_title);
        iv_avatar = findViewById(R.id.iv_avatar);

        video_view = findViewById(R.id.video_view);
        iv_video_mute = findViewById(R.id.iv_video_mute);
        tv_video_title = findViewById(R.id.tv_video_title);
        tv_video_time = findViewById(R.id.tv_video_time);

        ll_title.setVisibility(View.VISIBLE);
        tv_title.setText(R.string.main_tab_video);
        if (CommonAppConfig.getInstance().getUserBean() != null) {
            GlideUtil.loadUserImageDefault(this, CommonAppConfig.getInstance().getUserBean().getAvatar(), iv_avatar);
        } else {
            iv_avatar.setImageResource(R.mipmap.bg_avatar_default);
        }

        findViewById(R.id.iv_back).setOnClickListener(v -> {
            finish();
        });

        findViewById(R.id.iv_avatar).setOnClickListener(v -> {
            if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                OneLogInActivity.forward(mActivity);
            } else {
                if (!isFastDoubleClick())
                    PersonalHomepageActivity.forward(mActivity, CommonAppConfig.getInstance().getUid());
            }
        });

        EventBus.getDefault().register(this);



        android.view.ViewGroup.LayoutParams pp = video_view.getLayoutParams();
        pp.height = (int) (UIUtil.getScreenWidth(this) * 0.5625);
        video_view.setLayoutParams(pp);
        PlayerFactory.setPlayManager(Exo2PlayerManager.class);
        CacheFactory.setCacheManager(ExoPlayerCacheManager.class);
        orientationUtils = new OrientationUtils(this, video_view);
        orientationUtils.setEnable(false);
        video_view.getBackButton().setVisibility(View.GONE);
        video_view.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orientationUtils.resolveByClick();
                video_view.startWindowFullscreen(mActivity, true, true);
            }
        });
        iv_video_mute.setSelected(false);
        iv_video_mute.setOnClickListener(v -> {
            iv_video_mute.setSelected(!iv_video_mute.isSelected());
            GSYVideoManager.instance().setNeedMute(iv_video_mute.isSelected());
        });
        GSYVideoManager.instance().setNeedMute(false);
    }

    @Override
    protected void initData() {
        MaterialHeader materialHeader = new MaterialHeader(this);
        materialHeader.setColorSchemeColors(getResources().getColor(R.color.c_DC3C23));
        smart_rl.setRefreshHeader(materialHeader);
        smart_rl.setRefreshFooter(new ClassicsFooter(this));
        smart_rl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getMoreVideoList(tournament,mPage);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                /*if(mRecommendAdapter.getItemCount()<=0){
                    findViewById(R.id.view_loading).setVisibility(View.VISIBLE);
                }*/
                mPage = 2;
                mvpPresenter.getDetail(tournament,tId);
            }
        });

        View inflate2 = LayoutInflater.from(this).inflate(R.layout.layout_common_empty, null, false);
        inflate2.findViewById(R.id.ll_empty).setVisibility(View.VISIBLE);

        recyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecommendAdapter = new VideoAllAdapter(R.layout.item_video_all, new ArrayList<>());
        mRecommendAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken()) && SpUtil.getInstance().getBooleanValue(SpUtil.VIDEO_OVERTIME) && SpUtil.getInstance().getIntValue(SpUtil.LOGIN_REMIND) != 0){
                    OneLogInActivity.forward(mActivity);
                }else{
                    OneVideoDetailActivity.forward(mActivity,"",mRecommendAdapter.getItem(position).id);                }
            }
        });
        mRecommendAdapter.setEmptyView(inflate2);
        recyclerview.setAdapter(mRecommendAdapter);
        skeletonScreen = Skeleton.bind(recyclerview)
                .adapter(mRecommendAdapter)
                .shimmer(false)
                .count(2)
                .load(R.layout.item_live_video_skeleton)
                .show();

        if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken()) && mCountDownTimer != null) {
            mCountDownTimer.start();
        }
        smart_rl.autoRefresh();
    }

    @Override
    public void getDataSuccess(JsonBean model) {

    }

    @Override
    public void getDataFail(String msg) {
        smart_rl.finishRefresh();
        smart_rl.finishLoadMore();
        skeletonScreen.hide();
    }

    @Override
    protected VideoDetailPresenter createPresenter() {
        return new VideoDetailPresenter(this);
    }

    @Override
    public void getDataSuccess(List<ViewMoreBean> list, VideoDetailBean bean) {
        smart_rl.finishRefresh();
        skeletonScreen.hide();
        if (list != null && list.size() > 0) {
            mRecommendAdapter.setNewData(list);
        }else {
            mRecommendAdapter.setNewData(new ArrayList<>());
        }

        if(!TextUtils.isEmpty(bean.title)){
            tv_video_title.setText(bean.title);
        }
        if(!TextUtils.isEmpty(bean.addtime)){
            tv_video_time.setText(bean.addtime);
        }

        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        GlideUtil.loadImageDefault(this, bean.flie.get(0).img, imageView);
        video_view.setThumbImageView(imageView);

        if(bean.flie.size()>0 && !TextUtils.isEmpty(bean.flie.get(0).video)){
            gsyVideoOption = new GSYVideoOptionBuilder();
            gsyVideoOption
                    .setLooping(false)//循环
                    .setIsTouchWiget(true)//滑动调整
                    .setRotateViewAuto(false)
                    .setLockLand(false)
                    .setAutoFullWithSize(false)
                    .setShowFullAnimation(false)
                    .setNeedLockFull(true)
                    .setUrl(bean.flie.get(0).video)
                    .setCacheWithPlay(true)//边缓存
                    .setVideoAllCallBack(new GSYSampleCallBack() {
                        @Override
                        public void onPrepared(String url, Object... objects) {
                            super.onPrepared(url, objects);
                            orientationUtils.setEnable(video_view.isRotateWithSystem());
                            if (video_view.getGSYVideoManager().getPlayer() instanceof Exo2PlayerManager) {
                                ((Exo2PlayerManager) video_view.getGSYVideoManager().getPlayer()).setSeekParameter(SeekParameters.NEXT_SYNC);
                            }
                        }

                        @Override
                        public void onEnterFullscreen(String url, Object... objects) {
                            super.onEnterFullscreen(url, objects);
                        }

                        @Override
                        public void onAutoComplete(String url, Object... objects) {
                            super.onAutoComplete(url, objects);
                        }

                        @Override
                        public void onClickStartError(String url, Object... objects) {
                            super.onClickStartError(url, objects);
                        }

                        @Override
                        public void onQuitFullscreen(String url, Object... objects) {
                            super.onQuitFullscreen(url, objects);
                            if (orientationUtils != null) {
                                orientationUtils.backToProtVideo();
                            }
                        }
                    })
                    .setLockClickListener((view, lock) -> {
                        if (orientationUtils != null) {
                            orientationUtils.setEnable(!lock);
                        }
                    })
                    .setGSYVideoProgressListener((progress, secProgress, currentPosition, duration) -> {
                    })
                    .build(video_view);

//            gsyVideoOption.setUrl(bean.flie.get(0).video);
            video_view.startPlayLogic();
        }

    }

    @Override
    public void getVideoSuccess(List<ViewMoreBean> list) {
        mPage++;
        if (list != null && list.size() > 0) {
            smart_rl.finishLoadMore();
            mRecommendAdapter.addData(list);
        }else {
            smart_rl.finishLoadMoreWithNoMoreData();
        }
    }

    //登录成功，更新信息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateLoginTokenEvent(UpdateLoginTokenEvent event) {
        if (event != null) {
            if (mCountDownTimer != null) {
                mCountDownTimer.cancel();
            }
            smart_rl.autoRefresh();
            if (CommonAppConfig.getInstance().getUserBean() != null) {
                GlideUtil.loadUserImageDefault(this, CommonAppConfig.getInstance().getUserBean().getAvatar(), iv_avatar);
            } else {
                iv_avatar.setImageResource(R.mipmap.bg_avatar_default);
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        GSYVideoManager.releaseAllVideos();
        GSYVideoManager.instance().clearAllDefaultCache(this);
        if (orientationUtils != null) {
            orientationUtils.releaseListener();
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!isFinishing()) {
            video_view.onVideoPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        video_view.onVideoResume();
    }
}
