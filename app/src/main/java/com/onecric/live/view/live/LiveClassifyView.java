package com.onecric.live.view.live;

import com.onecric.live.model.JsonBean;
import com.onecric.live.model.LiveClassifyBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface LiveClassifyView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<LiveClassifyBean> list);

    void doReserveSuccess(int position);
}
