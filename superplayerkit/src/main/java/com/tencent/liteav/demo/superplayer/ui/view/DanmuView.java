package com.tencent.liteav.demo.superplayer.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.demo.superplayer.R;
import com.tencent.qcloud.tuikit.tuichat.component.face.FaceManager;

import java.util.HashMap;
import java.util.Random;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.AlphaValue;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.Duration;
import master.flame.danmaku.danmaku.model.FBDanmaku;
import master.flame.danmaku.danmaku.model.FTDanmaku;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.AndroidDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.model.android.SpannedCacheStuffer;
import master.flame.danmaku.danmaku.model.android.ViewCacheStuffer;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

/**
 * Created by liyuejiao on 2018/1/29.
 * <p>
 * 全功能播放器中的弹幕View
 * <p>
 * 1、随机发送弹幕{@link #addDanmaku(String, String, boolean)}
 * <p>
 * 2、弹幕操作所在线程的Handler{@link DanmuHandler}
 */
public class DanmuView extends DanmakuView {
    public static final int TYPE_ON = 0;//打开弹幕
    public static final int TYPE_HALF = 1;//半屏弹幕
    public static final int TYPE_OFF = 2;//关掉弹幕
    public static final int TYPE_BOTTOM = 3;//底部弹幕

    private Context        mContext;
    private DanmakuContext mDanmakuContext;
    private boolean        mShowDanma;         // 弹幕是否开启
    private HandlerThread  mHandlerThread;     // 发送弹幕的线程
    private DanmuHandler   mDanmuHandler;      // 弹幕线程handler

    private HashMap<Integer, Integer> maxLinesPair = new HashMap<Integer, Integer>();
    private float mAlpha = 1f;
    private float mTextSize = 12f;

    public DanmuView(Context context) {
        super(context);
        init(context);
    }

    public DanmuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DanmuView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }


    private void init(Context context) {
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 5); // 滚动弹幕最大显示5行
        mContext = context;
        enableDanmakuDrawingCache(true);
        setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                mShowDanma = true;
                start();
//                generateDanmaku();
            }

            @Override
            public void updateTimer(DanmakuTimer timer) {

            }

            @Override
            public void danmakuShown(BaseDanmaku danmaku) {

            }

            @Override
            public void drawingFinished() {

            }
        });
        mDanmakuContext = DanmakuContext.create();
        mDanmakuContext.setMaximumLines(null);
        mDanmakuContext.setCacheStuffer(viewCacheStuffer, null);
        prepare(mParser, mDanmakuContext);
    }

    ViewCacheStuffer viewCacheStuffer = new ViewCacheStuffer<MyViewHolder>() {
        @Override
        public MyViewHolder onCreateViewHolder(int viewType) {
            return new MyViewHolder(View.inflate(getContext(), R.layout.layout_view_cache, null));
        }

        @Override
        public void onBindViewHolder(int viewType, MyViewHolder viewHolder, BaseDanmaku danmaku, AndroidDisplayer.DisplayerConfig displayerConfig, TextPaint paint) {
            SpannableStringBuilder msg = FaceManager.handlerEmojiText(danmaku.text.toString());
            viewHolder.mText.setText(msg);
            viewHolder.mText.setTextColor(danmaku.textColor);
            viewHolder.mText.setTextSize(TypedValue.COMPLEX_UNIT_PX, danmaku.textSize);
            viewHolder.mText.setAlpha(mAlpha);
            if (danmaku.tag != null) {
                viewHolder.mBgDanmu.setVisibility(View.VISIBLE);
                int level = (int) danmaku.tag;
                if (level == 1) {
                    viewHolder.mBgDanmu.setBackgroundResource(R.mipmap.img_background_danmu_one);
                }else if (level == 2) {
                    viewHolder.mBgDanmu.setBackgroundResource(R.mipmap.img_background_danmu_two);
                }else if (level == 3) {
                    viewHolder.mBgDanmu.setBackgroundResource(R.mipmap.img_background_danmu_three);
                }else if (level == 4) {
                    viewHolder.mBgDanmu.setBackgroundResource(R.mipmap.img_background_danmu_four);
                }else if (level == 5) {
                    viewHolder.mBgDanmu.setBackgroundResource(R.mipmap.img_background_danmu_five);
                }
            }else {
                viewHolder.mBgDanmu.setVisibility(View.GONE);
            }
        }
    };

    public void switchDanmuPosition(int type) {
        switch (type) {
            case TYPE_HALF:
                mDanmakuContext.alignBottom(false);
                mDanmakuContext.setR2LDanmakuVisibility(true);
                mDanmakuContext.setMaximumLines(maxLinesPair);
                break;
            case TYPE_OFF:
                mDanmakuContext.alignBottom(false);
                mDanmakuContext.setR2LDanmakuVisibility(false);
                break;
            case TYPE_ON:
                mDanmakuContext.alignBottom(false);
                mDanmakuContext.setR2LDanmakuVisibility(true);
                mDanmakuContext.setMaximumLines(null);
                break;
            case TYPE_BOTTOM:
                mDanmakuContext.alignBottom(true);
                mDanmakuContext.setMaximumLines(maxLinesPair);
                break;
        }
    }

    public void setAlpha(float alpha) {
        mAlpha = alpha;
    }

    public void setTextSize(float size) {
        mTextSize = size;
    }

    @Override
    public void release() {
        super.release();
        mShowDanma = false;
        if (mDanmuHandler != null) {
            mDanmuHandler.removeCallbacksAndMessages(null);
            mDanmuHandler = null;
        }
        if (mHandlerThread != null) {
            mHandlerThread.quit();
            mHandlerThread = null;
        }
    }

    private BaseDanmakuParser mParser = new BaseDanmakuParser() {
        @Override
        protected IDanmakus parse() {
            return new Danmakus();
        }
    };

    /**
     * 随机生成一些弹幕内容以供测试
     */
    private void generateDanmaku() {
        mHandlerThread = new HandlerThread("Danmu");
        mHandlerThread.start();
        mDanmuHandler = new DanmuHandler(mHandlerThread.getLooper());
    }

    /**
     * 向弹幕View中添加一条弹幕
     *
     * @param content    弹幕的具体内容
     * @param color    弹幕的颜色
     * @param withBorder 弹幕是否有边框
     */
    public void addDanmaku(String content, String color, boolean withBorder) {
        BaseDanmaku danmaku = mDanmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        if (danmaku != null) {
            danmaku.text = content;
            danmaku.padding = 5;
            danmaku.textSize = sp2px(mContext, mTextSize);
            if (!TextUtils.isEmpty(color)) {
                danmaku.textColor = Color.parseColor(color);
            }else {
                danmaku.textColor = Color.WHITE;
            }
            danmaku.setTime(getCurrentTime());
/*            if (withBorder) {
                danmaku.borderColor = Color.WHITE;
            }*/
            addDanmaku(danmaku);
        }
    }

    /**
     * 向弹幕View中添加一条弹幕
     *
     * @param content    弹幕的具体内容
     * @param level    炫彩弹幕的
     * @param withBorder 弹幕是否有边框
     */
    public void addDanmaku(String content, int level, boolean withBorder) {
        BaseDanmaku danmaku = mDanmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        if (danmaku != null) {
            danmaku.text = content;
            danmaku.padding = 5;
            danmaku.textSize = sp2px(mContext, mTextSize);
            danmaku.textColor = Color.WHITE;
            danmaku.setTime(getCurrentTime());
            danmaku.tag = level;
/*            if (withBorder) {
                danmaku.borderColor = Color.WHITE;
            }*/
            addDanmaku(danmaku);
        }
    }

    public static int dp2px(Context context, int dpVal) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (scale * dpVal + 0.5f);
    }

    /**
     * sp单位转px
     *
     * @param context
     * @param spValue
     * @return
     */
    public int sp2px(Context context, float spValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (spValue * scale + 0.5f);
    }

    public void toggle(boolean on) {
        TXCLog.i(TAG, "onToggleControllerView on:" + on);
        if (mDanmuHandler != null) {
            if (on) {
                mDanmuHandler.sendEmptyMessageAtTime(DanmuHandler.MSG_SEND_DANMU, 100);
            } else {
                mDanmuHandler.removeMessages(DanmuHandler.MSG_SEND_DANMU);
            }
        }
    }

    public class DanmuHandler extends Handler {
        public static final int MSG_SEND_DANMU = 1001;

        public DanmuHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SEND_DANMU:
                    sendDanmu();
                    int time = new Random().nextInt(1000);
                    mDanmuHandler.sendEmptyMessageDelayed(MSG_SEND_DANMU, time);
                    break;
            }
        }

        private void sendDanmu() {
            int time = new Random().nextInt(300);
            String content = "barrage " + time + time;
            addDanmaku(content, "", false);
        }
    }

    public class MyViewHolder extends ViewCacheStuffer.ViewHolder {

        private final TextView mText;
        private final FrameLayout mBgDanmu;

        public MyViewHolder(View itemView) {
            super(itemView);
            mText = (TextView) itemView.findViewById(R.id.text);
            mBgDanmu = (FrameLayout) itemView.findViewById(R.id.rootView);
        }

    }


}
