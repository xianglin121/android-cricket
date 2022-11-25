package com.longya.live.activity;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.longya.live.R;
import com.longya.live.custom.LineControllerView;
import com.longya.live.event.ChangeBasketballLanguageEvent;
import com.longya.live.model.JsonBean;
import com.longya.live.presenter.match.BasketballMatchSettingPresenter;
import com.longya.live.presenter.match.FootballMatchSettingPresenter;
import com.longya.live.util.DialogUtil;
import com.longya.live.util.SpUtil;
import com.longya.live.util.WordUtil;
import com.longya.live.view.MvpActivity;
import com.longya.live.view.match.BasketballMatchSettingView;
import com.longya.live.view.match.FootballMatchSettingView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class BasketballMatchSettingActivity extends MvpActivity<BasketballMatchSettingPresenter> implements BasketballMatchSettingView, View.OnClickListener {

    public static void forward(Context context) {
        Intent intent = new Intent(context, BasketballMatchSettingActivity.class);
        context.startActivity(intent);
    }

    private LineControllerView line_language;
    private OptionsPickerView pvCustomOptions;

    @Override
    protected BasketballMatchSettingPresenter createPresenter() {
        return new BasketballMatchSettingPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_basketball_match_setting;
    }

    @Override
    protected void initView() {
        setTitleText(WordUtil.getString(this, R.string.basketball_match_setting_title));

        line_language = findViewById(R.id.line_language);

        line_language.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        if ("1".equals(SpUtil.getInstance().getStringValue(SpUtil.BASKETBALL_LANGUAGE))) {//繁体中文
            line_language.setContent(getString(R.string.language_zht));
        }else if ("2".equals(SpUtil.getInstance().getStringValue(SpUtil.BASKETBALL_LANGUAGE))) {//英文
            line_language.setContent(getString(R.string.language_en));
        }else {
            line_language.setContent(getString(R.string.language_zh));
        }
    }

    @Override
    public void getDataSuccess(JsonBean model) {

    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.line_language:
                List<String> list = new ArrayList<>();
                list.add(getString(R.string.language_zh));
                list.add(getString(R.string.language_zht));
                list.add(getString(R.string.language_en));
                pvCustomOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        String name = list.get(options1);
                        String language = SpUtil.getInstance().getStringValue(SpUtil.BASKETBALL_LANGUAGE);
                        if (!TextUtils.isEmpty(language)) {
                            if (!name.equals(language)) {
                                line_language.setContent(name);
                                SpUtil.getInstance().setStringValue(SpUtil.BASKETBALL_LANGUAGE, String.valueOf(options1));
                                EventBus.getDefault().post(new ChangeBasketballLanguageEvent());
                            }
                        }else {
                            if (getString(R.string.language_zh).equals(name)) {
                                SpUtil.getInstance().setStringValue(SpUtil.BASKETBALL_LANGUAGE, "0");
                            }else {
                                line_language.setContent(name);
                                SpUtil.getInstance().setStringValue(SpUtil.BASKETBALL_LANGUAGE, String.valueOf(options1));
                                EventBus.getDefault().post(new ChangeBasketballLanguageEvent());
                            }
                        }
                    }
                })
                        .setLayoutRes(R.layout.pickerview_choose_language, new CustomListener() {
                            @Override
                            public void customLayout(View v) {
                                TextView tv_cancel = v.findViewById(R.id.tv_cancel);
                                TextView tv_confirm = v.findViewById(R.id.tv_confirm);
                                tv_confirm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        pvCustomOptions.returnData();
                                        pvCustomOptions.dismiss();
                                    }
                                });

                                tv_cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        pvCustomOptions.dismiss();
                                    }
                                });
                            }
                        })
                        .isDialog(false)
                        .setOutSideCancelable(true)
                        .build();

                pvCustomOptions.setPicker(list);//添加数据
                pvCustomOptions.show();
                break;
        }
    }
}
