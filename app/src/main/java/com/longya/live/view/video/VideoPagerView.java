package com.longya.live.view.video;

import com.longya.live.model.JsonBean;
import com.longya.live.model.ReportBean;
import com.longya.live.model.ShortVideoBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface VideoPagerView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<ShortVideoBean> list);

    void getReportListSuccess(List<ReportBean> list);

    void doCommentSuccess(Integer cid);

    void doReportSuccess();
}
