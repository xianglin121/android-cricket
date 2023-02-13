package com.onecric.live.log;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

public class ExampleLogger {

    private static final String TAG_SUFFIX = "ENGAGELAB-PRIVATES-APP";

    public static void i(String tag, String message) {
        Log.i(TAG_SUFFIX, " [" + tag + "] " + message);
    }

    public static void d(String tag, String message) {
        Log.d(TAG_SUFFIX, " [" + tag + "] " + message);
    }

    public static void w(String tag, String message) {
        Log.w(TAG_SUFFIX, " [" + tag + "] " + message);
    }

    public static void e(String tag, String message) {
        Log.e(TAG_SUFFIX, " [" + tag + "] " + message);
    }

    public static String toLogString(Bundle bundle) {
        try {
            if (bundle == null) {
                return "null";
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{ ");
            for (String key : bundle.keySet()) {
                stringBuilder.append(key).append(":");
                stringBuilder.append(bundle.get(key)).append(" ");
            }
            stringBuilder.append("}");
            return stringBuilder.toString();
        } catch (Throwable throwable) {
            return bundle.toString();
        }
    }

    public static String toLogString(JSONObject json) {
        if (json == null) {
            return "null";
        }
        try {
            String ret = json.toString(2);
            return System.getProperty("line.separator") + ret;
        } catch (Throwable throwable) {
            return json.toString();
        }
    }

}
