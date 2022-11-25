package com.longya.live.view.user;

import com.longya.live.model.BalanceBean;
import com.longya.live.model.JsonBean;
import com.longya.live.model.UserBean;
import com.longya.live.model.WithdrawBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface MyWalletView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<BalanceBean> list);

    void getWithdrawDataSuccess(boolean isRefresh, List<WithdrawBean> list);
}
