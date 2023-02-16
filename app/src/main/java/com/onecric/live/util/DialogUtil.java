package com.onecric.live.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.method.DigitsKeyListener;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.onecric.live.BuildConfig;
import com.onecric.live.R;
import com.onecric.live.activity.SettingActivity;
import com.onecric.live.activity.WebViewActivity;
import com.onecric.live.model.LiveUserBean;

import java.util.ArrayList;
import java.util.Calendar;

import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.AddressPicker;

/**
 * 开发公司：东莞市梦幻科技有限公司 on 2017/8/8.
 */

public class DialogUtil {
    public static final int INPUT_TYPE_TEXT = 0;
    public static final int INPUT_TYPE_NUMBER = 1;
    public static final int INPUT_TYPE_NUMBER_PASSWORD = 2;
    public static final int INPUT_TYPE_TEXT_PASSWORD = 3;

    /**
     * 用于网络请求等耗时操作的LoadingDialog
     */
    public static Dialog loadingDialog(Context context, String text) {
        Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        if (!TextUtils.isEmpty(text)) {
            TextView titleView = (TextView) dialog.findViewById(R.id.text);
            if (titleView != null) {
                titleView.setText(text);
            }
        }
        return dialog;
    }


    public static Dialog loadingDialog(Context context) {
        return loadingDialog(context, "");
    }

    public static void showSimpleTipDialog(Context context, String content) {
        showSimpleTipDialog(context, null, content);
    }

    public static void showSimpleTipDialog(Context context, String title, String content) {
        final Dialog dialog = new Dialog(context, R.style.dialog2);
        dialog.setContentView(R.layout.dialog_simple_tip);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        if (!TextUtils.isEmpty(title)) {
            TextView titleView = (TextView) dialog.findViewById(R.id.title);
            titleView.setText(title);
        }
        if (!TextUtils.isEmpty(content)) {
            TextView contentTextView = (TextView) dialog.findViewById(R.id.content);
            contentTextView.setText(content);
        }
        dialog.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void showSimpleDialog(Context context, String content, SimpleCallback callback) {
        showSimpleDialog(context, content, true, callback);
    }

    public static void showSimpleDialog(Context context, String content, boolean cancelable, SimpleCallback callback) {
        showSimpleDialog(context, null, content, cancelable, callback);
    }

    public static void showSimpleDialog(Context context, String title, String content, boolean cancelable, SimpleCallback callback) {
        new Builder(context)
                .setTitle(title)
                .setContent(content)
                .setCancelable(cancelable)
                .setClickCallback(callback)
                .build()
                .show();
    }

    public static void showSimpleDialog(Context context, String content, boolean cancelable, String confirmString, SimpleCallback callback) {
        new Builder(context)
                .setContent(content)
                .setConfrimString(confirmString)
                .setCancelable(cancelable)
                .setClickCallback(callback)
                .build()
                .show();
    }


    public static void showSimpleInputDialog(Context context, String title, String hint, int inputType, int length, SimpleCallback callback) {
        new Builder(context).setTitle(title)
                .setCancelable(true)
                .setInput(true)
                .setHint(hint)
                .setInputType(inputType)
                .setLength(length)
                .setClickCallback(callback)
                .build()
                .show();
    }

    public static void showSimpleInputDialog(String content, Context context, String title, String hint, int inputType, int length, SimpleCallback callback) {
        new Builder(context).setTitle(title)
                .setCancelable(true)
                .setInput(true)
                .setContent(content)
                .setHint(hint)
                .setInputType(inputType)
                .setLength(length)
                .setClickCallback(callback)
                .build()
                .show();
    }

    public static void showSimpleInputDialog(Context context, String title, String hint, String confirmString, int inputType, int length, SimpleCallback callback) {
        new Builder(context).setTitle(title)
                .setCancelable(true)
                .setInput(true)
                .setHint(hint)
                .setConfrimString(confirmString)
                .setInputType(inputType)
                .setLength(length)
                .setClickCallback(callback)
                .build()
                .show();
    }

    public static void showSimpleInputDialog(Context context, String title, String hint, int inputType, int length, String digits, SimpleCallback callback) {
        new Builder(context).setTitle(title)
                .setCancelable(true)
                .setInput(true)
                .setHint(hint)
                .setInputType(inputType)
                .setLength(length)
                .setDigits(digits)
                .setClickCallback(callback)
                .build()
                .show();
    }

    public static void showSimpleInputDialog(Context context, String title, int inputType, int length, SimpleCallback callback) {
        showSimpleInputDialog(context, title, null, inputType, length, callback);
    }

    public static void showSimpleInputDialog(Context context, String title, int inputType, SimpleCallback callback) {
        showSimpleInputDialog(context, title, inputType, 0, callback);
    }

    public static void showSimpleInputDialog(Context context, String title, String digits, SimpleCallback callback) {
        showSimpleInputDialog(context, title, null, INPUT_TYPE_TEXT, 0, digits, callback);
    }

    public static void showSimpleInputDialog(Context context, String title, SimpleCallback callback) {
        showSimpleInputDialog(context, title, INPUT_TYPE_TEXT, callback);
    }

    public static void showStringArrayDialog(Context context, SparseArray<String> array, final StringArrayDialogCallback callback) {
        final Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.dialog_string_array);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
//        window.setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
        LinearLayout container = (LinearLayout) dialog.findViewById(R.id.container);
        View.OnClickListener itemListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) v;
                if (callback != null) {
                    callback.onItemClick(textView.getText().toString(), (int) v.getTag());
                }
                dialog.dismiss();
            }
        };
        for (int i = 0, length = array.size(); i < length; i++) {
            TextView textView = new TextView(context);
            textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DpUtil.dp2px(54)));
            textView.setTextColor(0xff323232);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            textView.setPadding(DpUtil.dp2px(17), 0, 0, 0);
            textView.setText(array.valueAt(i));
            textView.setTag(array.keyAt(i));
            textView.setOnClickListener(itemListener);
            container.addView(textView);
//            if (i != length - 1) {
//                View v = new View(context);
//                v.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DpUtil.dp2px(1)));
//                v.setBackgroundColor(0xfff5f5f5);
//                container.addView(v);
//            }
        }
//        dialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
        dialog.show();
    }

    public static void showBuyNobleDialog(Context context, String noble, SimpleCallback callback) {
        final Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.dialog_buy_noble);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        TextView tv_content = dialog.findViewById(R.id.tv_content);
        String str = context.getString(R.string.confirm_open);
        SpannableStringBuilder spannable = new SpannableStringBuilder(str + noble + "？");
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#3677FF")), str.length(), str.length() + noble.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_content.setText(spannable);
        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onConfirmClick(dialog, noble);
                }
            }
        });
        dialog.show();
    }

    public static void showSelectSexDialog(Context context, int sex, SelectSexCallback callback) {
        final Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.dialog_select_sex);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        ImageView iv_male = (ImageView) dialog.findViewById(R.id.iv_male);
        ImageView iv_female = (ImageView) dialog.findViewById(R.id.iv_female);
        if (sex == 0) {
            iv_male.setSelected(true);
            iv_female.setSelected(false);
        } else {
            iv_male.setSelected(false);
            iv_female.setSelected(true);
        }
        iv_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_male.setSelected(true);
                iv_female.setSelected(false);
            }
        });
        iv_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_male.setSelected(false);
                iv_female.setSelected(true);
            }
        });
        dialog.findViewById(R.id.tv_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (callback != null) {
                    callback.onSelectSex(iv_male.isSelected());
                }
            }
        });
        dialog.show();
    }

    public static void showCustomerDialog(Context context) {
        Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.dialog_customer);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.BOTTOM;
        dialog.getWindow().setAttributes(params);
        dialog.findViewById(R.id.tv_customer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + ""));//跳转到拨号界面，同时传递电话号码
                context.startActivity(dialIntent);
            }
        });
        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void showSelectAvatarDialog(Context context, SelectAvatarCallback callback) {
        Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.dialog_select_avatar);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.BOTTOM;
        dialog.getWindow().setAttributes(params);
        dialog.findViewById(R.id.tv_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (callback != null) {
                    callback.onSelectAvatar(false);
                }
            }
        });
        dialog.findViewById(R.id.tv_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (callback != null) {
                    callback.onSelectAvatar(true);
                }
            }
        });
        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void showDatePickerDialog(Context context, final DataPickerCallback callback) {
        final Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.dialog_date_picker);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.datePicker);
        final Calendar c = Calendar.getInstance();
        datePicker.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(year, month, dayOfMonth);
            }
        });
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btn_confirm) {
                    if (callback != null) {
                        String result = DateFormat.format("yyyy-MM-dd", c).toString();
                        callback.onConfirmClick(result);
                        dialog.dismiss();
                    }
                } else {
                    dialog.dismiss();
                }
            }
        };
        dialog.findViewById(R.id.btn_cancel).setOnClickListener(listener);
        dialog.findViewById(R.id.btn_confirm).setOnClickListener(listener);
        dialog.show();
    }

    public static void showVideoMoreDialog(Context context, SelectMoreCallback callback) {
        Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.dialog_video_more);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.BOTTOM;
        dialog.getWindow().setAttributes(params);
        dialog.findViewById(R.id.tv_collect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (callback != null) {
                    callback.onClick(0);
                }
            }
        });
        dialog.findViewById(R.id.tv_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (callback != null) {
                    callback.onClick(1);
                }
            }
        });
        dialog.findViewById(R.id.tv_report).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (callback != null) {
                    callback.onClick(2);
                }
            }
        });
        dialog.findViewById(R.id.tv_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (callback != null) {
                    callback.onClick(3);
                }
            }
        });
        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void showShieldRedEnvelopeDialog(Context context) {
        Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.dialog_shield_red_envelope);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(params);
        dialog.findViewById(R.id.ll_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void showVersionUpdateDialog(Context context, boolean isForce, String versionName, String versionContent, String url, String domain_pc_name, String android_mandatory_update_type) {
        if (checkUpdateInfo(context, versionName)) {
            Dialog dialog = new Dialog(context, R.style.dialog);
            dialog.setContentView(R.layout.dialog_version_update);
            dialog.setCancelable(!isForce);
            dialog.setCanceledOnTouchOutside(false);
            TextView tv_version_name = dialog.findViewById(R.id.tv_version_name);
            TextView tv_version_content = dialog.findViewById(R.id.tv_version_content);
            if (!TextUtils.isEmpty(versionName)) {
                tv_version_name.setText(versionName);
            }
            if (!TextUtils.isEmpty(versionContent)) {
                tv_version_content.setText(versionContent);
            }
            if (isForce) {
                dialog.findViewById(R.id.tv_cancel).setVisibility(View.GONE);
                dialog.findViewById(R.id.line).setVisibility(View.GONE);
            }
            dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
//                    Intent intent = new Intent();
//                    intent.setAction(Intent.ACTION_VIEW);
//                    Uri content_url = Uri.parse(url);
//                    intent.setData(content_url);
//                    context.startActivity(intent);
                    if ("0".equals(android_mandatory_update_type)) {
                        transferToGooglePlay(context);
                    } else if ("1".equals(android_mandatory_update_type)) {
                        goToDomain(context, domain_pc_name);
                    }
                }
            });
            dialog.show();
        }
    }


    static void goToDomain(Context context, String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        context.startActivity(intent);
    }

    static String googlePlay = "com.android.vending";

    static void transferToGooglePlay(Context context) {
        try {
            Uri uri = Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage(googlePlay);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean checkUpdateInfo(Context context, String serviceVersionCode) {//添加检查服务器更新的代码
        PackageManager mPackageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = mPackageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        String versionName = packageInfo.versionName;

        String mCurrentVersionCode = versionName.replaceAll("\\.", "");
        serviceVersionCode = serviceVersionCode.replaceAll("\\.", "");
        if (mCurrentVersionCode.compareTo(serviceVersionCode) >= 0) {// 版本号不低 不需要更新
            if (context instanceof SettingActivity) {
                ToastUtil.show(context.getString(R.string.tip_app_update_fail));
            }
            return false;
        } else {
            return true;
        }
    }

    public static Dialog showAnchorFollowDialog(Context context, LiveUserBean userBean, SimpleCallback callback) {
        Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.dialog_anchor_follow);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.BOTTOM;
        dialog.getWindow().setAttributes(params);
        ImageView iv_avatar = dialog.findViewById(R.id.iv_avatar);
        GlideUtil.loadUserImageDefault(context, userBean.getAvatar(), iv_avatar);
        TextView tv_name = dialog.findViewById(R.id.tv_name);
        if (!TextUtils.isEmpty(userBean.getUser_nickname())) {
            tv_name.setText(userBean.getUser_nickname());
        }
        ImageView iv_level = dialog.findViewById(R.id.iv_level);
        GlideUtil.loadUserImageDefault(context, userBean.getVotestotal_icon(), iv_level);
        TextView tv_desc = dialog.findViewById(R.id.tv_desc);
        if (!TextUtils.isEmpty(userBean.getSignature())) {
            tv_desc.setText(userBean.getSignature());
        }
        dialog.findViewById(R.id.tv_follow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (callback != null) {
                    callback.onConfirmClick(dialog, "");
                }
            }
        });
        dialog.show();
        return dialog;
    }

    public static void showPrivacyPolicyDialog(Context context, String privacyUrl, String protocolUrl, SimpleCallback3 callback) {
        final Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.dialog_privacy_policy);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        TextView content = dialog.findViewById(R.id.content);
        String textOne = "感谢您信任并使用【龙牙直播】!\n我们非常重视您的个人信息和隐私保护,为了更好的保障您的个人权益,在您使用我们的产品前,请您认真阅读";
        String textTwo = "《用户协议》";
        String textThree = "和";
        String textFour = "《隐私政策》";
        String textFive = "的全部内容,同意并接受全部条款后开始使用我们的产品和服务。若选择不同意,将无法使用我们的产品和服务,并会退出应用";
        SpannableStringBuilder style = new SpannableStringBuilder();
        style.append(textOne + textTwo + textThree + textFour + textFive);
        //设置部分文字点击事件
        ClickableSpan clickableSpanOne = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                WebViewActivity.forward(context, protocolUrl);
            }
        };
        ClickableSpan clickableSpanTwo = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                WebViewActivity.forward(context, privacyUrl);
            }
        };
        style.setSpan(clickableSpanOne, textOne.length(), textOne.length() + textTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(clickableSpanTwo, textOne.length() + textTwo.length() + textThree.length(), textOne.length() + textTwo.length() + textThree.length() + textFour.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置部分文字颜色
        style.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.c_E3AC72)), textOne.length(), textOne.length() + textTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.c_E3AC72)), textOne.length() + textTwo.length() + textThree.length(), textOne.length() + textTwo.length() + textThree.length() + textFour.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //配置给TextView
        content.setMovementMethod(LinkMovementMethod.getInstance());
        content.setText(style);

        dialog.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (callback != null) {
                    callback.onClick(true);
                }
            }
        });
        dialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (callback != null) {
                    callback.onClick(false);
                }
            }
        });
        dialog.show();
    }

    public static void showSelectPullUrlDialog(Context context, String hdUrl, String sdUrl, SelectPullUrlBack callback) {
        final Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.dialog_select_pull_url);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        TextView tv_HD = (TextView) dialog.findViewById(R.id.tv_HD);
        TextView tv_SD = (TextView) dialog.findViewById(R.id.tv_SD);
        if (!TextUtils.isEmpty(hdUrl)) {
            dialog.findViewById(R.id.line_hd).setVisibility(View.VISIBLE);
            tv_HD.setVisibility(View.VISIBLE);
        } else {
            dialog.findViewById(R.id.line_hd).setVisibility(View.GONE);
            tv_HD.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(sdUrl)) {
            dialog.findViewById(R.id.line_sd).setVisibility(View.VISIBLE);
            tv_SD.setVisibility(View.VISIBLE);
        } else {
            dialog.findViewById(R.id.line_sd).setVisibility(View.GONE);
            tv_SD.setVisibility(View.GONE);
        }
        tv_HD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (callback != null) {
                    callback.onSelectUrl(hdUrl);
                }
            }
        });
        tv_SD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (callback != null) {
                    callback.onSelectUrl(sdUrl);
                }
            }
        });
        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void showSelectSubscribeDialog(Context context, String matchTitle, SelectSubscribeBack callback) {
        final Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.dialog_select_subscribe);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.BOTTOM;
        dialog.getWindow().setAttributes(params);
        TextView tv_match_title = (TextView) dialog.findViewById(R.id.tv_match_title);
        tv_match_title.setText(matchTitle);
        TextView tv_save = (TextView) dialog.findViewById(R.id.tv_save);
        CheckBox checkBox1 = (CheckBox) dialog.findViewById(R.id.checkbox_1);
        CheckBox checkBox2 = (CheckBox) dialog.findViewById(R.id.checkbox_2);
        CheckBox checkBox3 = (CheckBox) dialog.findViewById(R.id.checkbox_3);
        CheckBox checkBox4 = (CheckBox) dialog.findViewById(R.id.checkbox_4);
        CheckBox checkBox5 = (CheckBox) dialog.findViewById(R.id.checkbox_5);
        CheckBox checkBox6 = (CheckBox) dialog.findViewById(R.id.checkbox_6);
        Switch btn_switch = (Switch) dialog.findViewById(R.id.btn_switch);
        btn_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            boolean checked1, checked2, checked3, checked4, checked5, checked6;

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    checked1 = checkBox1.isChecked();
                    checked2 = checkBox2.isChecked();
                    checked3 = checkBox3.isChecked();
                    checked4 = checkBox4.isChecked();
                    checked5 = checkBox5.isChecked();
                    checked6 = checkBox6.isChecked();
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                    checkBox4.setChecked(false);
                    checkBox5.setChecked(false);
                    checkBox6.setChecked(false);
                } else {
                    checkBox1.setChecked(checked1);
                    checkBox2.setChecked(checked2);
                    checkBox3.setChecked(checked3);
                    checkBox4.setChecked(checked4);
                    checkBox5.setChecked(checked5);
                    checkBox6.setChecked(checked6);
                }
            }
        });
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (callback != null) {
                    callback.onSelectSubscribe(checkBox1.isChecked() ? 1 : 0, checkBox2.isChecked() ? 1 : 0, checkBox3.isChecked() ? 1 : 0, checkBox4.isChecked() ? 1 : 0, checkBox5.isChecked() ? 1 : 0, checkBox6.isChecked() ? 1 : 0);
                }
            }
        });
        dialog.show();
    }

    public interface SimpleCallback3 {
        void onClick(boolean isConfirm);
    }

    public static class Builder {

        private Context mContext;
        private String mTitle;
        private String mContent;
        private String mConfrimString;
        private String mCancelString;
        private boolean mCancelable;
        private boolean mBackgroundDimEnabled;//显示区域以外是否使用黑色半透明背景
        private boolean mInput;//是否是输入框的
        private String mHint;
        private int mInputType;
        private int mLength;
        private String mDigits;
        private SimpleCallback mClickCallback;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public Builder setContent(String content) {
            mContent = content;
            return this;
        }

        public Builder setConfrimString(String confrimString) {
            mConfrimString = confrimString;
            return this;
        }

        public Builder setCancelString(String cancelString) {
            mCancelString = cancelString;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            mCancelable = cancelable;
            return this;
        }

        public Builder setBackgroundDimEnabled(boolean backgroundDimEnabled) {
            mBackgroundDimEnabled = backgroundDimEnabled;
            return this;
        }

        public Builder setInput(boolean input) {
            mInput = input;
            return this;
        }

        public Builder setHint(String hint) {
            mHint = hint;
            return this;
        }

        public Builder setInputType(int inputType) {
            mInputType = inputType;
            return this;
        }

        public Builder setLength(int length) {
            mLength = length;
            return this;
        }

        public Builder setDigits(String digits) {
            this.mDigits = digits;
            return this;
        }

        public Builder setClickCallback(SimpleCallback clickCallback) {
            mClickCallback = clickCallback;
            return this;
        }

        public Dialog build() {
            final Dialog dialog = new Dialog(mContext, R.style.dialog);
            dialog.setContentView(mInput ? R.layout.dialog_input : R.layout.dialog_simple);
            dialog.setCancelable(mCancelable);
            dialog.setCanceledOnTouchOutside(mCancelable);
            TextView titleView = (TextView) dialog.findViewById(R.id.title);
            if (!TextUtils.isEmpty(mTitle)) {
                titleView.setText(mTitle);
            }
            final TextView content = (TextView) dialog.findViewById(R.id.content);
            if (!TextUtils.isEmpty(mHint)) {
                content.setHint(mHint);
            }
            if (!TextUtils.isEmpty(mContent)) {
                content.setText(mContent);
            }
            if (!TextUtils.isEmpty(mDigits)) {
                content.setKeyListener(DigitsKeyListener.getInstance(mDigits));
            }
            if (mInputType == INPUT_TYPE_NUMBER) {
                content.setInputType(InputType.TYPE_CLASS_NUMBER);
            } else if (mInputType == INPUT_TYPE_NUMBER_PASSWORD) {
                content.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
            } else if (mInputType == INPUT_TYPE_TEXT_PASSWORD) {
                content.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            if (mLength > 0 && content instanceof EditText) {
                content.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mLength)});
                ((EditText) content).setSelection(mContent.length());
            }
            TextView btnConfirm = (TextView) dialog.findViewById(R.id.btn_confirm);
            if (!TextUtils.isEmpty(mConfrimString)) {
                btnConfirm.setText(mConfrimString);
            }
            TextView btnCancel = (TextView) dialog.findViewById(R.id.btn_cancel);
            if (!TextUtils.isEmpty(mCancelString)) {
                btnCancel.setText(mCancelString);
            }
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getId() == R.id.btn_confirm) {
                        if (mClickCallback != null) {
                            if (mInput) {
                                mClickCallback.onConfirmClick(dialog, content.getText().toString());
                            } else {
                                dialog.dismiss();
                                mClickCallback.onConfirmClick(dialog, "");
                            }
                        } else {
                            dialog.dismiss();
                        }
                    } else {
                        dialog.dismiss();
                        if (mClickCallback instanceof SimpleCallback2) {
                            ((SimpleCallback2) mClickCallback).onCancelClick();
                        }
                    }
                }
            };
            btnConfirm.setOnClickListener(listener);
            btnCancel.setOnClickListener(listener);
            return dialog;
        }

    }

    public interface DataPickerCallback {
        void onConfirmClick(String date);
    }

    public interface SelectAvatarCallback {
        void onSelectAvatar(boolean isTakePhoto);
    }

    public interface SelectSexCallback {
        void onSelectSex(boolean isMale);
    }

    public interface SelectMoreCallback {
        void onClick(int type);
    }

    public interface SelectPullUrlBack {
        void onSelectUrl(String url);
    }

    public interface SelectSubscribeBack {
        void onSelectSubscribe(int start, int out, int wickets, int miles, int delay, int result);
    }

    public interface StringArrayDialogCallback {
        void onItemClick(String text, int tag);
    }

    public interface SimpleCallback {
        void onConfirmClick(Dialog dialog, String content);
    }

    public interface SimpleCallback2 extends SimpleCallback {
        void onCancelClick();
    }

    /**
     * 城市选择
     */
    public static void showCityChooseDialog(Activity activity, ArrayList<Province> list,
                                            String province, String city, String district, AddressPicker.OnAddressPickListener listener) {
        AddressPicker picker = new AddressPicker(activity, list);
        picker.setTextColor(0xff323232);
        picker.setDividerColor(0xffdcdcdc);
        picker.setAnimationStyle(R.style.bottomToTopAnim);
        picker.setCancelTextColor(0xff969696);
        picker.setSubmitTextColor(0xff00abf0);
        picker.setTopLineColor(0xfff5f5f5);
        picker.setTopBackgroundColor(0xfff5f5f5);
        picker.setHeight(DpUtil.dp2px(250));
        picker.setOffset(5);
        picker.setHideProvince(false);
        picker.setHideCounty(false);
        picker.setColumnWeight(3 / 9.0f, 3 / 9.0f, 3 / 9.0f);
        if (TextUtils.isEmpty(province)) {
            province = "北京市";
        }
        if (TextUtils.isEmpty(city)) {
            city = "北京市";
        }
        if (TextUtils.isEmpty(district)) {
            district = "东城区";
        }
        picker.setSelectedItem(province, city, district);
        picker.setOnAddressPickListener(listener);
        picker.show();
    }
}
