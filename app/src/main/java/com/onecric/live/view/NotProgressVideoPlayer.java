package com.onecric.live.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

public class NotProgressVideoPlayer extends StandardGSYVideoPlayer {
    public NotProgressVideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public NotProgressVideoPlayer(Context context) {
        super(context);
    }

    public NotProgressVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);

    }


    @Override
    protected void onClickUiToggle(MotionEvent e) {
        //无触摸事件
    }

    @Override
    protected void showProgressDialog(float deltaX, String seekTime, int seekTimePosition, String totalTime, int totalTimeDuration) {

    }

    @Override
    protected void dismissProgressDialog() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //静音
        GSYVideoManager.instance().setNeedMute(true);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;

    }

}
