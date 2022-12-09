package com.onecric.live.activity;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.SparseArray;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.onecric.live.R;
import com.onecric.live.custom.LineControllerView;
import com.onecric.live.event.ChangeFootballLanguageEvent;
import com.onecric.live.event.UpdateRedAndYellowCardEvent;
import com.onecric.live.model.JsonBean;
import com.onecric.live.presenter.match.FootballMatchSettingPresenter;
import com.onecric.live.util.DialogUtil;
import com.onecric.live.util.SpUtil;
import com.onecric.live.util.WordUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.match.FootballMatchSettingView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class FootballMatchSettingActivity extends MvpActivity<FootballMatchSettingPresenter> implements FootballMatchSettingView, View.OnClickListener {

    public static void forward(Context context) {
        Intent intent = new Intent(context, FootballMatchSettingActivity.class);
        context.startActivity(intent);
    }

    private LineControllerView line_language;
    private LineControllerView line_red_and_yellow;
    private LineControllerView line_event_range;
    private LineControllerView line_goal_sound;
    private LineControllerView line_goal_vibrator;
    private LineControllerView line_red_card_sound;
    private LineControllerView line_red_card_vibrator;
    private OptionsPickerView pvCustomOptions;
    private LineControllerView view_corner_kick;

    @Override
    protected FootballMatchSettingPresenter createPresenter() {
        return new FootballMatchSettingPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_football_match_setting;
    }

    @Override
    protected void initView() {
        setTitleText(WordUtil.getString(this, R.string.football_match_setting_title));

        line_language = findViewById(R.id.line_language);
        line_red_and_yellow = findViewById(R.id.line_red_and_yellow);
        line_event_range = findViewById(R.id.line_event_range);
        line_goal_sound = findViewById(R.id.line_goal_sound);
        line_goal_vibrator = findViewById(R.id.line_goal_vibrator);
        line_red_card_sound = findViewById(R.id.line_red_card_sound);
        line_red_card_vibrator = findViewById(R.id.line_red_card_vibrator);
        view_corner_kick = findViewById(R.id.view_corner_kick);

        line_language.setOnClickListener(this);
        line_event_range.setOnClickListener(this);
        line_red_and_yellow.setCheckListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SpUtil.getInstance().setBooleanValue(SpUtil.RED_AND_YELLOW_CARD, line_red_and_yellow.isChecked());
                EventBus.getDefault().post(new UpdateRedAndYellowCardEvent());
            }
        });
        line_goal_sound.setCheckListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SpUtil.getInstance().setBooleanValue(SpUtil.GOAL_SOUND, line_goal_sound.isChecked());
            }
        });
        line_goal_vibrator.setCheckListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SpUtil.getInstance().setBooleanValue(SpUtil.GOAL_VIBRATOR, line_goal_vibrator.isChecked());
            }
        });
        line_red_card_sound.setCheckListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SpUtil.getInstance().setBooleanValue(SpUtil.RED_CARD_SOUND, line_red_card_sound.isChecked());
            }
        });
        line_red_card_vibrator.setCheckListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SpUtil.getInstance().setBooleanValue(SpUtil.RED_CARD_VIBRATOR, line_red_card_vibrator.isChecked());
            }
        });
    }

    @Override
    protected void initData() {
        if ("1".equals(SpUtil.getInstance().getStringValue(SpUtil.FOOTBALL_LANGUAGE))) {//繁体中文
            line_language.setContent(getString(R.string.language_zht));
        }else if ("2".equals(SpUtil.getInstance().getStringValue(SpUtil.FOOTBALL_LANGUAGE))) {//英文
            line_language.setContent(getString(R.string.language_en));
        }else {
            line_language.setContent(getString(R.string.language_zh));
        }

        if (SpUtil.getInstance().getIntValue(SpUtil.EVENT_NOTIFICATION_RANGE) == 1) {
            line_event_range.setContent("仅收藏");
        }else {
            line_event_range.setContent("全部");
        }

        line_red_and_yellow.setChecked(SpUtil.getInstance().getBooleanValue(SpUtil.RED_AND_YELLOW_CARD));
        line_goal_sound.setChecked(SpUtil.getInstance().getBooleanValue(SpUtil.GOAL_SOUND));
        line_goal_vibrator.setChecked(SpUtil.getInstance().getBooleanValue(SpUtil.GOAL_VIBRATOR));
        line_red_card_sound.setChecked(SpUtil.getInstance().getBooleanValue(SpUtil.RED_CARD_SOUND));
        line_red_card_vibrator.setChecked(SpUtil.getInstance().getBooleanValue(SpUtil.RED_CARD_VIBRATOR));

        String one = WordUtil.getString(this, R.string.corner_kick_tip);
        String two = WordUtil.getString(this, R.string.on_the_spot);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(one + two);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.c_999999)), one.length(), spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        view_corner_kick.setName(spannableStringBuilder);
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
                        String language = SpUtil.getInstance().getStringValue(SpUtil.FOOTBALL_LANGUAGE);
                        if (!TextUtils.isEmpty(language)) {
                            if (!name.equals(language)) {
                                line_language.setContent(name);
                                SpUtil.getInstance().setStringValue(SpUtil.FOOTBALL_LANGUAGE, String.valueOf(options1));
                                EventBus.getDefault().post(new ChangeFootballLanguageEvent());
                            }
                        }else {
                            if (getString(R.string.language_zh).equals(name)) {
                                SpUtil.getInstance().setStringValue(SpUtil.FOOTBALL_LANGUAGE, "0");
                            }else {
                                line_language.setContent(name);
                                SpUtil.getInstance().setStringValue(SpUtil.FOOTBALL_LANGUAGE, String.valueOf(options1));
                                EventBus.getDefault().post(new ChangeFootballLanguageEvent());
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
            case R.id.line_event_range:
                SparseArray<String> array = new SparseArray<>();
                array.put(0, "全部");
                array.put(1, "仅收藏");
                DialogUtil.showStringArrayDialog(this, array, new DialogUtil.StringArrayDialogCallback() {
                    @Override
                    public void onItemClick(String text, int tag) {
                        if ("全部".equals(text)) {
                            SpUtil.getInstance().setIntValue(SpUtil.EVENT_NOTIFICATION_RANGE, 0);
                            line_event_range.setContent("全部");
                        }else if ("仅收藏".equals(text)) {
                            SpUtil.getInstance().setIntValue(SpUtil.EVENT_NOTIFICATION_RANGE, 1);
                            line_event_range.setContent("仅收藏");
                        }
                    }
                });
                break;
        }
    }
}
