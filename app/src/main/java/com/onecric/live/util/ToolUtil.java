package com.onecric.live.util;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.onecric.live.AppManager;
import com.onecric.live.CommonAppConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.HashMap;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class ToolUtil {
    public static final int APP_TAKEPHOTO_CODE = 5001;
    public static final int APP_PHOTOALBUM_CODE = 5002;

    /**
     * 打开相机
     */
    public static String openCamera(Activity context, String file_name) {
        if (!checkPermission(context)) {
            return null;
        }
        File outputImage = null;
        try {
            Uri imageUri = null;
            File pack = new File(CommonAppConfig.IMAGE_PATH);
            if (!pack.exists()) {
                pack.mkdirs();
            }
            outputImage = new File(CommonAppConfig.IMAGE_PATH + file_name + ".png");
            if (Build.VERSION.SDK_INT >= 24) {
                imageUri = FileProvider.getUriForFile(context, AppManager.mContext.getPackageName() + ".fileProvider", outputImage);
            } else {
                imageUri = Uri.fromFile(outputImage);
            }
            //启动相机程序
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            context.startActivityForResult(intent, APP_TAKEPHOTO_CODE);

        } catch (Exception e) {
            ToastUtil.show(e.getMessage());
        }
        return outputImage.getPath();
    }

    public static void openPhotoAlbum(Activity context, String file_name) {
        if (!checkPermission(context)) {
            return;
        }

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        //允许裁剪
        intent.putExtra("crop", true);
        //允许缩放
        intent.putExtra("scale", true);
        //图片的输出位置
        intent.putExtra(MediaStore.EXTRA_OUTPUT, "");
        context.startActivityForResult(intent, APP_PHOTOALBUM_CODE);

    }

    public static String getFileFromUri(Uri uri, Context context) {
        if (uri == null) {
            return null;
        }
        switch (uri.getScheme()) {
            case "content":
                return getFileFromContentUri(uri, context);
            case "file":
                return uri.getPath();
            default:
                return null;
        }
    }

    /**
     * 通过内容解析中查询uri中的文件路径
     */
    private static String getFileFromContentUri(Uri contentUri, Context context) {
        if (contentUri == null) {
            return null;
        }
        File file = null;
        String filePath = null;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA};
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(contentUri, filePathColumn, null,
                null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            filePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
            cursor.close();
        }
        return filePath;
    }


    public static boolean checkPermission(Activity context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                context.requestPermissions(new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 1001);

            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 显示键盘
     *
     * @param et 输入焦点
     */
    public static void showInput(final EditText et, Context context) {
        et.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * 隐藏键盘
     */
    public static void hideInput(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        View v = context.getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    /**
     * 删除指定目录下的文件，这里用于缓存的删除
     *
     * @param filePath       filePath
     * @param deleteThisPath deleteThisPath
     */
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {
                    File files[] = file.listFiles();
                    for (File file1 : files) {
                        deleteFolderFile(file1.getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {
                        file.delete();
                    } else {
                        if (file.listFiles().length == 0) {
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取生成图片造成的缓存
     *
     * @return CacheSize
     */
    public static String getCacheSize() {
        try {
            long imgFolderSize = getFolderSize(new File(CommonAppConfig.IMAGE_PATH));
            long videoFolderSize = getFolderSize(new File(CommonAppConfig.VIDEO_PATH));
            return getFormatSize(imgFolderSize + videoFolderSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 格式化单位
     *
     * @param size size
     * @return size
     */
    private static String getFormatSize(double size) {

        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return "0.00MB";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    /**
     * 获取指定文件夹内所有文件大小的和
     *
     * @param file file
     * @return size
     * @throws Exception
     */
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    public static String changPhoneNumber(String phoneNumber) {
        StringBuffer sb = new StringBuffer();
        if (phoneNumber.length() > 6) {
            int indexOne = (phoneNumber.length() - 4) / 2;
            int indexTwo = indexOne + 4;
            String frontThreeString = phoneNumber.substring(0, indexOne);
            sb.append(frontThreeString);
            String substring = phoneNumber.substring(indexOne, indexTwo);
            String replace = substring.replace(substring, "****");
            sb.append(replace);
            String lastFourString = phoneNumber.substring(indexTwo, phoneNumber.length());
            sb.append(lastFourString);
            return sb.toString();
        } else {
            return "";
        }
    }

    public static String changAccountNumber(String accountNumber) {
        StringBuffer sb = new StringBuffer();
        if (accountNumber.length() > 10) {
            String frontThreeString = accountNumber.substring(0, 4);
            sb.append(frontThreeString);
            String substring = accountNumber.substring(4, accountNumber.length() - 3);
            String replace = substring.replace(substring, "****");
            sb.append(replace);
            String lastFourString = accountNumber.substring(accountNumber.length() - 3, accountNumber.length());
            sb.append(lastFourString);
            return sb.toString();
        } else {
            return "";
        }
    }

    public static File saveLocalBitmap(Bitmap bitmap, String file_name) {
        File pack = new File(CommonAppConfig.getInstance().getImagePath(AppManager.mContext));
        if (!pack.exists()) {
            pack.mkdirs();
        }
        File outputImage = new File(CommonAppConfig.getInstance().getImagePath(AppManager.mContext) + "/" + file_name + ".png");
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(outputImage);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)) {
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputImage;
    }

    public static Bitmap getFirstBitmap(Context context, String url, boolean isSD) {
        Bitmap bitmap = null;
        //从输入的媒体文件中取得帧和元数据
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            if (isSD) {
                //根据文件路径获取缩略图
                retriever.setDataSource(context, Uri.fromFile(new File(url)));
            } else {
                //根据网络路径获取缩略图
                retriever.setDataSource(url, new HashMap());
            }
            //得到第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }

    public static String getCurrentVersionCode(Context context) {
        PackageManager mPackageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = mPackageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
        String versionName = packageInfo.versionName;

        String mCurrentVersionCode = versionName.replaceAll("\\.", "");
        return mCurrentVersionCode;
    }
}
