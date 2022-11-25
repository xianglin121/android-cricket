package com.longya.live.view.theme;

import com.longya.live.model.HeadlineBean;
import com.longya.live.model.JsonBean;
import com.longya.live.model.LiveBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface ThemeHeadlineInnerView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<HeadlineBean> list, List<HeadlineBean> banners);
}
