package com.onecric.live.view.user;

import com.onecric.live.model.JsonBean;
import com.onecric.live.model.SpeakerBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface SpeakerHistoryView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<SpeakerBean> list);
}
