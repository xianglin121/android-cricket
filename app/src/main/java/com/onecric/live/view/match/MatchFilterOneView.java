package com.onecric.live.view.match;

import com.onecric.live.model.FilterBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface MatchFilterOneView extends BaseView<JsonBean> {
    void getDataSuccess(List<FilterBean> competitionList, List<FilterBean> countryList, int competitionCount, int selectCompetitionCount);
}
