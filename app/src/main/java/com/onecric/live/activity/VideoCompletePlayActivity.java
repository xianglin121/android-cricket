package com.onecric.live.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;

import com.onecric.live.R;
import com.onecric.live.view.BaseActivity;
import com.tencent.liteav.demo.superplayer.SuperPlayerDef;
import com.tencent.liteav.demo.superplayer.SuperPlayerView;

public class VideoCompletePlayActivity extends BaseActivity {
    public static void forward(Context context, String url) {
        Intent intent = new Intent(context, VideoCompletePlayActivity.class);
        intent.putExtra("videoUrl", url);
        context.startActivity(intent);
    }

    private SuperPlayerView mPlayer;

    @Override
    public int getLayoutId() {
        return R.layout.activity_video_complete_play;
    }

    @Override
    protected void initView() {
        showSysBar();
        String url = getIntent().getStringExtra("videoUrl");
        mPlayer = findViewById(R.id.playView);
        mPlayer.setPlayerViewCallback(new SuperPlayerView.OnSuperPlayerViewCallback() {
            @Override
            public void onStartFullScreenPlay() {

            }

            @Override
            public void onStopFullScreenPlay() {
            }

            @Override
            public void onClickFloatCloseBtn() {

            }

            @Override
            public void onClickSmallReturnBtn() {
                finish();
            }

            @Override
            public void onStartFloatWindowPlay() {

            }
        });
        mPlayer.play(url);
    }

    @Override
    protected void initData() {

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

    @Override
    protected void onPause() {
        super.onPause();
        if (mPlayer.getPlayerMode() != SuperPlayerDef.PlayerMode.FLOAT) {
            mPlayer.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPlayer.getPlayerState() == SuperPlayerDef.PlayerState.PLAYING
                || mPlayer.getPlayerState() == SuperPlayerDef.PlayerState.PAUSE) {
            mPlayer.onResume();
            if (mPlayer.getPlayerMode() == SuperPlayerDef.PlayerMode.FLOAT) {
                mPlayer.switchPlayMode(SuperPlayerDef.PlayerMode.WINDOW);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayer.release();
        if (mPlayer.getPlayerMode() != SuperPlayerDef.PlayerMode.FLOAT) {
            mPlayer.resetPlayer();
        }
    }
}
