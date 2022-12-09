package com.onecric.live.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.onecric.live.R;
import com.onecric.live.model.AccountBean;
import com.onecric.live.view.BaseActivity;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/12/17
 */
public class AccountInfoActivity extends BaseActivity {

    public static void forward(Context context, AccountBean bean) {
        Intent intent = new Intent(context, AccountInfoActivity.class);
        intent.putExtra("data", bean);
        context.startActivity(intent);
    }

    private AccountBean mAccountBean;

    private TextView tv_bank;
    private TextView tv_account;
    private TextView tv_name;
    private TextView tv_idcard;

    @Override
    public int getLayoutId() {
        return R.layout.activity_account_info;
    }

    @Override
    protected void initView() {
        mAccountBean = (AccountBean) getIntent().getSerializableExtra("data");
        tv_bank = findViewById(R.id.tv_bank);
        tv_account = findViewById(R.id.tv_account);
        tv_name = findViewById(R.id.tv_name);
        tv_idcard = findViewById(R.id.tv_idcard);

        findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAccountBean != null) {
                    BindAccountActivity.forward(mActivity, BindAccountActivity.TYPE_CHANGE, mAccountBean);
                }
            }
        });
    }

    @Override
    protected void initData() {
        if (mAccountBean != null) {
            if (!TextUtils.isEmpty(mAccountBean.getBank_name())) {
                tv_bank.setText(mAccountBean.getBank_name());
            }
            if (!TextUtils.isEmpty(mAccountBean.getCard_number_hide())) {
                tv_account.setText(mAccountBean.getCard_number_hide());
            }
            if (!TextUtils.isEmpty(mAccountBean.getName())) {
                tv_name.setText(mAccountBean.getName());
            }
            if (!TextUtils.isEmpty(mAccountBean.getId_card_hide())) {
                tv_idcard.setText(mAccountBean.getId_card_hide());
            }
        }
    }
}
