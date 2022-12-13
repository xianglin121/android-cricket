package com.onecric.live.view.user;

import com.onecric.live.model.CoinBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface ChargeView extends BaseView<List<CoinBean>> {
    void paySuccess(String url);

    void payFail(String msg);
}
