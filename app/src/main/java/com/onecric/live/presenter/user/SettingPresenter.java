package com.onecric.live.presenter.user;

import static com.onecric.live.AppManager.mContext;

import android.app.Activity;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.activity.MainActivity;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.util.SpUtil;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.user.SettingView;

public class SettingPresenter extends BasePresenter<SettingView> {
    public SettingPresenter(SettingView view) {
        attachView(view);
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
