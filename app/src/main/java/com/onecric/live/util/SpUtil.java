package com.onecric.live.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.onecric.live.AppManager;

import java.util.Map;

/**
 * 开发公司：东莞市梦幻科技有限公司 on 2018/9/17.
 * SharedPreferences 封装
 */

public class SpUtil {

    private static SpUtil sInstance;
    private SharedPreferences mSharedPreferences;

    public static final String UID = "uid";
    public static final String TOKEN = "token";
    public static final String USER_SIGN = "userSign";
    public static final String VISITOR_USER_ID = "visitorUserId";
    public static final String VISITOR_USER_SIGN = "visitorUserSign";
    public static final String USER_INFO = "userInfo";
    public static final String BLOCK_INFO = "blockInfo";
    public static final String DEFAULT_CONFIG = "defaultConfig";
    public static final String CONFIG = "config";
    public static final String HIDE_USAGE = "hideUsage";
    public static final String APP_PRIVACY_POLICY = "privacyPolicy";
    public static final String BASKETBALL_LANGUAGE = "basketballLanguage";//篮球语言
    public static final String FOOTBALL_LANGUAGE = "footballLanguage";//足球语言
    public static final String LANGUAGE_ZH = "0";//简体中文
    public static final String LANGUAGE_ZHT = "1";//繁体中文
    public static final String LANGUAGE_EN = "2";//英文
    public static final String MATCH_SEARCH_WORD = "matchSearchWord";//比赛搜索关键词
    public static final String LIVE_SEARCH_WORD = "liveSearchWord";//直播搜索关键词
    public static final String RED_AND_YELLOW_CARD = "redAndYellowCard";//红黄牌显示
    public static final String EVENT_NOTIFICATION_RANGE = "eventNotificationRange";//事件提示范围
    public static final String GOAL_SOUND = "goalSound";//进球的声音
    public static final String GOAL_VIBRATOR = "goalVibrator";//进球的震动
    public static final String RED_CARD_SOUND = "redCardSound";//红牌的声音
    public static final String RED_CARD_VIBRATOR = "redCardVibrator";//红牌的震动
    public static final String FLOATING_PLAY = "floatingPlay";//悬浮窗播放
    public static final String BOX_TIME = "boxTime";//宝箱剩余开启时间
    public static final String FIRST_ENTER = "first_enter";//是否第一次进入app
    public static final String VIDEO_OVERTIME = "video_overtime";//未登录状态下播放是否超时
    public static final String REGISTRATION_TOKEN = "registration_token";//fcm推送token
    public static final String LOGIN_REMIND = "login_remind";//登录倒计时时间

    private SpUtil() {
        mSharedPreferences = AppManager.mContext.getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
    }

    public static SpUtil getInstance() {
        if (sInstance == null) {
            synchronized (SpUtil.class) {
                if (sInstance == null) {
                    sInstance = new SpUtil();
                }
            }
        }
        return sInstance;
    }

    /**
     * 保存一个字符串
     */
    public void setStringValue(String key, String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * 获取一个字符串
     */
    public String getStringValue(String key) {
        return mSharedPreferences.getString(key, "");
    }

    /**
     * 保存多个字符串
     */
    public void setMultiStringValue(Map<String, String> pairs) {
        if (pairs == null || pairs.size() == 0) {
            return;
        }
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        for (Map.Entry<String, String> entry : pairs.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                editor.putString(key, value);
            }
        }
        editor.apply();
    }

    /**
     * 获取多个字符串
     */
    public String[] getMultiStringValue(String... keys) {
        if (keys == null || keys.length == 0) {
            return null;
        }
        int length = keys.length;
        String[] result = new String[length];
        for (int i = 0; i < length; i++) {
            String temp = "";
            if (!TextUtils.isEmpty(keys[i])) {
                temp = mSharedPreferences.getString(keys[i], "");
            }
            result[i] = temp;
        }
        return result;
    }

    /**
     * 保存一个int值
     */
    public void setIntValue(String key, int value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * 获取一个int值
     */
    public int getIntValue(String key) {
        return mSharedPreferences.getInt(key, 0);
    }

    /**
     * 保存一个int值
     */
    public void setLongValue(String key, long value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * 获取一个int值
     */
    public long getLongValue(String key) {
        return mSharedPreferences.getLong(key, 0L);
    }

    /**
     * 保存一个布尔值
     */
    public void setBooleanValue(String key, boolean value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * 获取一个布尔值
     */
    public boolean getBooleanValue(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }


    /**
     * 保存多个布尔值
     */
    public void setMultiBooleanValue(Map<String, Boolean> pairs) {
        if (pairs == null || pairs.size() == 0) {
            return;
        }
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        for (Map.Entry<String, Boolean> entry : pairs.entrySet()) {
            String key = entry.getKey();
            Boolean value = entry.getValue();
            if (!TextUtils.isEmpty(key)) {
                editor.putBoolean(key, value);
            }
        }
        editor.apply();
    }

    /**
     * 获取多个布尔值
     */
    public boolean[] getMultiBooleanValue(String[] keys) {
        if (keys == null || keys.length == 0) {
            return null;
        }
        int length = keys.length;
        boolean[] result = new boolean[length];
        for (int i = 0; i < length; i++) {
            boolean temp = false;
            if (!TextUtils.isEmpty(keys[i])) {
                temp = mSharedPreferences.getBoolean(keys[i], false);
            }
            result[i] = temp;
        }
        return result;
    }


    public void removeValue(String... keys) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        for (String key : keys) {
            editor.remove(key);
        }
        editor.apply();
    }

}
