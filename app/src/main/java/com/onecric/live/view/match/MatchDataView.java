package com.onecric.live.view.match;

import com.onecric.live.model.JsonBean;
import com.onecric.live.model.MatchDataClassifyBean;
import com.onecric.live.model.MatchDataFirstBean;
import com.onecric.live.model.MatchDataFollowBean;
import com.onecric.live.model.MatchDataSecondBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface MatchDataView extends BaseView<JsonBean> {
    void getFollowListSuccess(List<MatchDataFollowBean> list);

    void getClassifyListSuccess(List<MatchDataClassifyBean> list);

    void getCountryListSuccess(List<MatchDataFirstBean> list);

    void getListSuccess(int position, List<MatchDataSecondBean> list);
}
