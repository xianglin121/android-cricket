package com.onecric.live.util;

import android.os.Handler;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.onecric.live.AppManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import cn.qqtheme.framework.entity.Province;

/**
 * 开发公司：东莞市梦幻科技有限公司 on 2018/6/28.
 */

public class CityUtil {

    private ArrayList<Province> mProvinceList;
    private static CityUtil sInstance;
    private Handler mHandler;

    private CityUtil() {
        mProvinceList = new ArrayList<>();
        mHandler = new Handler();
    }

    public static CityUtil getInstance() {
        if (sInstance == null) {
            synchronized (CityUtil.class) {
                if (sInstance == null) {
                    sInstance = new CityUtil();
                }
            }
        }
        return sInstance;
    }

    public void getNewCityListFromAssets(final CommonCallback<ArrayList<Province>> callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedReader br = null;
                try {
                    InputStream is = AppManager.mContext.getAssets().open("new_city.json");
                    br = new BufferedReader(new InputStreamReader(is, "utf-8"));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    String result = sb.toString();
                    if (!TextUtils.isEmpty(result)) {
                        if (mProvinceList == null) {
                            mProvinceList = new ArrayList<>();
                        }
                        mProvinceList.addAll(JSON.parseArray(result, Province.class));
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (callback != null) {
                                    callback.callback(mProvinceList);
                                }
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (callback != null) {
                                callback.callback(null);
                            }
                        }
                    });
                } finally {
                    if (br != null) {
                        try {
                            br.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    public ArrayList<Province> getCityList() {
        return mProvinceList;
    }

}
