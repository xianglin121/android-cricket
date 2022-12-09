package com.onecric.live.view.video;

import com.onecric.live.model.JsonBean;
import com.onecric.live.model.ReportBean;
import com.onecric.live.model.ShortVideoBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface VideoPagerView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<ShortVideoBean> list);

    void getReportListSuccess(List<ReportBean> list);

    void doCommentSuccess(Integer cid);

    void doReportSuccess();
}
