package com.onecric.live.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.onecric.live.AppManager;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.HttpConstant;
import com.onecric.live.R;
import com.onecric.live.adapter.VideoPagerAdapter;
import com.onecric.live.adapter.layoutmanager.OnVideoViewPagerListener;
import com.onecric.live.adapter.layoutmanager.VideoViewPagerLayoutManager;
import com.onecric.live.custom.InputVideoCommentMsgDialog;
import com.onecric.live.custom.VideoCommentDialog;
import com.onecric.live.event.UpdateVideoLikeEvent;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.ReportBean;
import com.onecric.live.model.ShortVideoBean;
import com.onecric.live.presenter.video.VideoPagerPresenter;
import com.onecric.live.util.DialogUtil;
import com.onecric.live.util.DownloadUtil;
import com.onecric.live.util.ShareUtil;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.video.VideoPagerView;
//import com.pili.pldroid.player.PLOnPreparedListener;
//import com.pili.pldroid.player.widget.PLVideoView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

//import pro.piwik.sdk.extra.TrackHelper;

public class VideoPagerActivity extends MvpActivity<VideoPagerPresenter> implements VideoPagerView, View.OnClickListener {

    private RecyclerView rv;

    private SmartRefreshLayout refreshLayout;

    private VideoViewPagerLayoutManager mLayoutManager;
    private VideoPagerAdapter videoPagerAdapter;
    private int defaultIndex, currentIndex;
    private int mPage;
    private VideoPagerAdapter.VideoPagerHolder videoPagerHolder;
    private boolean noMoreData;
    public boolean isRequesting;
    private SeekBar seekBar;
    private OrientationUtils orientationUtils;
    //    private Handler handler = new Handler(Looper.getMainLooper()) {
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            long pos;
//            switch (msg.what) {
//                case 0:
////                    if (!videoPagerHolder.videoView.isPlaying()) {
////                        return;
////                    }
//                    pos = setProgress();
//                    if (pos == -1) {
//                        return;
//                    }
//                    msg = obtainMessage(0);
//                    sendMessageDelayed(msg, 50);
//                    break;
//            }
//        }
//    };
    private VideoCommentDialog mCommentDialog;
    private InputVideoCommentMsgDialog inputVideoCommentMsgDialog;

    private Dialog mLoadingDialog;

    private List<ReportBean> mReportList;//????????????

    //????????????????????????????????????????????????
    private CountDownTimer mCountDownTimer = new CountDownTimer(180000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            ToastUtil.show(getString(R.string.tip_login_to_live));
            finish();
            LoginActivity.forward(mActivity);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        pauseVideoView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (videoPagerHolder != null) {
//            videoPagerHolder.videoView.release();
//        }
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        GSYVideoManager.releaseAllVideos();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (videoPagerHolder != null && videoPagerHolder.videoView != null)
            videoPagerHolder.videoView.onVideoResume();
    }


    public static void forward(Context context, List<ShortVideoBean> data, int index, int page) {
        Intent starter = new Intent(context, VideoPagerActivity.class);
        starter.putExtra("data", JSON.toJSONString(data));
        starter.putExtra("index", index);
        starter.putExtra("page", page);
        context.startActivity(starter);
    }

    @Override
    protected VideoPagerPresenter createPresenter() {
        return new VideoPagerPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //?????????????????????????????????
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_video_pager;
    }

    @Override
    protected void initView() {
        defaultIndex = getIntent().getIntExtra("index", 0);
        currentIndex = defaultIndex;
        mPage = getIntent().getIntExtra("page", 0);
        mPage++;

        refreshLayout = findViewById(R.id.smart_rl);
        rv = findViewById(R.id.recyclerView);
        seekBar = findViewById(R.id.seekBar);

        findViewById(R.id.iv_back).setOnClickListener(this);

        //?????????????????????
        mCommentDialog = new VideoCommentDialog(this, R.style.dialog);
        //????????????????????????
        mLoadingDialog = DialogUtil.loadingDialog(this, getString(R.string.downloading));
    }

    @Override
    protected void initData() {
        seekBar.setMax(1000);
        initRecycle();
        if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
            mvpPresenter.getReportList();
        }

        if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
            mCountDownTimer.start();
        }
    }

    private long setProgress() {
        if (videoPagerHolder == null) {
            return 0;
        }

        long position = videoPagerHolder.videoView.getPlayPosition();
        long duration = videoPagerHolder.videoView.getDuration();
        if (seekBar != null) {
            if (duration > 0) {
                long pos = 1000L * position / duration;
                seekBar.setProgress((int) pos);
            }
            int percent = videoPagerHolder.videoView.getBuffterPoint();
            seekBar.setSecondaryProgress(percent * 10);
        }
        return position;
    }

    /**
     * ??????videoView
     */
    private void pauseVideoView() {
        if (videoPagerHolder != null && !isFinishing()) {
            videoPagerHolder.videoView.onVideoPause();
            videoPagerHolder.videoView.setTag(true);
//            videoPagerHolder.mPauseIv.setVisibility(View.VISIBLE);
        }
    }

    private void initRecycle() {
        mLayoutManager = new VideoViewPagerLayoutManager(this, OrientationHelper.VERTICAL);
        mLayoutManager.setOnViewPagerListener(new OnVideoViewPagerListener() {
            @Override
            public void onInitComplete() {
                ShortVideoBean bean = videoPagerAdapter.getItem(defaultIndex);
                playVideo(bean);
            }

            @Override
            public void onPageRelease(View view) {
                VideoPagerAdapter.VideoPagerHolder videoPagerHolder = (VideoPagerAdapter.VideoPagerHolder) rv.getChildViewHolder(view);
                if (videoPagerHolder != null) {
                    videoPagerAdapter.resetViewHolder(videoPagerHolder);
                }
            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {
                ShortVideoBean bean = videoPagerAdapter.getItem(position);
                currentIndex = position;
                playVideo(bean);
                isBottom = videoPagerAdapter.getItemCount() - 1 - position <= 5;
                if (isBottom && !noMoreData && !isRequesting) {
                    mvpPresenter.getList(false, mPage);
                }
            }
        });
        videoPagerAdapter = new VideoPagerAdapter(this) {
            @Override
            public void play() {
                playVideo(videoPagerAdapter.getItem(currentIndex));
            }

            @Override
            public void showLoadingDialog() {
                VideoPagerActivity.this.showLoadingDialog();
            }

            @Override
            public void dismissLoadingDialog() {
                VideoPagerActivity.this.dismissLoadingDialog();
            }
        };
        List<ShortVideoBean> data = JSON.parseObject(getIntent().getStringExtra("data"), new TypeReference<List<ShortVideoBean>>() {
        });
        videoPagerAdapter.setBeans(data, false);
        rv.setLayoutManager(mLayoutManager);
        rv.setAdapter(videoPagerAdapter);
        rv.scrollToPosition(defaultIndex);
        mLayoutManager.setPosition(defaultIndex);

        refreshLayout.setEnableOverScrollDrag(false);
        refreshLayout.setEnableOverScrollBounce(false);
        refreshLayout.setEnableAutoLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(0);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (!isRequesting) {
                    mvpPresenter.getList(false, mPage);
                }
            }
        });

        if (defaultIndex == data.size() - 1) {
            mvpPresenter.getList(false, mPage);
        }
    }

    @Override
    public void getDataSuccess(boolean isRefresh, List<ShortVideoBean> list) {
        mPage++;
        if (list != null && list.size() > 0) {
            if (refreshLayout != null) {
                refreshLayout.finishLoadMore();
            }
            videoPagerAdapter.setBeans(list, false);
        } else {
            if (refreshLayout != null) {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
            noMoreData = true;
        }
    }

    @Override
    public void getReportListSuccess(List<ReportBean> list) {
        if (list != null) {
            mReportList = list;
        }
    }

    @Override
    public void doCommentSuccess(Integer cid) {
        if (cid != null) {
            mCommentDialog.updateReplyList(cid);
        } else {
            if (videoPagerHolder != null) {
                int commentCount = videoPagerAdapter.getItem(currentIndex).getComment_count();
                commentCount++;
                videoPagerAdapter.getItem(currentIndex).setComment_count(commentCount);
                videoPagerHolder.tv_comment_count.setText(String.valueOf(commentCount));
            }
            mCommentDialog.updateList(videoPagerAdapter.getItem(currentIndex).getId());
        }
    }

    @Override
    public void doReportSuccess() {
        ToastUtil.show(getString(R.string.tip_report_success));
    }

    @Override
    public void getDataSuccess(JsonBean model) {

    }

    @Override
    public void getDataFail(String msg) {

    }

    public void doComment(String content, Integer cid) {
        mvpPresenter.doComment(videoPagerAdapter.getItem(currentIndex).getId(), content, cid);
    }

    public void showInputDialog(int type, Integer cid) {
        inputVideoCommentMsgDialog = new InputVideoCommentMsgDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        if (cid != null) {
            bundle.putInt("cid", cid);
        }
        inputVideoCommentMsgDialog.setArguments(bundle);
        inputVideoCommentMsgDialog.show(getSupportFragmentManager(), "InputVideoCommentMsgDialog");
    }

    /**
     * ????????????
     */
    private synchronized void playVideo(final ShortVideoBean bean) {
        if (bean.getVideo() == null) {
            return;
        }
        if (bean.getVideo().size() == 0) {
            return;
        }
        //???????????????????????????
//        handler.removeCallbacksAndMessages(null);
//        seekBar.setProgress(0);

        if (videoPagerHolder != null) {
//            videoPagerHolder.clickView.setOnClickListener(null);
            videoPagerHolder.videoView.release();
        }

        final String url = bean.getVideo().get(0).getVideo();

        VideoPagerAdapter.VideoPagerHolder holder = null;
        for (int i = 0; i < rv.getChildCount(); i++) {
            VideoPagerAdapter.VideoPagerHolder viewHolder =
                    (VideoPagerAdapter.VideoPagerHolder) rv.getChildViewHolder(rv.getChildAt(i));
            if (viewHolder.position == currentIndex) {
                holder = viewHolder;
                break;
            }
        }
        if (holder == null) {
            holder = (VideoPagerAdapter.VideoPagerHolder) rv.getChildViewHolder(rv.getChildAt(0));
        }
        videoPagerHolder = holder;

        holder.videoView.setLooping(true);
        holder.videoView.setUp(url, true, "");
        //??????????????????
//        TrackHelper.track().impression("Android content impression").piece("video").target(url).with(((AppManager) getApplication()).getTracker());
        //???????????????
        holder.videoView.getBackButton().setVisibility(View.VISIBLE);
        //????????????
        orientationUtils = new OrientationUtils(this, holder.videoView);
        //????????????????????????,????????????????????????????????????????????????
        holder.videoView.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ------- ???????????????????????????????????????????????????????????????-------
                // ??????????????????????????????????????? setNeedOrientationUtils(false)
                orientationUtils.resolveByClick();
            }
        });
        //????????????????????????
        holder.videoView.setIsTouchWiget(true);
        //????????????????????????
        holder.videoView.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        ///?????????????????????
//        holder.videoView.setNeedOrientationUtils(false);

        holder.videoView.startPlayLogic();

//
//        holder.videoView.setUp(url, true, "");
////        holder.videoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_FIT_PARENT);
//        holder.videoView.setLooping(true);
//        holder.videoView.startPlayLogic();
//        holder.videoView.setTag(false);
//        holder.videoView.setVideoAllCallBack(new VideoAllCallBack() {
//            @Override
//            public void onStartPrepared(String url, Object... objects) {
//
//            }
//
//            @Override
//            public void onPrepared(String url, Object... objects) {
//                handler.sendEmptyMessage(0);//?????????????????????
//                AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0f);
//                alphaAnimation.setDuration(300);
//                alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
//                    @Override
//                    public void onAnimationStart(Animation animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animation animation) {
//                        videoPagerHolder.coverImage.setVisibility(View.GONE);
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animation animation) {
//
//                    }
//                });
//                videoPagerHolder.coverImage.startAnimation(alphaAnimation);
//            }
//
//            @Override
//            public void onClickStartIcon(String url, Object... objects) {
//
//            }
//
//            @Override
//            public void onClickStartError(String url, Object... objects) {
//
//            }
//
//            @Override
//            public void onClickStop(String url, Object... objects) {
//
//            }
//
//            @Override
//            public void onClickStopFullscreen(String url, Object... objects) {
//
//            }
//
//            @Override
//            public void onClickResume(String url, Object... objects) {
//
//            }
//
//            @Override
//            public void onClickResumeFullscreen(String url, Object... objects) {
//
//            }
//
//            @Override
//            public void onClickSeekbar(String url, Object... objects) {
//
//            }
//
//            @Override
//            public void onClickSeekbarFullscreen(String url, Object... objects) {
//
//            }
//
//            @Override
//            public void onAutoComplete(String url, Object... objects) {
//
//            }
//
//            @Override
//            public void onComplete(String url, Object... objects) {
//
//            }
//
//            @Override
//            public void onEnterFullscreen(String url, Object... objects) {
//            }
//
//            @Override
//            public void onQuitFullscreen(String url, Object... objects) {
//
//            }
//
//            @Override
//            public void onQuitSmallWidget(String url, Object... objects) {
//
//            }
//
//            @Override
//            public void onEnterSmallWidget(String url, Object... objects) {
//
//            }
//
//            @Override
//            public void onTouchScreenSeekVolume(String url, Object... objects) {
//
//            }
//
//            @Override
//            public void onTouchScreenSeekPosition(String url, Object... objects) {
//
//            }
//
//            @Override
//            public void onTouchScreenSeekLight(String url, Object... objects) {
//
//            }
//
//            @Override
//            public void onPlayError(String url, Object... objects) {
//
//            }
//
//            @Override
//            public void onClickStartThumb(String url, Object... objects) {
//
//            }
//
//            @Override
//            public void onClickBlank(String url, Object... objects) {
//
//            }
//
//            @Override
//            public void onClickBlankFullscreen(String url, Object... objects) {
//
//            }
//        });
        holder.iv_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getUid())) {
                    LoginActivity.forward(mActivity);
                    return;
                }
                if (bean.getUid() != Integer.parseInt(CommonAppConfig.getInstance().getUid())) {
                    if (bean.getIs_attention() == 0) {
                        videoPagerHolder.iv_follow.setFollow(true);
                        mvpPresenter.doFollow(bean.getUid());
                    }
                }
            }
        });
        holder.iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getUid())) {
                    LoginActivity.forward(mActivity);
                    return;
                }
                int likeCount = bean.getLikes();
                if (bean.getIs_likes() == 1) {
                    bean.setIs_likes(0);
                    videoPagerHolder.iv_like.setSelected(false);
                    likeCount--;
                } else {
                    bean.setIs_likes(1);
                    videoPagerHolder.iv_like.setSelected(true);
                    likeCount++;
                }
                bean.setLikes(likeCount);
                videoPagerHolder.tv_like_count.setText(String.valueOf(likeCount));
                mvpPresenter.doVideoLike(bean.getId());
//                TrackHelper.track().socialInteraction("Like", "Video_user").target("onecric.live.app").with(((AppManager) getApplication()).getTracker());
                EventBus.getDefault().post(new UpdateVideoLikeEvent(bean.getId()));
            }
        });
        holder.iv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCommentDialog.updateList(bean.getId());
                mCommentDialog.show();
            }
        });
        holder.iv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getUid())) {
                    LoginActivity.forward(mActivity);
                    return;
                }
                DialogUtil.showVideoMoreDialog(mActivity, new DialogUtil.SelectMoreCallback() {
                    @Override
                    public void onClick(int type) {
                        if (type == 0) {//??????
                            if (bean.getIs_favorites() == 1) {
                                ToastUtil.show(getString(R.string.collection_cancel));
                                bean.setIs_favorites(0);
                            } else {
                                ToastUtil.show(getString(R.string.collection_success));
                                bean.setIs_favorites(1);
                            }
                            mvpPresenter.doVideoCollect(bean.getId());
                        } else if (type == 1) {//??????
                            mLoadingDialog.show();
                            String fileName = "LY_VIDEO_" + System.currentTimeMillis() + ".mp4";
                            DownloadUtil.get().download(mActivity, url, DownloadUtil.VIDEO_PATH, fileName, new DownloadUtil.OnDownloadListener() {
                                @Override
                                public void onDownloadSuccess() {
                                    mLoadingDialog.dismiss();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToastUtil.show(getString(R.string.video_download_success));
                                        }
                                    });
                                }

                                @Override
                                public void onDownloading(int progress) {

                                }

                                @Override
                                public void onDownloadFailed() {
                                    mLoadingDialog.dismiss();
                                }
                            });
                        } else if (type == 2) {//??????
                            if (mReportList != null && mReportList.size() > 0) {
                                SparseArray<String> array = new SparseArray<>();
                                for (int i = 0; i < mReportList.size(); i++) {
                                    array.put(mReportList.get(i).getId(), mReportList.get(i).getName());
                                }
                                DialogUtil.showStringArrayDialog(mActivity, array, new DialogUtil.StringArrayDialogCallback() {
                                    @Override
                                    public void onItemClick(String text, int tag) {
                                        mvpPresenter.doReport(tag, bean.getId());
                                    }
                                });
                            }
                        } else if (type == 3) {//??????
                            ShareUtil.shareText(mActivity, "", HttpConstant.SHORT_VIDEO_URL + bean.getId());
                        }
                    }
                });
            }
        });
        mvpPresenter.doWatch(bean.getId());
    }

    private boolean isPause() {
        return videoPagerHolder.videoView.getTag() != null && (Boolean) videoPagerHolder.videoView.getTag();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
//      ?????????????????????
        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            videoPagerHolder.videoView.getFullscreenButton().performClick();
            return;
        }
        //????????????
        videoPagerHolder.videoView.setVideoAllCallBack(null);
        super.onBackPressed();
    }
}