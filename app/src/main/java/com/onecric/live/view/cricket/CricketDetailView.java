package com.onecric.live.view.cricket;

import com.onecric.live.model.CricketMatchBean;
import com.onecric.live.model.UpdatesBean;
import com.onecric.live.view.BaseView;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/26
 */
public interface CricketDetailView extends BaseView<CricketMatchBean> {
    void getUpdatesDataSuccess(List<UpdatesBean> list);
}
