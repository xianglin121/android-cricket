package com.onecric.live.view.user;

import com.onecric.live.model.HeadlineBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface MySpaceHeadlineOneView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<HeadlineBean> list);

    void doDeleteSuccess(int position);
}
