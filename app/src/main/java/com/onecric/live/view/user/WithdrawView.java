package com.onecric.live.view.user;

import com.onecric.live.model.AccountBean;
import com.onecric.live.view.BaseView;

public interface WithdrawView extends BaseView<AccountBean> {
    void getCodeSuccess();

    void getBalanceSuccess(int isPayPwd, String balance, double withdrawalRadio);

    void applyWithdrawSuccess();
}
