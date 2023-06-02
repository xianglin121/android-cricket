package com.onecric.live.fragment;

import static com.onecric.live.HttpConstant.SHARE_LIVE_URL;
import static com.onecric.live.util.SpUtil.GMAIL_ACCOUNT;

import android.app.Dialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.activity.LanguageActivity;
import com.onecric.live.activity.MyFollowActivity;
import com.onecric.live.activity.NewsActivity;
import com.onecric.live.activity.OneLogInActivity;
import com.onecric.live.activity.SettingActivity;
import com.onecric.live.activity.UserInfoActivity;
import com.onecric.live.event.ToggleTabEvent;
import com.onecric.live.model.ConfigurationBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.UserBean;
import com.onecric.live.presenter.login.MainPresenter;
import com.onecric.live.util.DialogUtil;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.util.ShareUtil;
import com.onecric.live.util.SpUtil;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.util.WordUtil;
import com.onecric.live.view.MvpFragment;
import com.onecric.live.view.login.MainView;

import org.greenrobot.eventbus.EventBus;

public class MoreFragment extends MvpFragment<MainPresenter> implements MainView,View.OnClickListener {
    private ImageView iv_avatar_nav;
    private TextView tv_name_nav,tv_sign_out,tv_account,tv_login;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_more;
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected void initUI() {
        iv_avatar_nav = findViewById(R.id.iv_avatar_nav);
        tv_name_nav = findViewById(R.id.tv_name_nav);
        tv_sign_out = findViewById(R.id.tv_sign_out);
        tv_account = findViewById(R.id.tv_account);
        tv_login = findViewById(R.id.tv_login);
        tv_login.setOnClickListener(this);
        tv_sign_out.setOnClickListener(this);
        iv_avatar_nav.setOnClickListener(this);
        findViewById(R.id.tv_list_1).setOnClickListener(this);
        findViewById(R.id.tv_list_2).setOnClickListener(this);
        findViewById(R.id.tv_list_3).setOnClickListener(this);
        findViewById(R.id.tv_list_4).setOnClickListener(this);
        findViewById(R.id.tv_list_5).setOnClickListener(this);
        findViewById(R.id.tv_list_6).setOnClickListener(this);
        updateUserInfo();
    }

    public void updateUserInfo() {
        tv_sign_out.setVisibility(View.GONE);
        tv_account.setVisibility(View.GONE);
        if (CommonAppConfig.getInstance().getUserBean() != null) {
            //已登录
            tv_login.setVisibility(View.GONE);
            tv_sign_out.setVisibility(View.VISIBLE);
            GlideUtil.loadUserImageDefault(getContext(), CommonAppConfig.getInstance().getUserBean().getAvatar(), iv_avatar_nav);
            if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getUserBean().getUser_nickname())) {
                tv_name_nav.setVisibility(View.VISIBLE);
                tv_name_nav.setText(CommonAppConfig.getInstance().getUserBean().getUser_nickname());
            }
            if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getUserBean().getMobile())) {
                tv_account.setVisibility(View.VISIBLE);
                tv_account.setText(CommonAppConfig.getInstance().getUserBean().getMobile());
            }else if(!TextUtils.isEmpty(SpUtil.getInstance().getStringValue(GMAIL_ACCOUNT))){
                tv_account.setVisibility(View.VISIBLE);
                tv_account.setText(SpUtil.getInstance().getStringValue(GMAIL_ACCOUNT));
            }
        } else {
            //未登录
            iv_avatar_nav.setImageResource(R.mipmap.bg_avatar_default);
            tv_login.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login:
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                    OneLogInActivity.forward(getContext());
                }
                break;
            case R.id.tv_sign_out:
                if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                    //退出登录
                    DialogUtil.showSimpleDialog(getContext(), getString(R.string.tips), WordUtil.getString(getContext(), R.string.confirm_sign_out_tip), true, new DialogUtil.SimpleCallback() {
                        @Override
                        public void onConfirmClick(Dialog dialog, String content) {
                            mvpPresenter.signOut(getActivity());
                        }
                    });
                }
                break;
            case R.id.iv_avatar_nav:
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                    OneLogInActivity.forward(getContext());
                } else {
                    UserInfoActivity.forward(getActivity());
                }
                break;
            case R.id.tv_list_1:
                //切LiveTab
                EventBus.getDefault().post(new ToggleTabEvent(0));
                break;
            case R.id.tv_list_2:
                //News
                NewsActivity.forward(getContext());
                break;
            case R.id.tv_list_3:
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                    ToastUtil.show(getString(R.string.please_login));
                    OneLogInActivity.forward(getContext());
                } else {
                    MyFollowActivity.forward(getContext());
                }
                break;
            case R.id.tv_list_4:
                SettingActivity.forward(getContext());
                break;
            case R.id.tv_list_5:
                ShareUtil.shareText(getContext(), getString(R.string.system_share), SHARE_LIVE_URL);
                break;
            case R.id.tv_list_6:
                LanguageActivity.forward(getContext());
            default:break;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataSuccess(JsonBean model) {

    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void getVisitorUserSigSuccess(String userId, String userSig) {

    }

    @Override
    public void getDataSuccess(UserBean model) {

    }

    @Override
    public void getConfigSuccess(ConfigurationBean bean) {

    }
}
