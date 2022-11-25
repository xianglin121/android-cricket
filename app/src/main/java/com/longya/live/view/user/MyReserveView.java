package com.longya.live.view.user;

import com.longya.live.model.JsonBean;
import com.longya.live.model.ReserveMatchBean;
import com.longya.live.model.UserBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface MyReserveView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<ReserveMatchBean> list);

    void doReserveSuccess(int position);
}
