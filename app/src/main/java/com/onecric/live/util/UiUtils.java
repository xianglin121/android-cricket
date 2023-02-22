package com.onecric.live.util;

import android.Manifest;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.onecric.live.R;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class UiUtils {

    public static String getJsonData(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    context.getResources().getAssets().open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 隐藏软键盘
     *
     * @param view
     */
    public static void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 展开动画
     * @param v
     * @param duration
     * @param targetHeight
     * recylerView没用
     */
    public static void expandView(final View v, int duration, int targetHeight) {
        int prevHeight  = v.getHeight();
        v.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    /**
     * 收缩动画
     * @param v
     * @param duration
     * @param targetHeight
     * recylerView没用
     */
    public static void collapseView(final View v, int duration, int targetHeight) {
        int prevHeight  = v.getHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    /**
     * 得到截屏 用于拼接二维码信息
     */
    public static View getScreenShotBitmap(Activity activity) {
        //截屏-将view作为原图绘制出来
        View v = activity.getWindow().getDecorView();
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.RGB_565);
        Canvas c = new Canvas(bitmap);
        c.translate(-v.getScrollX(), -v.getScrollY());
        v.draw(c);
        return v;
    }

    /**
     * 保存截图到本地
     */
    public static void saveBitmapFile(Activity activity,Bitmap bit) {
        //先判断权限
/*        if (!EasyPermissions.hasPermissions(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(activity, activity.getString(R.string.start_permission_storage_tip), Toast.LENGTH_LONG).show();
            return;
        }*/

        String path = Environment.getExternalStorageDirectory().getPath();
        if (Build.VERSION.SDK_INT > 29) {
            path = activity.getExternalFilesDir(null).getAbsolutePath() ;
        }

        File file = new File(path, "onecric_share_live_"+System.currentTimeMillis()+".jpg");
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(file));

            //截屏-将view作为原图绘制出来
            View v = activity.getWindow().getDecorView();
            Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.RGB_565);
            Canvas c = new Canvas(bitmap);
            c.translate(-v.getScrollX(), -v.getScrollY());
            v.draw(c);

            //压缩Bitmap,不支持png图片的压缩
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();

            // 把文件插入到系统图库
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            Uri uri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            // 通知图库更新
            activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
            ToastUtil.show(activity.getString(R.string.save_success));

        } catch (IOException e) {
            e.printStackTrace();
            ToastUtil.show(e.getMessage());
        }
    }
}
