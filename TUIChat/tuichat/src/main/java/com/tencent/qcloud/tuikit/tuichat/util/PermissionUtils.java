package com.tencent.qcloud.tuikit.tuichat.util;

import static android.app.Notification.EXTRA_CHANNEL_ID;
import static android.provider.Settings.EXTRA_APP_PACKAGE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.tencent.qcloud.tuikit.tuichat.R;
import com.tencent.qcloud.tuikit.tuichat.TUIChatService;


public class PermissionUtils {

    private static final String TAG = PermissionUtils.class.getSimpleName();

    public static boolean checkPermission(Context context, String permission) {
        Log.i(TAG, "checkPermission permission:" + permission + "|sdk:" + Build.VERSION.SDK_INT);
        boolean flag = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = ActivityCompat.checkSelfPermission(context, permission);
            if (PackageManager.PERMISSION_GRANTED != result) {
                showPermissionDialog(context);
                flag = false;
            }
        }
        return flag;
    }

    public static void showPermissionDialog(final Context context) {
        AlertDialog permissionDialog = new AlertDialog.Builder(context)
                .setMessage(TUIChatService.getAppContext().getString(R.string.permission_content))
                .setPositiveButton(TUIChatService.getAppContext().getString(R.string.setting), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Uri packageURI = Uri.parse("package:" + context.getPackageName());
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton(TUIChatService.getAppContext().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //关闭页面或者做其他操作
                        dialog.cancel();
                    }
                })
                .create();
        permissionDialog.show();
    }

    public static void showNotifiPermissionDialog(final Context context) {
        AlertDialog permissionDialog = new AlertDialog.Builder(context)
                .setMessage("The application does not open the notification permission to authorize the open notification permission")
                .setPositiveButton(TUIChatService.getAppContext().getString(R.string.setting), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
//                        Uri packageURI = Uri.parse("package:" + context.getPackageName());
//                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
//                        context.startActivity(intent);
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                        //这种方案适用于 API 26, 即8.0(含8.0)以上可以用
                        intent.putExtra(EXTRA_APP_PACKAGE, context.getPackageName());
                        intent.putExtra(EXTRA_CHANNEL_ID, context.getApplicationInfo().uid);
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton(TUIChatService.getAppContext().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //关闭页面或者做其他操作
                        dialog.cancel();
                    }
                })
                .create();
        permissionDialog.show();
    }

}
