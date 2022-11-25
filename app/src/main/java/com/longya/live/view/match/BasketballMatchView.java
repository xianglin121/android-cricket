package com.longya.live.view.match;

import com.longya.live.model.JsonBean;
import com.longya.live.view.BaseView;

public interface BasketballMatchView extends BaseView<JsonBean> {
    void getCollectCountSuccess(int count);
}
