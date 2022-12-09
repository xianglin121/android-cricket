package com.onecric.live.view.match;

import com.onecric.live.model.JsonBean;
import com.onecric.live.model.MatchListBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface HotMatchInnerView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<MatchListBean> list);
}
