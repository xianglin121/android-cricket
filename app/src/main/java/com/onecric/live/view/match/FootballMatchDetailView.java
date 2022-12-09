package com.onecric.live.view.match;

import com.onecric.live.model.FootballDetailBean;
import com.onecric.live.model.ReportBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface FootballMatchDetailView extends BaseView<FootballDetailBean> {
    void getReportListSuccess(List<ReportBean> list);

    void doReportSuccess();
}
