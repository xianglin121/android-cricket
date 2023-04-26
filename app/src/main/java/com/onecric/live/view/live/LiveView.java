package com.onecric.live.view.live;

import com.onecric.live.model.JsonBean;
import com.onecric.live.model.LiveBean;
import com.onecric.live.model.LiveFiltrateBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface LiveView extends BaseView<JsonBean> {
    void getOtherDataSuccess(List<LiveFiltrateBean> list);
}
