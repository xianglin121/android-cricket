package com.onecric.live.view.user;

import com.onecric.live.model.JsonBean;
import com.onecric.live.model.ReserveMatchBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface MyReserveView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<ReserveMatchBean> list);

    void doReserveSuccess(int position);
}
