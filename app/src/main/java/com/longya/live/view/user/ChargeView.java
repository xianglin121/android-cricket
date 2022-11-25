package com.longya.live.view.user;

import com.longya.live.model.CoinBean;
import com.longya.live.model.JsonBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface ChargeView extends BaseView<List<CoinBean>> {
    void paySuccess(String url);

    void payFail(String msg);
}
