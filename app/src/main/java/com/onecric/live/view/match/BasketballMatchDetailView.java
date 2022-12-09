package com.onecric.live.view.match;

import com.onecric.live.model.BasketballDetailBean;
import com.onecric.live.model.ReportBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface BasketballMatchDetailView extends BaseView<BasketballDetailBean> {
    void getReportListSuccess(List<ReportBean> list);

    void doReportSuccess();
}
