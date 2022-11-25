package com.longya.live.view.user;

import com.longya.live.model.AccountBean;
import com.longya.live.model.JsonBean;
import com.longya.live.view.BaseView;

public interface WithdrawView extends BaseView<AccountBean> {
    void getCodeSuccess();

    void getBalanceSuccess(int isPayPwd, String balance, double withdrawalRadio);

    void applyWithdrawSuccess();
}
