package com.longya.live.fragment;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.longya.live.CommonAppConfig;
import com.longya.live.R;
import com.longya.live.activity.ActivityCenterActivity;
import com.longya.live.activity.ChargeActivity;
import com.longya.live.activity.CommonProblemActivity;
import com.longya.live.activity.DefaultAvatarActivity;
import com.longya.live.activity.LoginActivity;
import com.longya.live.activity.LoginNewActivity;
import com.longya.live.activity.MyFollowActivity;
import com.longya.live.activity.MyLevelActivity;
import com.longya.live.activity.MyMessageActivity;
import com.longya.live.activity.MyReserveActivity;
import com.longya.live.activity.MySpaceActivity;
import com.longya.live.activity.PayPwdSettingActivity;
import com.longya.live.activity.RegisterActivity;
import com.longya.live.activity.SettingActivity;
import com.longya.live.activity.SpeakerHistoryActivity;
import com.longya.live.activity.UserInfoActivity;
import com.longya.live.activity.WebViewActivity;
import com.longya.live.activity.WithdrawActivity;
import com.longya.live.event.UpdateUserInfoEvent;
import com.longya.live.model.JsonBean;
import com.longya.live.model.UserBean;
import com.longya.live.presenter.user.UserPresenter;
import com.longya.live.util.CommonUtil;
import com.longya.live.util.GlideUtil;
import com.longya.live.util.ToastUtil;
import com.longya.live.view.MvpFragment;
import com.longya.live.view.user.UserView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class UserFragment extends MvpFragment<UserPresenter> implements UserView, View.OnClickListener {

    private ImageView iv_avatar;
    private LinearLayout ll_login_and_register;
    private TextView tv_name;
    private TextView tv_desc;
    private TextView tv_my_diamond_count;
    private TextView tv_withdraw_diamond_count;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected UserPresenter createPresenter() {
        return new UserPresenter(this);
    }

    @Override
    protected void initUI() {
        iv_avatar = rootView.findViewById(R.id.iv_avatar);
        ll_login_and_register = rootView.findViewById(R.id.ll_login_and_register);
        tv_name = rootView.findViewById(R.id.tv_name);
        tv_desc = rootView.findViewById(R.id.tv_desc);
        tv_my_diamond_count = rootView.findViewById(R.id.tv_my_diamond_count);
        tv_withdraw_diamond_count = rootView.findViewById(R.id.tv_withdraw_diamond_count);

        iv_avatar.setOnClickListener(this);
        tv_name.setOnClickListener(this);
        ll_login_and_register.setOnClickListener(this);
        rootView.findViewById(R.id.iv_setting).setOnClickListener(this);
        rootView.findViewById(R.id.iv_msg_center).setOnClickListener(this);
//        rootView.findViewById(R.id.tv_login).setOnClickListener(this);
//        rootView.findViewById(R.id.tv_register).setOnClickListener(this);
        rootView.findViewById(R.id.tv_charge).setOnClickListener(this);
        rootView.findViewById(R.id.tv_withdraw).setOnClickListener(this);
        rootView.findViewById(R.id.ll_follow).setOnClickListener(this);
        rootView.findViewById(R.id.ll_reserve).setOnClickListener(this);
        rootView.findViewById(R.id.ll_space).setOnClickListener(this);
        rootView.findViewById(R.id.ll_msg).setOnClickListener(this);
        rootView.findViewById(R.id.cl_level).setOnClickListener(this);
        rootView.findViewById(R.id.cl_activity).setOnClickListener(this);
        rootView.findViewById(R.id.cl_speaker).setOnClickListener(this);
        rootView.findViewById(R.id.cl_customer).setOnClickListener(this);
        rootView.findViewById(R.id.cl_problem).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
            ll_login_and_register.setVisibility(View.GONE);
            tv_name.setVisibility(View.VISIBLE);
            tv_desc.setVisibility(View.GONE);
        }else {
            ll_login_and_register.setVisibility(View.VISIBLE);
            tv_name.setVisibility(View.GONE);
            tv_desc.setVisibility(View.VISIBLE);
        }
        UserBean userBean = CommonAppConfig.getInstance().getUserBean();
        if (userBean != null) {
            GlideUtil.loadUserImageDefault(getContext(), userBean.getAvatar(), iv_avatar);
            if (!TextUtils.isEmpty(userBean.getUser_nickname())) {
                tv_name.setText(userBean.getUser_nickname());
            }else {
                tv_name.setText("");
            }
            if (!TextUtils.isEmpty(userBean.getBalance())) {
                tv_my_diamond_count.setText(userBean.getBalance());
            }else {
                tv_my_diamond_count.setText("");
            }
            if (!TextUtils.isEmpty(userBean.getWithdrawal_balance())) {
                tv_withdraw_diamond_count.setText(userBean.getWithdrawal_balance());
            }else {
                tv_withdraw_diamond_count.setText("");
            }
        }
    }

    public void updateUserInfo() {
        mvpPresenter.getUserInfo();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataSuccess(UserBean userBean) {
        if (userBean != null) {
            CommonAppConfig.getInstance().saveUserInfo(JSONObject.toJSONString(userBean));
            GlideUtil.loadUserImageDefault(getContext(), userBean.getAvatar(), iv_avatar);
            if (!TextUtils.isEmpty(userBean.getUser_nickname())) {
                tv_name.setText(userBean.getUser_nickname());
            }else {
                tv_name.setText("");
            }
            if (!TextUtils.isEmpty(userBean.getBalance())) {
                tv_my_diamond_count.setText(userBean.getBalance());
            }else {
                tv_my_diamond_count.setText("");
            }
        }
    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_avatar:
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                    LoginActivity.forward(getActivity());
                }else {
                    DefaultAvatarActivity.forward(getActivity());
                }
                break;
            case R.id.tv_name:
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                    ToastUtil.show(getContext().getString(R.string.please_login));
                    return;
                }
                UserInfoActivity.forward(getActivity());
                break;
            case R.id.iv_setting:
                SettingActivity.forward(getActivity());
                break;
            case R.id.ll_login_and_register:
                LoginActivity.forward(getActivity());
                break;
            case R.id.tv_charge:
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                    ToastUtil.show(getContext().getString(R.string.please_login));
                    return;
                }
                ChargeActivity.forward(getContext());
                break;
            case R.id.tv_withdraw:
                if (CommonAppConfig.getInstance().getUserBean() != null) {
                    if (CommonAppConfig.getInstance().getUserBean().getIs_defray_pass() == 1) {
                        WithdrawActivity.forward(getContext());
                    }else {
                        PayPwdSettingActivity.forward(getContext(), PayPwdSettingActivity.ENTER_BY_WITHDRAW);
                    }
                }
                break;
            case R.id.ll_follow:
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                    ToastUtil.show(getContext().getString(R.string.please_login));
                    return;
                }
                MyFollowActivity.forward(getContext());
                break;
            case R.id.ll_reserve:
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                    ToastUtil.show(getContext().getString(R.string.please_login));
                    return;
                }
                MyReserveActivity.forward(getContext());
                break;
            case R.id.ll_space:
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                    ToastUtil.show(getContext().getString(R.string.please_login));
                    return;
                }
                MySpaceActivity.forward(getContext(), Integer.valueOf(CommonAppConfig.getInstance().getUid()));
                break;
            case R.id.iv_msg_center:
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                    ToastUtil.show(getContext().getString(R.string.please_login));
                    return;
                }
                MyMessageActivity.forward(getContext());
                break;
            case R.id.ll_msg:
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                    ToastUtil.show(getContext().getString(R.string.please_login));
                    return;
                }
                MyMessageActivity.forward(getContext());
                break;
            case R.id.cl_level:
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                    ToastUtil.show(getContext().getString(R.string.please_login));
                    return;
                }
                MyLevelActivity.forward(getContext());
                break;
            case R.id.cl_activity:
                ActivityCenterActivity.forward(getContext());
                break;
            case R.id.cl_speaker:
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                    ToastUtil.show(getContext().getString(R.string.please_login));
                    return;
                }
                SpeakerHistoryActivity.forward(getContext());
                break;
            case R.id.cl_problem:
                CommonProblemActivity.forward(getContext());
                break;
            case R.id.cl_customer:
                CommonUtil.forwardCustomer(getContext());
                break;
        }
    }
}
