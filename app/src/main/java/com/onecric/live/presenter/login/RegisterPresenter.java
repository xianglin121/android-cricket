package com.onecric.live.presenter.login;

import static com.onecric.live.AppManager.mContext;
import static com.onecric.live.fragment.dialog.LoginDialog.getFlavor;
import static com.onecric.live.util.SpUtil.REGISTRATION_TOKEN;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.model.ConfigurationBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.util.SpUtil;
import com.onecric.live.view.login.RegisterView;

public class RegisterPresenter extends BasePresenter<RegisterView> {
    public RegisterPresenter(RegisterView view) {
        attachView(view);
    }

    public void getCode(String phone,String liveId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mobile", phone);
        jsonObject.put("type", 1);
        //区分从直播间注册
        jsonObject.put("liveId", liveId);
//        addSubscription(apiStores.getCode(getRequestBody(jsonObject)),
        /*addSubscription(apiStores.oneSendCode(getRequestBody(jsonObject)),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        mvpView.getDataSuccess(null);
                    }

                    @Override
                    public void onFailure(String msg) {
                        mvpView.getDataFail(msg);
                    }

                    @Override
                    public void onError(String msg) {
                        mvpView.getDataFail(msg);
                    }

                    @Override
                    public void onFinish() {

                    }
                });*/
    }

    //没用
    public void register(String phone, String code, String password) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mobile", phone);
        jsonObject.put("code", code);
        jsonObject.put("password", password);
        jsonObject.put("channel", 2);
        addSubscription(apiStores.register(getRequestBody(jsonObject)),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        mvpView.registerSuccess(msg);
                    }

                    @Override
                    public void onFailure(String msg) {
                        mvpView.registerFail(msg);
                    }

                    @Override
                    public void onError(String msg) {
                        mvpView.registerFail(msg);
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    public void oneRegister(String phone, String password) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mobile", phone);
        jsonObject.put("password", password);
        jsonObject.put("code", getFlavor(mContext));
        jsonObject.put("channel", 2);
        addSubscription(apiStores.oneRegister(getRequestBody(jsonObject)),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        mvpView.registerSuccess(msg);
                    }

                    @Override
                    public void onFailure(String msg) {
                        mvpView.registerFail(msg);
                    }

                    @Override
                    public void onError(String msg) {
                        mvpView.registerFail(msg);
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    public void getConfiguration(String currentVersionNumber) {
        addSubscription(apiStores.getDefaultConfiguration(currentVersionNumber,SpUtil.getInstance().getStringValue(REGISTRATION_TOKEN)),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        CommonAppConfig.getInstance().saveConfig(JSONObject.parseObject(data, ConfigurationBean.class));
                        mvpView.showCountryList();
                    }

                    @Override
                    public void onFailure(String msg) {
                    }

                    @Override
                    public void onError(String msg) {
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    public void oneLoginGmail(String id, String name, String photo,String gToken,String email,String liveId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("name", name);
        jsonObject.put("photo", photo);
        jsonObject.put("device_type", "android");
        jsonObject.put("code", getFlavor(mContext));
        jsonObject.put("email", email);
        //区分从直播间注册
        jsonObject.put("liveId", liveId);
        /*addSubscription(apiStores.oneLoginGmail(getRequestBody(jsonObject)),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        if (!TextUtils.isEmpty(JSON.parseObject(data).getString("id")) &&
                                !TextUtils.isEmpty(JSON.parseObject(data).getString("token"))){
                            CommonAppConfig.getInstance().saveLoginInfo(JSON.parseObject(data).getString("id"),
                                    JSON.parseObject(data).getString("token"), JSONObject.parseObject(data, UserBean.class).getUserSig(), data);
                        }
                        SpUtil.getInstance().setStringValue(GMAIL_INFO,gToken);
                        SpUtil.getInstance().setStringValue(GMAIL_ACCOUNT,email);
                        mvpView.loginIsSuccess(true);
                    }

                    @Override
                    public void onFailure(String msg) {
                        ToastUtil.show(msg);
                        mvpView.loginIsSuccess(false);
                    }

                    @Override
                    public void onError(String msg) {
                        ToastUtil.show(msg);
                        mvpView.loginIsSuccess(false);
                    }

                    @Override
                    public void onFinish() {

                    }
                });*/
    }

    public void updateJgId(String id) {
        addSubscription(apiStores.updateJgId(CommonAppConfig.getInstance().getToken(), id),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                    }

                    @Override
                    public void onFailure(String msg) {
                    }

                    @Override
                    public void onError(String msg) {
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }
}
