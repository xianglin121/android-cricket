package com.onecric.live.util;

import android.content.Context;
import android.content.Intent;

import com.onecric.live.AppManager;
import com.onecric.live.view.BaseActivity;

//import pro.piwik.sdk.extra.TrackHelper;

/**
 * 开发公司：东莞市梦幻网络科技有限公司
 * 时间：2022/11/12
 */
public class ShareUtil {
    public static void shareText(Context context, String title, String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        context.startActivity(Intent.createChooser(intent, title));
//        TrackHelper.track().socialInteraction("Share", "").target("onecric.live.app").with(((AppManager) ((BaseActivity) context).getApplication()).getTracker());
    }
}
