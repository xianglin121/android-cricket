package com.onecric.live.view;

import com.onecric.live.model.CricketAllBean;
import com.onecric.live.model.CricketFiltrateBean;
import com.onecric.live.model.JsonBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/26
 */
public interface CricketNewView extends BaseView<JsonBean> {
    void getDataSuccess(List<CricketFiltrateBean> list);

    void getDataSuccess(int type, CricketAllBean bean);

    void getDataFail(int type ,String msg);

    void getRefreshSuccess(CricketAllBean bean);
}
