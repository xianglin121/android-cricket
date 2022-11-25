package com.longya.live.view.user;

import com.longya.live.model.HeadlineBean;
import com.longya.live.model.JsonBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface MySpaceHeadlineOneView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<HeadlineBean> list);

    void doDeleteSuccess(int position);
}
