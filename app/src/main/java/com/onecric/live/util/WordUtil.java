package com.onecric.live.util;

import android.content.Context;

/**
 * 开发公司：东莞市梦幻科技有限公司 on 2017/10/10.
 * 获取string.xml中的字
 */

public class WordUtil {

    public static String getString(Context context, int res) {
        return context.getResources().getString(res);
    }

    public static String getString(Context context, int res, Object...formatArgs) {
        return context.getResources().getString(res,formatArgs);
    }
}
