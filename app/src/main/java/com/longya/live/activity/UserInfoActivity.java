package com.longya.live.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.longya.live.AppManager;
import com.longya.live.CommonAppConfig;
import com.longya.live.CommonCode;
import com.longya.live.R;
import com.longya.live.event.UpdateUserInfoEvent;
import com.longya.live.model.JsonBean;
import com.longya.live.model.UserBean;
import com.longya.live.presenter.user.SettingPresenter;
import com.longya.live.presenter.user.UserInfoPresenter;
import com.longya.live.util.DialogUtil;
import com.longya.live.util.GlideUtil;
import com.longya.live.util.ToastUtil;
import com.longya.live.util.ToolUtil;
import com.longya.live.util.WordUtil;
import com.longya.live.view.BaseActivity;
import com.longya.live.view.MvpActivity;
import com.longya.live.view.user.SettingView;
import com.longya.live.view.user.UserInfoView;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Arrays;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class UserInfoActivity extends MvpActivity<UserInfoPresenter> implements UserInfoView, View.OnClickListener {

    public static void forward(Context context) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        context.startActivity(intent);
    }

    private ImageView iv_avatar;
    private TextView tv_name;
    private TextView tv_introduction;
    private TextView tv_phone;
    private String avatar_url;
    private String mToken;

    @Override
    public boolean getStatusBarTextColor() {
        return true;
    }

    @Override
    protected UserInfoPresenter createPresenter() {
        return new UserInfoPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initView() {
        setTitleText(WordUtil.getString(this, R.string.user_info_title));

        iv_avatar = findViewById(R.id.iv_avatar);
        tv_name = findViewById(R.id.tv_name);
        tv_introduction = findViewById(R.id.tv_introduction);
        tv_phone = findViewById(R.id.tv_phone);

        findViewById(R.id.cl_modify_avatar).setOnClickListener(this);
        findViewById(R.id.cl_nickname).setOnClickListener(this);
        findViewById(R.id.cl_introduction).setOnClickListener(this);
        findViewById(R.id.cl_change_phone).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mvpPresenter.getUserInfo();
        mvpPresenter.getToken();
    }

    @Override
    public void getDataSuccess(UserBean userBean) {
        if (userBean != null) {
            GlideUtil.loadUserImageDefault(this, userBean.getAvatar(), iv_avatar);
            if (!TextUtils.isEmpty(userBean.getUser_nickname())) {
                tv_name.setText(userBean.getUser_nickname());
            }
            if (!TextUtils.isEmpty(userBean.getSignature())) {
                tv_introduction.setText(userBean.getSignature());
            }
            if (!TextUtils.isEmpty(userBean.getMobile())) {
                String mobile = userBean.getMobile();
                if (mobile.contains("-")) {
                    mobile = mobile.substring(mobile.indexOf("-")+1);
                }
                tv_phone.setText(ToolUtil.changPhoneNumber(mobile));
            }
        }
    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void getTokenSuccess(String token) {
        mToken = token;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cl_nickname:
                ModifyNickNameActivity.forwardForResult(this, tv_name.getText().toString());
                break;
            case R.id.cl_introduction:
                ModifyIntroductionActivity.forwardForResult(this, tv_introduction.getText().toString());
                break;
            case R.id.cl_change_phone:
                ChangePhoneActivity.forwardForResult(this, tv_phone.getText().toString());
                break;
            case R.id.cl_modify_avatar:
                if (TextUtils.isEmpty(mToken)){
                    return;
                }
                DialogUtil.showSelectAvatarDialog(this, new DialogUtil.SelectAvatarCallback() {
                    @Override
                    public void onSelectAvatar(boolean isTakePhoto) {
                        long currentTimeMillis = System.currentTimeMillis();
                        if (isTakePhoto) {
                            avatar_url = ToolUtil.openCamera(mActivity,"avatar_" + currentTimeMillis);
                            GlideUtil.loadUserImageDefault(mActivity, avatar_url, iv_avatar);
                        }else {
                            ToolUtil.openPhotoAlbum(mActivity,"avatar_" + currentTimeMillis);
                        }
                    }
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ToolUtil.APP_TAKEPHOTO_CODE) {
            if (!TextUtils.isEmpty(avatar_url)) {
                showLoadingDialog();
                compressImage(avatar_url);
            }
        }
        if (data == null) {
            return;
        }
        if (requestCode == ToolUtil.APP_PHOTOALBUM_CODE) {
            if (data != null) {
                Uri uri = data.getData();
                avatar_url = ToolUtil.getFileFromUri(uri, mActivity);
                if (!TextUtils.isEmpty(avatar_url)) {
                    GlideUtil.loadUserImageDefault(mActivity, avatar_url, iv_avatar);
                    showLoadingDialog();
                    compressImage(avatar_url);
                }
            }
        }else if (requestCode == CommonCode.REQUEST_CODE_MODIFY_NICKNAME) {
            String nickname = data.getStringExtra("nickname");
            if (!TextUtils.isEmpty(nickname)) {
                mvpPresenter.updateNickname(nickname);
                tv_name.setText(nickname);
            }
        }else if (requestCode == CommonCode.REQUEST_CODE_MODIFY_INTRODUCTION) {
            String introduction = data.getStringExtra("introduction");
            if (!TextUtils.isEmpty(introduction)) {
                mvpPresenter.updateIntroduction(introduction);
                tv_introduction.setText(introduction);
            }
        }else if (requestCode == CommonCode.REQUEST_CODE_CHANGE_PHONE) {
            String phone = data.getStringExtra("phone");
            String code = data.getStringExtra("code");
            if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(code)) {
                mvpPresenter.updatePhone(phone, code);
                if (phone.contains("-")) {
                    phone = phone.substring(phone.indexOf("-")+1);
                }
                tv_phone.setText(ToolUtil.changPhoneNumber(phone));
            }
        }
    }

    private void compressImage(String path) {
        File file = new File(CommonAppConfig.getInstance().getImagePath(this));
        if (!file.exists()) {
            file.mkdir();
        }
        Luban.with(mActivity)
                .load(path)
                .setTargetDir(CommonAppConfig.getInstance().getImagePath(this))
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {
                        if (file != null) {
                            AppManager.mContext.getUpLoadManager().put(file, null, mToken, new UpCompletionHandler() {
                                @Override
                                public void complete(String key, ResponseInfo info, JSONObject response) {
                                    dismissLoadingDialog();
                                    //res包含hash、key等信息，具体字段取决于上传策略的设置
                                    try {
                                        if (info.isOK()) {
                                            if (!TextUtils.isEmpty(response.getString("key"))) {
                                                mvpPresenter.updateAvatar(response.getString("key"));
                                            }
                                        } else {
                                            ToastUtil.show(getString(R.string.upload_failed));
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, null);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoadingDialog();
                    }
                }).launch();
    }
}
