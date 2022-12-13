package com.onecric.live.view.user;

import com.onecric.live.model.BalanceBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.WithdrawBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface MyWalletView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<BalanceBean> list);

    void getWithdrawDataSuccess(boolean isRefresh, List<WithdrawBean> list);
}
