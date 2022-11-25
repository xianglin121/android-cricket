package com.longya.live.view.user;

import com.longya.live.model.JsonBean;
import com.longya.live.model.LiveBean;
import com.longya.live.model.SpeakerBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface SpeakerHistoryView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<SpeakerBean> list);
}
