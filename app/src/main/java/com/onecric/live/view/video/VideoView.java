package com.onecric.live.view.video;

import com.onecric.live.model.JsonBean;
import com.onecric.live.model.OneVideoBean;
import com.onecric.live.model.ShortVideoBean;
import com.onecric.live.model.VideoCategoryBean;
import com.onecric.live.model.VideoShowBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface VideoView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<ShortVideoBean> list);
    void getCategorySuccess(List<VideoCategoryBean> list);
    void getInnerDataSuccess(List<OneVideoBean.FirstCategoryBean> tList, List<OneVideoBean.SecondCategoryBean> othersList, List<VideoShowBean> showsList,OneVideoBean.BannerBean  banner);
}
