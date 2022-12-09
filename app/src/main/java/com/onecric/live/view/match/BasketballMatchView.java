package com.onecric.live.view.match;

import com.onecric.live.model.JsonBean;
import com.onecric.live.view.BaseView;

public interface BasketballMatchView extends BaseView<JsonBean> {
    void getCollectCountSuccess(int count);
}
