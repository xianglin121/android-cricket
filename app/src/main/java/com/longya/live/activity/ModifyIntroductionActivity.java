package com.longya.live.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.longya.live.CommonCode;
import com.longya.live.R;
import com.longya.live.util.ToastUtil;
import com.longya.live.view.BaseActivity;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/12/4
 */
public class ModifyIntroductionActivity extends BaseActivity {
    public static void forwardForResult(Activity activity, String introduction) {
        Intent intent = new Intent(activity, ModifyIntroductionActivity.class);
        intent.putExtra("introduction", introduction);
        activity.startActivityForResult(intent, CommonCode.REQUEST_CODE_MODIFY_INTRODUCTION);
    }

    private EditText et_input;

    @Override
    public boolean getStatusBarTextColor() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_introduction;
    }

    @Override
    protected void initView() {
        setTitleText(getString(R.string.title_update_introduction));
        String introduction = getIntent().getStringExtra("introduction");

        et_input = findViewById(R.id.et_input);

        findViewById(R.id.tv_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_input.getText().toString())) {
                    ToastUtil.show(getString(R.string.hint_input_introduction));
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("introduction", et_input.getText().toString());
                setResult(CommonCode.RESULT_CODE_MODIFY_INTRODUCTION, intent);
                finish();
            }
        });

        if (!TextUtils.isEmpty(introduction)) {
            et_input.setText(introduction);
            et_input.setSelection(introduction.length());
        }
    }

    @Override
    protected void initData() {

    }
}
