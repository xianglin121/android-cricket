package com.onecric.live.view.theme;

import com.onecric.live.model.HeadlineBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface ThemeHeadlineInnerView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<HeadlineBean> list, List<HeadlineBean> banners);
}
