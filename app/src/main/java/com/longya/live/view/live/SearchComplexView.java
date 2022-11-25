package com.longya.live.view.live;

import com.longya.live.model.CommunityBean;
import com.longya.live.model.HeadlineBean;
import com.longya.live.model.JsonBean;
import com.longya.live.model.LiveBean;
import com.longya.live.model.MatchListBean;
import com.longya.live.model.UserBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface SearchComplexView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<UserBean> anchorList, List<LiveBean> liveList, List<MatchListBean> matchList, List<HeadlineBean> headlineList, List<CommunityBean> communityList);

    void doFollowSuccess(int id);

    void doReserveSuccess(int position);
}
