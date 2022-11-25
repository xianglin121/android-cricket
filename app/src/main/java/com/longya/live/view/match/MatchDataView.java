package com.longya.live.view.match;

import com.longya.live.model.JsonBean;
import com.longya.live.model.MatchDataClassifyBean;
import com.longya.live.model.MatchDataFirstBean;
import com.longya.live.model.MatchDataFollowBean;
import com.longya.live.model.MatchDataSecondBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface MatchDataView extends BaseView<JsonBean> {
    void getFollowListSuccess(List<MatchDataFollowBean> list);

    void getClassifyListSuccess(List<MatchDataClassifyBean> list);

    void getCountryListSuccess(List<MatchDataFirstBean> list);

    void getListSuccess(int position, List<MatchDataSecondBean> list);
}
