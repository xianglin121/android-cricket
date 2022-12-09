package com.onecric.live.wxapi;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.onecric.live.event.WXLoginEvent;
import com.onecric.live.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/3.
 */

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private IWXAPI api;
    public static final String Wechat_Appid = "wxb174e004a019d1cf";
    public static final String Wechat_Secret = "67f68ffe1a758324170f08837d9eb5bb";
    private String openId;

    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        this.api = WXAPIFactory.createWXAPI(this, Wechat_Appid, true);
        this.api.handleIntent(getIntent(), this);
    }

    public void onReq(BaseReq paramBaseReq) {
        Log.e("tag", "2--------" );
    }

    public void onResp(BaseResp baseResp) {
        Log.e("tag", "2--------" + baseResp.errCode);

        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                if (baseResp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {//微信登录
                    SendAuth.Resp resp = (SendAuth.Resp) baseResp;
                    if (!TextUtils.isEmpty(resp.code)) {
//                        getAccessToken(resp.code);
                        WXLoginEvent wxLoginEvent = new WXLoginEvent();
                        wxLoginEvent.setLoginInfo(resp.code);
                        EventBus.getDefault().post(wxLoginEvent);
                    } else {
                        ToastUtil.show("Logon failed");
                    }
                    finish();
                }else {
                    finish();
                }
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                finish();
                break;
            default:
                finish();
                break;
        }
    }

    private void getAccessToken(String code) {
        StringBuffer loginUrl = new StringBuffer();
        loginUrl.append("https://api.weixin.qq.com/sns/oauth2/access_token")
                .append("?appid=")
                .append(Wechat_Appid)
                .append("&secret=")
                .append(Wechat_Secret)
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code");
//        System.out.println("haha" + loginUrl.toString());

        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(loginUrl.toString())
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                System.out.println("haha1" + "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseInfo= response.body().string();
//                System.out.println("haha1" + "onResponse: " +responseInfo);
                String access = null;
                String openId = null;
                try {
                    JSONObject jsonObject = new JSONObject(responseInfo);
                    access = jsonObject.getString("access_token");
                    openId = jsonObject.getString("openid");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getUserInfo(access, openId);
            }
        });

    }

    private void getUserInfo(String access, String openId) {
        String getUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access + "&openid=" + openId;
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(getUserInfoUrl)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                System.out.println("haha" + "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseInfo = response.body().string();
//                System.out.println("haha" + "onResponse: " + responseInfo);
                WXLoginEvent wxLoginEvent = new WXLoginEvent();
                wxLoginEvent.setLoginInfo(responseInfo);
                EventBus.getDefault().post(wxLoginEvent);
                finish();
            }
        });
    }

}
