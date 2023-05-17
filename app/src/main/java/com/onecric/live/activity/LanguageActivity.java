package com.onecric.live.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hjq.language.MultiLanguages;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.event.UpdateLoginTokenEvent;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.retrofit.ApiClient;
import com.onecric.live.retrofit.ApiStores;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.util.SpUtil;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigInteger;
import java.util.Locale;
import java.util.UUID;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LanguageActivity extends BaseActivity  {
    public static void forward(Context context) {
        Intent intent = new Intent(context, LanguageActivity.class);
        context.startActivity(intent);
    }

    private ImageView iv_avatar,iv_1,iv_2;
    private int languageType = 0;
    @Override
    public int getLayoutId() {
        return R.layout.activity_language;
    }

    @Override
    protected void initView() {
        findViewById(R.id.ll_title).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.tv_title)).setText(getString(R.string.language));
        iv_avatar = findViewById(R.id.iv_avatar);
        iv_1 = findViewById(R.id.iv_1);
        iv_2 = findViewById(R.id.iv_2);

        languageType = SpUtil.getInstance().getIntValue(SpUtil.APP_LANGUAGE);
        switch (languageType){
            case 1:
                iv_1.setSelected(true);
                break;
            case 2:
                iv_2.setSelected(true);
                break;
            default:
                String loc = Locale.getDefault().getLanguage();
                if("hi".equals(loc)){
                    languageType = 2;
                    iv_2.setSelected(true);
                }else{
                    languageType = 1;
                    iv_1.setSelected(true);
                }
                break;
        }

        findViewById(R.id.ll_1).setOnClickListener(v -> {
            if(!iv_1.isSelected()){
                iv_2.setSelected(false);
                iv_1.setSelected(true);
                SpUtil.getInstance().setIntValue(SpUtil.APP_LANGUAGE,1);
//                changeLan(1);
                showLoadingDialog();
                setLanguage(Locale.ENGLISH,1);
            }
        });

        findViewById(R.id.ll_2).setOnClickListener(v -> {
            if(!iv_2.isSelected()){
                iv_1.setSelected(false);
                iv_2.setSelected(true);
                SpUtil.getInstance().setIntValue(SpUtil.APP_LANGUAGE,2);
//                changeLan(2);
                showLoadingDialog();
                setLanguage(new Locale("hi","rIN"),2);
            }
        });

        findViewById(R.id.iv_back).setOnClickListener(v -> {
            finish();
        });
        findViewById(R.id.iv_avatar).setOnClickListener(v -> {
            if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                OneLogInActivity.forward(mActivity);
            } else if (!isFastDoubleClick()){
                PersonalHomepageActivity.forward(mActivity, CommonAppConfig.getInstance().getUid());
            }
        });

        EventBus.getDefault().register(this);
    }

    private void changeLan(int type) {
/*        // 获得res资源对象
        Resources resources = getResources();
        // 获得屏幕参数,主要是用来下面的切换
        DisplayMetrics metrics = resources.getDisplayMetrics();
        // 获得配置对象
        Configuration config = resources.getConfiguration();

        switch (type) {
            case 1:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    config.setLocale(Locale.ENGLISH);
                } else {
                    config.locale = Locale.ENGLISH;
                }
                break;
            case 2:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    config.setLocale(new Locale("hi","rIN"));
                } else {
                    config.locale = new Locale("hi","rIN");
                }
                break;
            }
        resources.updateConfiguration(config, metrics);
        MainActivity.loginForward(this);*/


    }

    public void switchLanguage(Locale language) {
        // 设置当前的语种（返回 true 需要重启 App）
        if(MultiLanguages.setAppLanguage(this, language)){
            new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(@NonNull Message msg) {
                    MainActivity.loginForward(LanguageActivity.this);
                    finish();
                    return false;
                }
            }).sendEmptyMessageDelayed(0,500);
        }

    }


    @Override
    protected void initData() {

    }

    //登录成功，更新信息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateLoginTokenEvent(UpdateLoginTokenEvent event) {
        if (event != null) {
            if (CommonAppConfig.getInstance().getUserBean() != null) {
                GlideUtil.loadUserImageDefault(this, CommonAppConfig.getInstance().getUserBean().getAvatar(), iv_avatar);
            } else {
                iv_avatar.setImageResource(R.mipmap.bg_avatar_default);
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    private void setLanguage(Locale locale,int type) {
        String lan = "english";
        if(type == 2){
            lan = "hindi";
        }

        String myUuid = SpUtil.getInstance().getStringValue(SpUtil.MY_UUID);
        if(TextUtils.isEmpty(myUuid)){
            String[] uuidSplit = UUID.randomUUID().toString().split("-");
            myUuid = new BigInteger(uuidSplit[uuidSplit.length-1],16).toString();
            SpUtil.getInstance().setStringValue(SpUtil.MY_UUID,myUuid);
        }

        ApiClient.retrofit().create(ApiStores.class)
                .setLanguage(lan,myUuid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        dismissLoadingDialog();
                        switchLanguage(locale);
                    }

                    @Override
                    public void onFailure(String msg) {
                        dismissLoadingDialog();
                        ToastUtil.show(msg);
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
