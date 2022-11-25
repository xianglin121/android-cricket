package com.longya.live.view.match;

import com.longya.live.model.FilterBean;
import com.longya.live.model.JsonBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface MatchFilterTwoView extends BaseView<JsonBean> {
    void getDataSuccess(List<FilterBean> competitionList, List<FilterBean> countryList, int countryCount, int selectCountryCount);
}
