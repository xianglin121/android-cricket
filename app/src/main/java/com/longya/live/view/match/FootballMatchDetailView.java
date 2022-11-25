package com.longya.live.view.match;

import com.longya.live.model.FootballDetailBean;
import com.longya.live.model.JsonBean;
import com.longya.live.model.ReportBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface FootballMatchDetailView extends BaseView<FootballDetailBean> {
    void getReportListSuccess(List<ReportBean> list);

    void doReportSuccess();
}
