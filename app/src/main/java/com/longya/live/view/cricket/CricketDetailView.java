package com.longya.live.view.cricket;

import com.longya.live.model.CricketMatchBean;
import com.longya.live.model.HeadlineBean;
import com.longya.live.model.JsonBean;
import com.longya.live.model.UpdatesBean;
import com.longya.live.view.BaseView;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/26
 */
public interface CricketDetailView extends BaseView<CricketMatchBean> {
    void getUpdatesDataSuccess(List<UpdatesBean> list);
}
