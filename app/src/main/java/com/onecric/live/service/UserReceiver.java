package com.onecric.live.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.engagelab.privates.common.component.MTCommonReceiver;
import com.engagelab.privates.core.api.MTCorePrivatesApi;
import com.engagelab.privates.core.api.WakeMessage;
import com.engagelab.privates.push.api.CustomMessage;
import com.engagelab.privates.push.api.MobileNumberMessage;
import com.engagelab.privates.push.api.NotificationMessage;
import com.engagelab.privates.push.api.PlatformTokenMessage;
import com.onecric.live.activity.AboutUsActivity;
import com.onecric.live.activity.CricketDetailActivity;
import com.onecric.live.activity.LiveDetailActivity;
import com.onecric.live.common.ExampleGlobal;
import com.onecric.live.listener.StatusObserver;
import com.onecric.live.log.ExampleLogger;

/**
 * 开发者继承MTCommonReceiver，可以获得sdk的方法回调
 * <p>
 * 所有回调均在主线程
 */
public class UserReceiver extends MTCommonReceiver {

    private static final String TAG = "UserReceiver";

    /**
     * 应用通知开关状态回调
     *
     * @param context 不为空
     * @param enable  通知开关是否开，true为打开，false为关闭
     */
    @Override
    public void onNotificationStatus(Context context, boolean enable) {
        ExampleLogger.i(TAG, "onNotificationStatus:" + enable);
//        Toast.makeText(context.getApplicationContext(), "onNotificationStatus " + enable, Toast.LENGTH_SHORT).show();
        ExampleGlobal.isNotificationEnable = enable;
        if (StatusObserver.getInstance().getListener() != null) {
            StatusObserver.getInstance().getListener().onNotificationStatus(enable);
        }
    }

    /**
     * 长连接状态回调
     *
     * @param context 不为空
     * @param enable  是否连接
     */
    @Override
    public void onConnectStatus(Context context, boolean enable) {
        ExampleLogger.i(TAG, "onConnectState:" + enable);
//        Toast.makeText(context.getApplicationContext(), "onConnectStatus " + enable, Toast.LENGTH_SHORT).show();
        ExampleGlobal.isConnectEnable = enable;
        if (StatusObserver.getInstance().getListener() != null) {
            StatusObserver.getInstance().getListener().onConnectStatus(enable);
        }
        // 长连接成功可获取registrationId
        if (enable) {
            String registrationId = MTCorePrivatesApi.getRegistrationId(context);
            ExampleLogger.i(TAG, "registrationId:" + registrationId);
        }
    }

    /**
     * 通知消息到达回调
     *
     * @param context             不为空
     * @param notificationMessage 通知消息
     */
    @Override
    public void onNotificationArrived(Context context, NotificationMessage notificationMessage) {
        ExampleLogger.i(TAG, "onNotificationArrived:" + notificationMessage.toString());
    }

    /**
     * 通知消息点击回调
     *
     * @param context             不为空
     * @param notificationMessage 通知消息
     */
    @Override
    public void onNotificationClicked(Context context, NotificationMessage notificationMessage) {
        ExampleLogger.i(TAG, "onNotificationClicked:" + notificationMessage.toString());
        // 用于演示消息展示  todo 等接口定好后再来处理这里点击消息的跳转逻辑
//        Intent intent = new Intent();
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        Bundle bundle = notificationMessage.getExtras();
//        if (bundle != null && bundle.getInt("matchStatus") == 1) {//比赛开始   进入视频直播界面
//            intent.setClass(context, LiveDetailActivity.class);
//            intent.putExtra("anchorId", bundle.getInt("anchorId"));
//            intent.putExtra("type", bundle.getInt("type"));
//            intent.putExtra("matchId", bundle.getInt("matchId"));
//            context.startActivity(intent);
//        } else if (bundle != null && bundle.getInt("matchStatus") == 0) {//比赛已经结束 进入比赛详情界面
//            intent.setClass(context, CricketDetailActivity.class);
//            intent.putExtra("matchId", bundle.getInt("matchId"));
//            context.startActivity(intent);
//            context.startActivity(intent);
//        }
//        context.startActivity(intent);
    }

    /**
     * 通知消息删除回调
     *
     * @param context             不为空
     * @param notificationMessage 通知消息
     */
    @Override
    public void onNotificationDeleted(Context context, NotificationMessage notificationMessage) {
        ExampleLogger.i(TAG, "onNotificationDeleted:" + notificationMessage.toString());
    }

    /**
     * 自定义消息回调
     *
     * @param context       不为空
     * @param customMessage 自定义消息
     */
    @Override
    public void onCustomMessage(Context context, CustomMessage customMessage) {
        ExampleLogger.i(TAG, "onCustomMessage:" + customMessage.toString());
        // 用于演示自定义消息展示
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setClass(context, CustomMessageActivity.class);
        intent.putExtra("message", customMessage);
        context.startActivity(intent);
    }

    /**
     * 厂商token消息回调
     *
     * @param context              不为空
     * @param platformTokenMessage 厂商token消息
     */
    @Override
    public void onPlatformToken(Context context, PlatformTokenMessage platformTokenMessage) {
        ExampleLogger.i(TAG, "onPlatformToken:" + platformTokenMessage.toString());
    }

    /**
     * 移动号码消息回调
     *
     * @param context             不为空
     * @param mobileNumberMessage 移动号码消息
     */
    @Override
    public void onMobileNumber(Context context, MobileNumberMessage mobileNumberMessage) {
        ExampleLogger.i(TAG, "onMobileNumber:" + mobileNumberMessage.toString());
    }

    /**
     * 被拉起回调
     *
     * @param context     不为空
     * @param wakeMessage 被拉起消息
     */
    @Override
    public void onWake(Context context, WakeMessage wakeMessage) {
        ExampleLogger.i(TAG, "onWake:" + wakeMessage.toString());
    }

}
