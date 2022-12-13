package com.onecric.live;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.onecric.live.model.BlockFunctionBean;
import com.onecric.live.model.ConfigurationBean;
import com.onecric.live.model.UserBean;
import com.onecric.live.util.SpUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CommonAppConfig {
    private static CommonAppConfig sInstance;

    //存放图片到sdcard中
//    public static final String IMAGE_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + AppManager.mContext.getPackageName()+ ".image/";
    public static final String IMAGE_PATH = AppManager.mContext.getFilesDir().getPath() + File.separator + AppManager.mContext.getPackageName()+ ".image/";
    //存放视频到sdcard中
//    public static final String VIDEO_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + AppManager.mContext.getPackageName()+ ".video/";
    public static final String VIDEO_PATH = AppManager.mContext.getFilesDir().getPath() + File.separator + AppManager.mContext.getPackageName()+ ".video/";

    private CommonAppConfig() {

    }

    public static CommonAppConfig getInstance() {
        if (sInstance == null) {
            synchronized (CommonAppConfig.class) {
                if (sInstance == null) {
                    sInstance = new CommonAppConfig();
                }
            }
        }
        return sInstance;
    }

    public String getUid() {
        return SpUtil.getInstance().getStringValue(SpUtil.UID);
    }

    public String getToken() {
        return SpUtil.getInstance().getStringValue(SpUtil.TOKEN);
    }

    public String getUserSign() {
        return SpUtil.getInstance().getStringValue(SpUtil.USER_SIGN);
    }

    /**
     * 保存游客ID
     */
    public void setVisitorUserId(String userId) {
        SpUtil.getInstance().setStringValue(SpUtil.VISITOR_USER_ID, userId);
    }

    /**
     * 获取游客ID
     */
    public String getVisitorUserId() {
        return SpUtil.getInstance().getStringValue(SpUtil.VISITOR_USER_ID);
    }

    /**
     * 保存游客签名
     */
    public void setVisitorUserSign(String userSign) {
        SpUtil.getInstance().setStringValue(SpUtil.VISITOR_USER_SIGN, userSign);
    }

    /**
     * 获取游客签名
     */
    public String getVisitorUserSign() {
        return SpUtil.getInstance().getStringValue(SpUtil.VISITOR_USER_SIGN);
    }

    /**
     * 保存登录信息
     */
    public void saveLoginInfo(String uid, String token, String userSign, String userBean) {
        Map<String, String> map = new HashMap<>();
        map.put(SpUtil.UID, uid);
        map.put(SpUtil.TOKEN, token);
        map.put(SpUtil.USER_SIGN, userSign);
        map.put(SpUtil.USER_INFO, userBean);
        SpUtil.getInstance().setMultiStringValue(map);
    }

    /**
     * 清除登录信息
     */
    public void clearLoginInfo() {
        SpUtil.getInstance().removeValue(
                SpUtil.UID, SpUtil.TOKEN, SpUtil.USER_SIGN, SpUtil.USER_INFO, SpUtil.BLOCK_INFO);
    }

    public void saveUserInfo(String userBean) {
        Map<String, String> map = new HashMap<>();
        map.put(SpUtil.USER_INFO, userBean);
        SpUtil.getInstance().setMultiStringValue(map);
    }

    public UserBean getUserBean() {
        UserBean userBean = null;
        String userBeanJson = SpUtil.getInstance().getStringValue(SpUtil.USER_INFO);
        if (!TextUtils.isEmpty(userBeanJson)) {
            userBean = JSON.parseObject(userBeanJson, UserBean.class);
        }
        return userBean;
    }

    public void saveBlockFunctionInfo(BlockFunctionBean blockFunctionBean) {
        if (blockFunctionBean != null) {
            Map<String, String> map = new HashMap<>();
            map.put(SpUtil.BLOCK_INFO, JSONObject.toJSONString(blockFunctionBean));
            SpUtil.getInstance().setMultiStringValue(map);
        }
    }

    public BlockFunctionBean getBlockFunctionInfo() {
        BlockFunctionBean blockFunctionBean = null;
        String json = SpUtil.getInstance().getStringValue(SpUtil.BLOCK_INFO);
        if (!TextUtils.isEmpty(json)) {
            blockFunctionBean = JSON.parseObject(json, BlockFunctionBean.class);
        }else {
            blockFunctionBean = new BlockFunctionBean();
            saveBlockFunctionInfo(blockFunctionBean);
        }
        return blockFunctionBean;
    }

    public void saveConfig(ConfigurationBean configurationBean) {
        if (configurationBean != null) {
            Map<String, String> map = new HashMap<>();
            map.put(SpUtil.DEFAULT_CONFIG, JSONObject.toJSONString(configurationBean));
            SpUtil.getInstance().setMultiStringValue(map);
        }
    }

    public ConfigurationBean getConfig() {
        ConfigurationBean configurationBean = null;
        String json = SpUtil.getInstance().getStringValue(SpUtil.DEFAULT_CONFIG);
        if (!TextUtils.isEmpty(json)) {
            configurationBean = JSON.parseObject(json, ConfigurationBean.class);
        }else {
            configurationBean = new ConfigurationBean();
        }
        return configurationBean;
    }

    public String getDiskCacheDir(Context context) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        }else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }

    public String getImagePath(Context context) {
        return getDiskCacheDir(context) + File.separator + "images";
    }
}