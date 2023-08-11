package com.onecric.live.view.live;

import com.onecric.live.model.BannerBean;
import com.onecric.live.model.GameBannerBean;
import com.onecric.live.model.GameHistoryBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.LiveAuthorBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface OneGameView extends BaseView<JsonBean> {
    void getAllDataSuccess(List<LiveAuthorBean> aList);
    void getBannerSuccess(List<GameBannerBean> list);
    void getDataHistorySuccess(boolean isRefresh,List<GameHistoryBean> list);
    void getAdvertSuccess(List<BannerBean> list);
    void doFollowSuccess(int position,boolean isFollow);
}
