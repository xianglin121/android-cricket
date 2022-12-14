package com.tencent.liteav.demo.superplayer;

import android.app.Activity;
import android.app.AppOpsManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.demo.superplayer.model.SuperPlayer;
import com.tencent.liteav.demo.superplayer.model.SuperPlayerImpl;
import com.tencent.liteav.demo.superplayer.model.SuperPlayerObserver;
import com.tencent.liteav.demo.superplayer.model.VipWatchModel;
import com.tencent.liteav.demo.superplayer.model.entity.PlayImageSpriteInfo;
import com.tencent.liteav.demo.superplayer.model.entity.PlayKeyFrameDescInfo;
import com.tencent.liteav.demo.superplayer.model.entity.VideoQuality;
import com.tencent.liteav.demo.superplayer.model.net.LogReport;
import com.tencent.liteav.demo.superplayer.model.utils.NetWatcher;
import com.tencent.liteav.demo.superplayer.ui.player.FloatPlayer;
import com.tencent.liteav.demo.superplayer.ui.player.MatchFullScreenPlayer;
import com.tencent.liteav.demo.superplayer.ui.player.MatchWindowPlayer;
import com.tencent.liteav.demo.superplayer.ui.player.Player;
import com.tencent.liteav.demo.superplayer.ui.view.DanmuView;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

/**
 * ???????????????view
 * <p>
 * ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
 * ??????????????????????????????????????????????????????????????????????????????????????????{@link #playWithModel(SuperPlayerModel)}??????{@link SuperPlayerModel}????????????????????????
 * <p>
 * 1???????????????{@link #playWithModel(SuperPlayerModel)}
 * 2???????????????{@link #setPlayerViewCallback(OnSuperPlayerViewCallback)}
 * 3???controller????????????{@link #mControllerCallback}
 * 4???????????????????????????{@link #resetPlayer()}
 */
public class MatchPlayerView extends RelativeLayout {
    private static final String TAG                    = "SuperPlayerView";
    public final        int    OP_SYSTEM_ALERT_WINDOW = 24;                      // ??????TYPE_TOAST??????????????????API??????

    private Context                    mContext;
    private int mAnchorId;
    private int mType;
    private int mMatchId;
    private ViewGroup                  mRootView;                                 // SuperPlayerView??????view
    private TXCloudVideoView           mTXCloudVideoView;                         // ?????????????????????view
    private MatchFullScreenPlayer mFullScreenPlayer;                         // ??????????????????view
    private MatchWindowPlayer mWindowPlayer;                             // ??????????????????view
    private FloatPlayer mFloatPlayer;                              // ?????????????????????view
    private DanmuView mDanmuView;                                // ??????
    private ViewGroup.LayoutParams     mLayoutParamWindowMode;          // ???????????????SuperPlayerView???????????????
    private ViewGroup.LayoutParams     mLayoutParamFullScreenMode;      // ???????????????SuperPlayerView???????????????
    private LayoutParams               mVodControllerWindowParams;      // ??????controller???????????????
    private LayoutParams               mVodControllerFullScreenParams;  // ??????controller???????????????
    private WindowManager              mWindowManager;                  // ????????????????????????
    private WindowManager.LayoutParams mWindowParams;                   // ?????????????????????
    private OnSuperPlayerViewCallback  mPlayerViewCallback;              // SuperPlayerView??????
    private NetWatcher mWatcher;                         // ?????????????????????
    private SuperPlayer mSuperPlayer;
    private RelativeLayout mErrorPlayer;

    public MatchPlayerView(Context context) {
        super(context);
        initialize(context);
    }

    public MatchPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public MatchPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        mContext = context;
        initView();
        initPlayer();
    }

    public void setInitId(int anchorId, int type, int matchId) {
        mAnchorId = anchorId;
        mType = type;
        mMatchId = matchId;
    }

    /**
     * ?????????view
     */
    private void initView() {
        mRootView = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.superplayer_match_view, null);
        mTXCloudVideoView = (TXCloudVideoView) mRootView.findViewById(R.id.superplayer_cloud_video_view);
        mFullScreenPlayer = (MatchFullScreenPlayer) mRootView.findViewById(R.id.superplayer_controller_large);
        mWindowPlayer = (MatchWindowPlayer) mRootView.findViewById(R.id.superplayer_controller_small);
        mFloatPlayer = (FloatPlayer) mRootView.findViewById(R.id.superplayer_controller_float);
//        mDanmuView = (DanmuView) mRootView.findViewById(R.id.superplayer_danmuku_view);
        mErrorPlayer = mRootView.findViewById(R.id.superplayer_rl_error);

        mVodControllerWindowParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mVodControllerFullScreenParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        mFullScreenPlayer.setCallback(mControllerCallback);
        mWindowPlayer.setCallback(mControllerCallback);
        mFloatPlayer.setCallback(mControllerCallback);

        removeAllViews();
//        mRootView.removeView(mDanmuView);
        mRootView.removeView(mTXCloudVideoView);
        mRootView.removeView(mWindowPlayer);
        mRootView.removeView(mFullScreenPlayer);
        mRootView.removeView(mFloatPlayer);

        addView(mTXCloudVideoView);
//        addView(mDanmuView);
    }

//    public void setPeopleCount(String count) {
//        if (!TextUtils.isEmpty(count)) {
//            mWindowPlayer.setPeopleCount(count);
//        }
//    }
//
//    public void setPeopleCountVisibility(int visibility) {
//        mWindowPlayer.setPeopleCountVisibility(visibility);
//    }

    public void setCountdownVisible(int visibility) {
        mWindowPlayer.setCountdownVisible(visibility);
//        mFullScreenPlayer.setCountdownVisible(visibility);
    }

    public void setCountdownText(String text) {
        mWindowPlayer.setCountdownText(text);
//        mFullScreenPlayer.setCountdownText(text);
    }

    private void initPlayer() {
        mSuperPlayer = new SuperPlayerImpl(mContext, mTXCloudVideoView);
        mSuperPlayer.setObserver(mSuperPlayerObserver);

        if (mSuperPlayer.getPlayerMode() == SuperPlayerDef.PlayerMode.FULLSCREEN) {
            addView(mFullScreenPlayer);
            mFullScreenPlayer.hide();
        } else if (mSuperPlayer.getPlayerMode() == SuperPlayerDef.PlayerMode.WINDOW) {
            addView(mWindowPlayer);
            mWindowPlayer.hide();
        }

        post(new Runnable() {
            @Override
            public void run() {
                if (mSuperPlayer.getPlayerMode() == SuperPlayerDef.PlayerMode.WINDOW) {
                    mLayoutParamWindowMode = getLayoutParams();
                }
                try {
                    // ????????????Parent???LayoutParam??????????????????????????????fullscreen????????????LayoutParam
                    Class parentLayoutParamClazz = getLayoutParams().getClass();
                    Constructor constructor = parentLayoutParamClazz.getDeclaredConstructor(int.class, int.class);
                    mLayoutParamFullScreenMode = (ViewGroup.LayoutParams) constructor.newInstance(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        LogReport.getInstance().setAppName(mContext);
        LogReport.getInstance().setPackageName(mContext);

        if (mWatcher == null) {
            mWatcher = new NetWatcher(mContext);
        }
    }

    /**
     * ??????????????????
     *
     */
    public void updateQuality(int type) {
        mWindowPlayer.updateQuality(type);
        mFullScreenPlayer.updateQuality(type);
    }

    /**
     * ????????????
     *
     * @param model
     */
    public void playWithModel(final SuperPlayerModel model) {
        if (model.videoId != null) {
            mSuperPlayer.play(model.appId, model.videoId.fileId, model.videoId.pSign);
        } else if (model.videoIdV2 != null) {
        } else if (model.multiURLs != null && !model.multiURLs.isEmpty()) {
            mSuperPlayer.play(model.appId, model.multiURLs, model.playDefaultIndex);
        } else {
            mSuperPlayer.play(model.url);
        }
        mFullScreenPlayer.setVipWatchModel(model.vipWatchMode);
        mWindowPlayer.setVipWatchModel(model.vipWatchMode);
        mFloatPlayer.setVipWatchModel(model.vipWatchMode);
    }

    /**
     * ??????VipWatchModel ???????????????null?????????????????????VIP??????
     * @param vipWatchModel
     */
    public void setVipWatchModel(VipWatchModel vipWatchModel){
        mFullScreenPlayer.setVipWatchModel(vipWatchModel);
        mWindowPlayer.setVipWatchModel(vipWatchModel);
        mFloatPlayer.setVipWatchModel(vipWatchModel);
    }

    /**
     * ????????????
     *
     * @param url ????????????
     */
    public void play(String url) {
        mSuperPlayer.play(url);
    }

    /**
     * ????????????
     *
     * @param appId ???????????????appId
     * @param url   ??????????????????
     */
    public void play(int appId, String url) {
        mSuperPlayer.play(appId, url);
    }

    /**
     * ????????????
     *
     * @param appId  ???????????????appId
     * @param fileId ???????????????fileId
     * @param psign  ??????????????????????????????????????????????????????????????????????????????
     */
    public void play(int appId, String fileId, String psign) {
        mSuperPlayer.play(appId, fileId, psign);
    }

    /**
     * ??????????????????
     *
     * @param appId           ???????????????appId
     * @param superPlayerURLS ?????????????????????
     * @param defaultIndex    ????????????Index
     */
    public void play(int appId, List<SuperPlayerModel.SuperPlayerURL> superPlayerURLS, int defaultIndex) {
        mSuperPlayer.play(appId, superPlayerURLS, defaultIndex);
    }

    /**
     * ????????????
     *
     * @param title ????????????
     */
    public void updateTitle(String title) {
        mWindowPlayer.updateTitle(title);
        mFullScreenPlayer.updateTitle(title);
    }

    /**
     * ????????????VIP???????????????????????????????????????
     * @return
     */
    public boolean isShowingVipView(){
        return mFullScreenPlayer.isShowingVipView() || mWindowPlayer.isShowingVipView() || mFloatPlayer.isShowingVipView();
    }


    /**
     * resume??????????????????
     */
    public void onResume() {
        if (mDanmuView != null && mDanmuView.isPrepared() && mDanmuView.isPaused()) {
            mDanmuView.resume();
        }
        mSuperPlayer.resume();
    }

    /**
     * pause??????????????????
     */
    public void onPause() {
        if (mDanmuView != null && mDanmuView.isPrepared()) {
            mDanmuView.pause();
        }
        mSuperPlayer.pauseVod();
    }

    /**
     * ???????????????
     */
    public void resetPlayer() {
        if (mDanmuView != null) {
            mDanmuView.release();
            mDanmuView = null;
        }
        stopPlay();
    }

    /**
     * ????????????
     */
    private void stopPlay() {
        mSuperPlayer.stop();
        if (mWatcher != null) {
            mWatcher.stop();
        }
    }

    /**
     * ??????????????????????????????
     *
     * @param callback
     */
    public void setPlayerViewCallback(OnSuperPlayerViewCallback callback) {
        mPlayerViewCallback = callback;
    }

    /**
     * ????????????????????????
     */
    private void fullScreen(boolean isFull) {
        if (getContext() instanceof Activity) {
            Activity activity = (Activity) getContext();
            if (isFull) {
                //?????????????????????????????????
                View decorView = activity.getWindow().getDecorView();
                if (decorView == null) return;
                if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
                    decorView.setSystemUiVisibility(View.GONE);
                } else if (Build.VERSION.SDK_INT >= 19) {
                    int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
                    decorView.setSystemUiVisibility(uiOptions);
                }
            } else {
                View decorView = activity.getWindow().getDecorView();
                if (decorView == null) return;
                if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
                    decorView.setSystemUiVisibility(View.VISIBLE);
                } else if (Build.VERSION.SDK_INT >= 19) {
                    decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                }
            }
        }
    }

    /**
     * ?????????controller??????
     */
    private Player.Callback mControllerCallback = new Player.Callback() {
        @Override
        public void onSwitchPlayMode(SuperPlayerDef.PlayerMode playerMode) {
            if (playerMode == SuperPlayerDef.PlayerMode.FULLSCREEN) {
                fullScreen(true);
            } else {
                fullScreen(false);
            }
            mFullScreenPlayer.hide();
            mWindowPlayer.hide();
            mFloatPlayer.hide();
            //??????????????????
            if (playerMode == SuperPlayerDef.PlayerMode.FULLSCREEN) {
                if (mLayoutParamFullScreenMode == null) {
                    return;
                }
                removeView(mWindowPlayer);
                addView(mFullScreenPlayer, mVodControllerFullScreenParams);
                setLayoutParams(mLayoutParamFullScreenMode);
                rotateScreenOrientation(SuperPlayerDef.Orientation.LANDSCAPE);
                if (mPlayerViewCallback != null) {
                    mPlayerViewCallback.onStartFullScreenPlay();
                }
            } else if (playerMode == SuperPlayerDef.PlayerMode.WINDOW) {// ??????????????????
                // ??????????????????
                if (mSuperPlayer.getPlayerMode() == SuperPlayerDef.PlayerMode.FLOAT) {
                    try {
                        Context viewContext = getContext();
                        Intent intent = null;
                        if (viewContext instanceof Activity) {
                            intent = new Intent(viewContext, viewContext.getClass());
                            intent.putExtra("anchorId", mAnchorId);
                            intent.putExtra("type", mType);
                            intent.putExtra("matchId", mMatchId);
                        } else {
                            showToast(R.string.superplayer_float_play_fail);
                            return;
                        }
                        mContext.startActivity(intent);
                        mSuperPlayer.pause();
                        if (mLayoutParamWindowMode == null) {
                            return;
                        }
                        mWindowManager.removeView(mFloatPlayer);
                        mSuperPlayer.setPlayerView(mTXCloudVideoView);
                        mSuperPlayer.resume();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (mSuperPlayer.getPlayerMode() == SuperPlayerDef.PlayerMode.FULLSCREEN) { // ?????????????????????
                    if (mLayoutParamWindowMode == null) {
                        return;
                    }
                    removeView(mFullScreenPlayer);
                    addView(mWindowPlayer, mVodControllerWindowParams);
                    setLayoutParams(mLayoutParamWindowMode);
                    rotateScreenOrientation(SuperPlayerDef.Orientation.PORTRAIT);
                    if (mPlayerViewCallback != null) {
                        mPlayerViewCallback.onStopFullScreenPlay();
                    }
                }
            } else if (playerMode == SuperPlayerDef.PlayerMode.FLOAT) {//?????????????????????
                TXCLog.i(TAG, "requestPlayMode Float :" + Build.MANUFACTURER);
                SuperPlayerGlobalConfig prefs = SuperPlayerGlobalConfig.getInstance();
                if (!prefs.enableFloatWindow) {
                    return;
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // 6.0???????????????????????????
                    if (!Settings.canDrawOverlays(mContext)) {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                        intent.setData(Uri.parse("package:" + mContext.getPackageName()));
                        mContext.startActivity(intent);
                        return;
                    }
                } else {
                    if (!checkOp(mContext, OP_SYSTEM_ALERT_WINDOW)) {
                        showToast(R.string.superplayer_enter_setting_fail);
                        return;
                    }
                }
                mSuperPlayer.pause();

                mWindowManager = (WindowManager) mContext.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
                mWindowParams = new WindowManager.LayoutParams();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mWindowParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                } else {
                    mWindowParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                }
                mWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                mWindowParams.format = PixelFormat.TRANSLUCENT;
                mWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
//                mWindowParams.verticalMargin = ScreenUtil.getPxByDp(48f);

                SuperPlayerGlobalConfig.TXRect rect = prefs.floatViewRect;
                mWindowParams.x = rect.x;
                mWindowParams.y = rect.y;
                mWindowParams.width = rect.width;
                mWindowParams.height = rect.height;
                try {
                    mWindowManager.addView(mFloatPlayer, mWindowParams);
                } catch (Exception e) {
                    showToast(R.string.superplayer_float_play_fail);
                    return;
                }

                TXCloudVideoView videoView = mFloatPlayer.getFloatVideoView();
                if (videoView != null) {
                    mSuperPlayer.setPlayerView(videoView);
                    mSuperPlayer.resume();
                }
                // ???????????????
                LogReport.getInstance().uploadLogs(LogReport.ELK_ACTION_FLOATMOE, 0, 0);
            }
            mSuperPlayer.switchPlayMode(playerMode);
        }

        @Override
        public void onBackPressed(SuperPlayerDef.PlayerMode playMode) {
            switch (playMode) {
                case FULLSCREEN:// ???????????????????????????????????????????????????
                    onSwitchPlayMode(SuperPlayerDef.PlayerMode.WINDOW);
                    break;
                case WINDOW:// ?????????????????????????????????????????????
                    if (mPlayerViewCallback != null) {
                        mPlayerViewCallback.onClickSmallReturnBtn();
                    }
                    break;
                case FLOAT:// ???????????????????????????
                    mWindowManager.removeView(mFloatPlayer);
                    if (mPlayerViewCallback != null) {
                        mPlayerViewCallback.onClickFloatCloseBtn();
                    }
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onFloatPositionChange(int x, int y) {
            mWindowParams.x = x;
            mWindowParams.y = y;
            mWindowManager.updateViewLayout(mFloatPlayer, mWindowParams);
        }

        @Override
        public void onPause() {
            mSuperPlayer.pause();
            if (mSuperPlayer.getPlayerType() != SuperPlayerDef.PlayerType.VOD) {
                if (mWatcher != null) {
                    mWatcher.stop();
                }
            }
        }

        @Override
        public void onResume() {
            if (mSuperPlayer.getPlayerState() == SuperPlayerDef.PlayerState.END) { //??????
                mSuperPlayer.reStart();
            } else if (mSuperPlayer.getPlayerState() == SuperPlayerDef.PlayerState.PAUSE) { //????????????
                mSuperPlayer.resume();
            }
        }

        @Override
        public void onSeekTo(int position) {
            mSuperPlayer.seek(position);
        }

        @Override
        public void onResumeLive() {
            mSuperPlayer.resumeLive();
        }

        @Override
        public void onDanmuToggle(boolean isOpen) {
            if (mDanmuView != null) {
                mDanmuView.toggle(isOpen);
            }
        }

        @Override
        public void onDanmuToggle(int type) {
            if (mDanmuView != null) {
                mFullScreenPlayer.setBarrageToggle(type);
                mWindowPlayer.setBarrageToggle(type);
                mDanmuView.switchDanmuPosition(type);
            }
        }

        @Override
        public void onAlphaChange(float alpha) {
            mDanmuView.setAlpha(alpha);
        }

        @Override
        public void onTextSizeChange(float size) {
            mDanmuView.setTextSize(size);
        }

        @Override
        public void onRefreshLive() {
            if (mPlayerViewCallback != null) {
                mPlayerViewCallback.onRefreshClick();
            }
        }

        @Override
        public void onClickFloatingMode() {
            switchPlayMode(SuperPlayerDef.PlayerMode.FLOAT);
        }

        @Override
        public void onToggleDanmuPosition(int position) {
            if (mDanmuView != null) {
                mDanmuView.switchDanmuPosition(position);
            }
        }

        @Override
        public void onSnapshot() {
            mSuperPlayer.snapshot(new TXLivePlayer.ITXSnapshotListener() {
                @Override
                public void onSnapshot(Bitmap bitmap) {
                    if (bitmap != null) {
                        showSnapshotWindow(bitmap);
                    } else {
                        showToast(R.string.superplayer_screenshot_fail);
                    }
                }
            });
        }

        @Override
        public void onQualityChange(int type) {
            if (mPlayerViewCallback != null) {
                mPlayerViewCallback.onQualityChange(type);
            }
        }

        @Override
        public void onQualityChange(VideoQuality quality) {
            mFullScreenPlayer.updateVideoQuality(quality);
            mSuperPlayer.switchStream(quality);
        }

        @Override
        public void onSpeedChange(float speedLevel) {
            mSuperPlayer.setRate(speedLevel);
        }

        @Override
        public void onMirrorToggle(boolean isMirror) {
            mSuperPlayer.setMirror(isMirror);
        }

        @Override
        public void onHWAccelerationToggle(boolean isAccelerate) {
            mSuperPlayer.enableHardwareDecode(isAccelerate);
        }

        @Override
        public void onClickHandleVip() {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://cloud.tencent.com/document/product/454/18872"));
            if(getContext() instanceof Activity){
                getContext().startActivity(intent);
            }
        }

        @Override
        public void onClickVipTitleBack( SuperPlayerDef.PlayerMode playerMode) {
            mFullScreenPlayer.hideVipView();
            mWindowPlayer.hideVipView();
            mFloatPlayer.hideVipView();
        }

        @Override
        public void onClickVipRetry() {
            mControllerCallback.onSeekTo(0);
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    mControllerCallback.onResume();
                    //??????VIP View
                    mFullScreenPlayer.hideVipView();
                    mWindowPlayer.hideVipView();
                    mFloatPlayer.hideVipView();
                }
            }, 500);

        }

        @Override
        public void onCloseVipTip() {
            mFullScreenPlayer.hideTipView();
            mWindowPlayer.hideTipView();
            mFloatPlayer.hideTipView();
        }

        @Override
        public void onClickRedEnvelope() {
            if (mPlayerViewCallback != null) {
                mPlayerViewCallback.onClickRedEnvelope();
            }
        }
    };

    /**
     * ??????????????????
     *
     * @param bmp
     */
    private void showSnapshotWindow(final Bitmap bmp) {
        final PopupWindow popupWindow = new PopupWindow(mContext);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(mContext).inflate(R.layout.superplayer_layout_new_vod_snap, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.superplayer_iv_snap);
        imageView.setImageBitmap(bmp);
        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(mRootView, Gravity.TOP, 1800, 300);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                save2MediaStore(mContext, bmp);
            }
        });
        postDelayed(new Runnable() {
            @Override
            public void run() {
                popupWindow.dismiss();
            }
        }, 3000);
    }

    /**
     * ??????????????????
     *
     * @param orientation
     */
    private void rotateScreenOrientation(SuperPlayerDef.Orientation orientation) {
        switch (orientation) {
            case LANDSCAPE:
                ((Activity) mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            case PORTRAIT:
                ((Activity) mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
        }
    }

    /**
     * ?????????????????????
     * <p>
     * API <18?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     * API >= 19 ????????????????????????????????????
     * API >=23????????????manifest???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     * API >25???TYPE_TOAST ?????????????????????????????????????????????????????????
     */
    public boolean checkOp(Context context, int op) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            try {
                Method method = AppOpsManager.class.getDeclaredMethod("checkOp", int.class, int.class, String.class);
                return AppOpsManager.MODE_ALLOWED == (int) method.invoke(manager, op, Binder.getCallingUid(), context.getPackageName());
            } catch (Exception e) {
                TXCLog.e(TAG, Log.getStackTraceString(e));
            }
        }
        return true;
    }

    /**
     * SuperPlayerView???????????????
     */
    public interface OnSuperPlayerViewCallback {

        /**
         * ??????????????????
         */
        void onStartFullScreenPlay();

        /**
         * ??????????????????
         */
        void onStopFullScreenPlay();

        /**
         * ???????????????????????????x??????
         */
        void onClickFloatCloseBtn();

        /**
         * ????????????????????????????????????
         */
        void onClickSmallReturnBtn();

        /**
         * ?????????????????????
         */
        void onStartFloatWindowPlay();

        /**
         * ????????????
         */
        void onRefreshClick();

        /**
         * ????????????
         */
        void onQualityChange(int type);

        /**
         * ????????????
         */
        void onClickRedEnvelope();
    }

    public void release() {
        if (mWindowPlayer != null) {
            mWindowPlayer.release();
        }
        if (mFullScreenPlayer != null) {
            mFullScreenPlayer.release();
        }
        if (mFloatPlayer != null) {
            mFloatPlayer.release();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        try {
            release();
        } catch (Throwable e) {
            TXCLog.e(TAG, Log.getStackTraceString(e));
        }
    }

    public void switchPlayMode(SuperPlayerDef.PlayerMode playerMode) {
        if (playerMode == SuperPlayerDef.PlayerMode.WINDOW) {
            if (mControllerCallback != null) {
                mControllerCallback.onSwitchPlayMode(SuperPlayerDef.PlayerMode.WINDOW);
            }
        } else if (playerMode == SuperPlayerDef.PlayerMode.FLOAT) {
            if (mPlayerViewCallback != null) {
                mPlayerViewCallback.onStartFloatWindowPlay();
            }
            if (mControllerCallback != null) {
                mControllerCallback.onSwitchPlayMode(SuperPlayerDef.PlayerMode.FLOAT);
            }
        }
    }

    public SuperPlayerDef.PlayerMode getPlayerMode() {
        return mSuperPlayer.getPlayerMode();
    }

    public SuperPlayerDef.PlayerState getPlayerState() {
        return mSuperPlayer.getPlayerState();
    }

    private SuperPlayerObserver mSuperPlayerObserver = new SuperPlayerObserver() {
        @Override
        public void onPlayBegin(String name) {
            mWindowPlayer.updatePlayState(SuperPlayerDef.PlayerState.PLAYING);
            mFullScreenPlayer.updatePlayState(SuperPlayerDef.PlayerState.PLAYING);
//            updateTitle(name);
            mWindowPlayer.hideBackground();
            if (mDanmuView != null && mDanmuView.isPrepared() && mDanmuView.isPaused()) {
                mDanmuView.resume();
            }
            if (mWatcher != null) {
                mWatcher.exitLoading();
            }
            //??????????????????
            if (mDanmuView != null) {
                mDanmuView.toggle(true);
            }
        }

        @Override
        public void onPlayPause() {
            mWindowPlayer.updatePlayState(SuperPlayerDef.PlayerState.PAUSE);
            mFullScreenPlayer.updatePlayState(SuperPlayerDef.PlayerState.PAUSE);
        }

        @Override
        public void onPlayStop() {
            mWindowPlayer.updatePlayState(SuperPlayerDef.PlayerState.END);
            mFullScreenPlayer.updatePlayState(SuperPlayerDef.PlayerState.END);
            // ????????????????????????????????????
            if (mWatcher != null) {
                mWatcher.stop();
            }
        }

        @Override
        public void onPlayLoading() {
            mWindowPlayer.updatePlayState(SuperPlayerDef.PlayerState.LOADING);
            mFullScreenPlayer.updatePlayState(SuperPlayerDef.PlayerState.LOADING);
            if (mWatcher != null) {
                mWatcher.enterLoading();
            }
        }

        @Override
        public void onPlayProgress(long current, long duration) {
            mWindowPlayer.updateVideoProgress(current, duration);
            mFullScreenPlayer.updateVideoProgress(current, duration);
            mFloatPlayer.updateVideoProgress(current, duration);
        }

        @Override
        public void onSeek(int position) {
            if (mSuperPlayer.getPlayerType() != SuperPlayerDef.PlayerType.VOD) {
                if (mWatcher != null) {
                    mWatcher.stop();
                }
            }
        }

        @Override
        public void onSwitchStreamStart(boolean success, SuperPlayerDef.PlayerType playerType, VideoQuality quality) {
            if (playerType == SuperPlayerDef.PlayerType.LIVE) {
                if (success) {
                    Toast.makeText(mContext, "???????????????" + quality.title + "...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "??????" + quality.title + "?????????????????????????????????", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onSwitchStreamEnd(boolean success, SuperPlayerDef.PlayerType playerType, VideoQuality quality) {
            if (playerType == SuperPlayerDef.PlayerType.LIVE) {
                if (success) {
                    Toast.makeText(mContext, "?????????????????????", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "?????????????????????", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onPlayerTypeChange(SuperPlayerDef.PlayerType playType) {
            mWindowPlayer.updatePlayType(playType);
            mFullScreenPlayer.updatePlayType(playType);
            mFloatPlayer.updatePlayType(playType);
        }

        @Override
        public void onPlayTimeShiftLive(TXLivePlayer player, String url) {
            if (mWatcher == null) {
                mWatcher = new NetWatcher(mContext);
            }
            mWatcher.start(url, player);
        }

        @Override
        public void onVideoQualityListChange(List<VideoQuality> videoQualities, VideoQuality defaultVideoQuality) {
            mFullScreenPlayer.setVideoQualityList(videoQualities);
            mFullScreenPlayer.updateVideoQuality(defaultVideoQuality);
        }

        @Override
        public void onVideoImageSpriteAndKeyFrameChanged(PlayImageSpriteInfo info, List<PlayKeyFrameDescInfo> list) {
            mFullScreenPlayer.updateImageSpriteInfo(info);
            mFullScreenPlayer.updateKeyFrameDescInfo(list);
        }

        @Override
        public void onError(int code, String message) {
//            showToast(message);
        }
    };

    private void showToast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    private void showToast(int resId) {
        Toast.makeText(mContext, resId, Toast.LENGTH_SHORT).show();
    }

    public static void save2MediaStore(Context context, Bitmap image) {
        File sdcardDir = context.getExternalFilesDir(null);
        if (sdcardDir == null) {
            Log.e(TAG, "sdcardDir is null");
            return;
        }
        File appDir = new File(sdcardDir, "superplayer");
        if (!appDir.exists()) {
            appDir.mkdir();
        }

        long dateSeconds = System.currentTimeMillis() / 1000;
        String fileName = dateSeconds + ".jpg";
        File file = new File(appDir, fileName);

        String filePath = file.getAbsolutePath();

        File f = new File(filePath);
        if (f.exists()) {
            f.delete();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            // Save the screenshot to the MediaStore
            ContentValues values = new ContentValues();
            ContentResolver resolver = context.getContentResolver();
            values.put(MediaStore.Images.ImageColumns.DATA, filePath);
            values.put(MediaStore.Images.ImageColumns.TITLE, fileName);
            values.put(MediaStore.Images.ImageColumns.DISPLAY_NAME, fileName);
            values.put(MediaStore.Images.ImageColumns.DATE_ADDED, dateSeconds);
            values.put(MediaStore.Images.ImageColumns.DATE_MODIFIED, dateSeconds);
            values.put(MediaStore.Images.ImageColumns.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.ImageColumns.WIDTH, image.getWidth());
            values.put(MediaStore.Images.ImageColumns.HEIGHT, image.getHeight());
            Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            OutputStream out = resolver.openOutputStream(uri);
            image.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

            // update file size in the database
            values.clear();
            values.put(MediaStore.Images.ImageColumns.SIZE, new File(filePath).length());
            resolver.update(uri, values, null, null);

        } catch (Exception e) {
            TXCLog.e(TAG, Log.getStackTraceString(e));
        }
    }

    public void addDanmu(String content, String color, boolean withBorder) {
        if (mDanmuView != null) {
            mDanmuView.addDanmaku(content, color, withBorder);
        }
    }

    public void addDanmu(String content, int level, boolean withBorder) {
        if (mDanmuView != null) {
            mDanmuView.addDanmaku(content, level, withBorder);
        }
    }
}
