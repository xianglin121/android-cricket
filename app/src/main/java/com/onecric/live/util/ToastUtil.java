package com.onecric.live.util;

import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.onecric.live.AppManager;
import com.onecric.live.R;

/**
 * 开发公司：东莞市梦幻科技有限公司 on 2017/8/3.
 */

public class ToastUtil {

    private static Toast sToast;
    private static long sLastTime;
    private static String sLastString;

    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            sToast = Toast.makeText(AppManager.mContext, "", Toast.LENGTH_SHORT);
        }else {
            sToast = makeToast();
        }
    }

    private static Toast makeToast() {
        Toast toast = new Toast(AppManager.mContext);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            View view = LayoutInflater.from(AppManager.mContext).inflate(R.layout.view_toast, null);
            toast.setView(view);
        }
        return toast;
    }

    public static void show(String s) {
        if (TextUtils.isEmpty(s)) {
            return;
        }
        long curTime = System.currentTimeMillis();
        if (curTime - sLastTime > 2000) {
            sLastTime = curTime;
            sLastString = s;
            sToast.setText(s);
            sToast.show();
        } else {
            if (!s.equals(sLastString)) {
                sLastTime = curTime;
                sLastString = s;
                sToast = makeToast();
                sToast.setText(s);
                sToast.show();
            }
        }

    }

}
