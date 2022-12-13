package com.onecric.live.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.onecric.live.CommonCode;
import com.onecric.live.R;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.BaseActivity;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/12/4
 */
public class ModifyNickNameActivity extends BaseActivity {
    public static void forwardForResult(Activity activity, String nickname) {
        Intent intent = new Intent(activity, ModifyNickNameActivity.class);
        intent.putExtra("nickname", nickname);
        activity.startActivityForResult(intent, CommonCode.REQUEST_CODE_MODIFY_NICKNAME);
    }

    private TextView tv_name;
    private EditText et_input;

    @Override
    public boolean getStatusBarTextColor() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_nickname;
    }

    @Override
    protected void initView() {
        setTitleText(getString(R.string.title_update_nickname));
        String nickname = getIntent().getStringExtra("nickname");

        tv_name = findViewById(R.id.tv_name);
        et_input = findViewById(R.id.et_input);

        findViewById(R.id.tv_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_input.getText().toString())) {
                    ToastUtil.show(getString(R.string.empty_input_nickname));
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("nickname", et_input.getText().toString());
                setResult(CommonCode.RESULT_CODE_MODIFY_NICKNAME, intent);
                finish();
            }
        });

        if (!TextUtils.isEmpty(nickname)) {
            tv_name.setText(nickname);
        }
    }

    @Override
    protected void initData() {

    }
}
