package com.onecric.live.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import com.onecric.live.AppManager;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.BaseActivity;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import pro.piwik.sdk.extra.TrackHelper;

public class VideoSingleActivity extends BaseActivity {

    private StandardGSYVideoPlayer videoView;
    private String url;
    private String img;
    private OrientationUtils orientationUtils;

    //未登录用户倒计时三分钟跳转登录页
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

    public static void forward(Context context, String url,String img) {
        Intent starter = new Intent(context, VideoSingleActivity.class);
        starter.putExtra("url", url);
        starter.putExtra("img", url);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_video_single;
    }

    @Override
    protected void initView() {
        showSysBar();
        url = getIntent().getStringExtra("url");
        img = getIntent().getStringExtra("img");
        videoView = findViewById(R.id.video_view);
        //封面
/*        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        GlideUtil.loadImageDefault(this, img, imageView);
        videoPlayer.setThumbImageView(imageView);*/

        //配置url
        videoView.setLooping(true);
        videoView.setUp(url, true, "");
        //播放视频统计
        TrackHelper.track().impression("Android content impression").piece("video").target(url).with(((AppManager) getApplication()).getTracker());
        //设置返回键
        videoView.getBackButton().setVisibility(View.VISIBLE);
        //设置旋转
        orientationUtils = new OrientationUtils(this, videoView);
        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        videoView.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orientationUtils.resolveByClick();
            }
        });
        //是否可以滑动调整
        videoView.setIsTouchWiget(true);
        //设置返回按键功能
        videoView.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        videoView.startPlayLogic();
    }

    @Override
    protected void initData() {
        if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
            mCountDownTimer.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!isFinishing()) {
            videoView.onVideoPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
        videoView.onVideoResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //设置音量键控制媒体音量
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        //不需要回归竖屏
        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            videoView.getFullscreenButton().performClick();
            return;
        }
        //释放所有
        videoView.setVideoAllCallBack(null);
        super.onBackPressed();
    }

    private void showSysBar() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}
