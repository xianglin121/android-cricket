package com.onecric.live.presenter.login;

import static com.onecric.live.AppManager.mContext;

import android.app.Activity;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSONObject;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.activity.MainActivity;
import com.onecric.live.model.ConfigurationBean;
import com.onecric.live.model.UserBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.util.SpUtil;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.login.MainView;

public class MainPresenter extends BasePresenter<MainView> {
    public MainPresenter(MainView view) {
        attachView(view);
    }

    public void getConfiguration(String versionNumber) {
        addSubscription(apiStores.getDefaultConfiguration(versionNumber),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
//                        CommonAppConfig.getInstance().saveConfig(JSONObject.parseObject(data, ConfigurationBean.class));
                        mvpView.getConfigSuccess(JSONObject.parseObject(data, ConfigurationBean.class));
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

    public void getVisitorUserSig() {
        addSubscription(apiStores.getVisitorUserSig(),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        mvpView.getVisitorUserSigSuccess(JSONObject.parseObject(data).getString("id"), JSONObject.parseObject(data).getString("sig"));
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

    public void getUserInfo() {
        addSubscription(apiStores.getUserInfo(CommonAppConfig.getInstance().getToken(), 0),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        if (!TextUtils.isEmpty(data)) {
                            mvpView.getDataSuccess(JSONObject.parseObject(data, UserBean.class));
                        }
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

    public void signOut(Activity context) {
        String gToken = SpUtil.getInstance().getStringValue(SpUtil.GMAIL_INFO);
        if(!TextUtils.isEmpty(gToken)){
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestId()
                    .requestEmail()
                    .requestIdToken(mContext.getString(R.string.server_client_id))
                    .build();
            GoogleSignIn.getClient(context, gso).signOut().addOnCompleteListener(context, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    SpUtil.getInstance().setStringValue(SpUtil.GMAIL_INFO,"");
                    SpUtil.getInstance().setStringValue(SpUtil.GMAIL_ACCOUNT,"");
                }
            });
        }
        addSubscription(apiStores.signOut(CommonAppConfig.getInstance().getToken()),
                new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        CommonAppConfig.getInstance().clearLoginInfo();
                        SpUtil.getInstance().setBooleanValue(SpUtil.VIDEO_OVERTIME, false);
                        MainActivity.loginForward(context);
                    }

                    @Override
                    public void onFailure(String msg) {
                        ToastUtil.show(msg);
                    }

                    @Override
                    public void onError(String msg) {
                        ToastUtil.show(msg);
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }
}
