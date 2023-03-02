package com.onecric.live.util;

import static com.tencent.liteav.base.ContextUtils.getApplicationContext;

import android.Manifest;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.onecric.live.R;
import com.tencent.qcloud.tuikit.tuichat.util.PermissionUtils;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
     * 根据地址生成二维码图片
     */
    public static Bitmap createQrCode(String url,final int width,final int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 0+"");//去除白边
        try {
            BitMatrix encode = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, width, height, hints);

            int[] pixels = new int[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (encode.get(j, i)) {
                        pixels[i * width + j] = 0x00000000;
                    } else {
                        pixels[i * width + j] = 0xffffffff;
                    }
                }
            }
            Bitmap bitmap=Bitmap.createBitmap(width,height, Bitmap.Config.RGB_565);
            bitmap.setPixels(pixels,0,width,0,0,width,height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存图片到本地
     */
    public static File saveBitmapFile(Activity activity,Bitmap bit) {
        //先判断权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 10004);
                return null;
            }
        }

        if(bit == null){
            return null;
        }

        if (bit.isRecycled()) {
            return null;
        }

        try {
            String result = MediaStore.Images.Media.insertImage(activity.getContentResolver(), bit, "onecric_share_live_"+System.currentTimeMillis()+".jpeg", "onecric_share_live");
            Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(result));
            activity.sendBroadcast(scannerIntent);
            ToastUtil.show(activity.getString(R.string.save_success));
        }catch (Exception e) {
            Log.e("Exception", "ltt saveBitmapFile0 Exception:" + e.getMessage().toString());
            e.printStackTrace();
            ToastUtil.show(activity.getString(R.string.save_failed));
        }
        return null;
    }

    /**
     * 保存图片到缓存
     */
    public static String saveTitmapToCache(Context context , Bitmap bitmap){

        // 默认保存在应用缓存目录里 Context.getCacheDir()
        File file=new File(context.getCacheDir(),System.currentTimeMillis()+".png");
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getPath();
    }

    /**
     * 获取截图的Bitmap
     */
    public static Bitmap convertViewToBitmap(View view){
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    /**
     * 调用系统分享图片
     */
    public static boolean sharePictureFile(Activity activity, Bitmap bitmap) {
        //先判断权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 10005);
                return false;
            }
        }
        //压缩前要防止 Can’t compress a recycled bitmap
        if (bitmap.isRecycled()) {
            return false;
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//        Bitmap bitmap = Bitmap.createBitmap(((BitmapDrawable) context.getDrawable(R.mipmap.xxx)).getBitmap());

        String imgpath = saveTitmapToCache(getApplicationContext(), bitmap);
        if(!TextUtils.isEmpty(imgpath)){
            Uri uri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileProvider", new File(imgpath));
            intent.setDataAndType(uri, "image/*");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.setType("image/*");
            activity.startActivity(Intent.createChooser(intent, "Share to..."));
        }
        return true;
    }


}
