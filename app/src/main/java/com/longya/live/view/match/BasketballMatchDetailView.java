package com.longya.live.view.match;

import com.longya.live.model.BasketballDetailBean;
import com.longya.live.model.JsonBean;
import com.longya.live.model.ReportBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface BasketballMatchDetailView extends BaseView<BasketballDetailBean> {
    void getReportListSuccess(List<ReportBean> list);

    void doReportSuccess();
}
