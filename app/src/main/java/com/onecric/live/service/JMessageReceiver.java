//package com.onecric.live.service;
//
//import android.content.Context;
//import android.text.TextUtils;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//
//import cn.jpush.android.api.NotificationMessage;
//import cn.jpush.android.service.JPushMessageReceiver;
//
///**
// * 东莞市梦幻科技有限公司
// * 开发公司：东莞星轨科技有限公司
// * 时间：2020/5/21
// */
//public class JMessageReceiver extends JPushMessageReceiver {
//
//    @Override
//    public void onNotifyMessageOpened(Context context, NotificationMessage notificationMessage) {
//        JSONObject jsonObject = JSON.parseObject(notificationMessage.notificationExtras);
//        String type = jsonObject.getString("type");
//        switch (type){
//            case "0":
//                break;
//        }
//    }
//
//    @Override
//    public void onNotifyMessageArrived(Context context, NotificationMessage notificationMessage) {
//        if (!TextUtils.isEmpty(notificationMessage.notificationContent)) {
//        }else {
//        }
//    }
//}
