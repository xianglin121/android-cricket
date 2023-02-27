package com.onecric.live.retrofit;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.onecric.live.AppManager;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.activity.LoginActivity;
import com.onecric.live.activity.MainActivity;
import com.onecric.live.event.UpdateLoginTokenEvent;
import com.onecric.live.util.LogUtil;
import com.onecric.live.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;


/**
 * Created by WuXiaolong on 2016/9/22.
 * github:https://github.com/WuXiaolong/
 * 微信公众号：吴小龙同学
 * 个人博客：http://wuxiaolong.me/
 */
public abstract class ApiCallback extends DisposableObserver<JsonObject> {

    public abstract void onSuccess(String data, String msg);

    public abstract void onFailure(String msg);

    public abstract void onError(String msg);

    public abstract void onFinish();


    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            //httpException.response().errorBody().string()
            int code = httpException.code();
            String msg = httpException.getMessage();
            LogUtil.d("code=" + code);
            if (code == 504) {
                msg = AppManager.mContext.getString(R.string.network_is_weak);
            }
            if (code == 502 || code == 404 || code == 500) {
                msg = AppManager.mContext.getString(R.string.tip_network_error_one);
            }
            onError(msg);
        } else {
//            onError(e.getMessage());
            onError(AppManager.mContext.getString(R.string.no_internet_connection));
        }
        onFinish();
    }

    @Override
    public void onNext(JsonObject model) {
        JSONObject jsonObject = JSON.parseObject(model.toString());
        if (jsonObject.getIntValue("code") == 0) {
            onSuccess(jsonObject.getString("data"), jsonObject.getString("msg"));
        }else {
            String msg = jsonObject.getString("msg");
            if (jsonObject.getIntValue("code") == 700) {
                ToastUtil.show(AppManager.mContext.getString(R.string.tip_login_error_one));
                CommonAppConfig.getInstance().clearLoginInfo();
                EventBus.getDefault().post(new UpdateLoginTokenEvent());
//                MainActivity.forward(AppManager.mContext);
//                LoginActivity.forward2(AppManager.mContext);
                MainActivity.loginForward(AppManager.mContext);
            }
            if (jsonObject.getIntValue("code") == 405) {
                msg = AppManager.mContext.getString(R.string.tip_login_error_two);
            }
            onFailure(msg);
        }
    }

    @Override
    public void onComplete() {
        onFinish();
    }
}
