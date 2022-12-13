package com.onecric.live.util;

import android.content.Context;

import com.onecric.live.AppManager;

import java.lang.reflect.Field;

/**
 * 开发公司：东莞市梦幻科技有限公司 on 2017/8/9.
 * dp转px工具类
 */

public class DpUtil {

    private static float scale;

    static {
        scale = AppManager.mContext.getResources().getDisplayMetrics().density;
    }

    public static int dp2px(int dpVal) {
        return (int) (scale * dpVal + 0.5f);
    }

    public static float dp2px(float dpValue) {
        return dpValue * scale + 0.5f;
    }

    /**
     * 获取系统状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int statusHeight = 0;
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (Integer) field.get(o);
                statusHeight = context.getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        return statusHeight;
    }
}
