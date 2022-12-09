package com.onecric.live.view.live;

import com.onecric.live.model.CommunityBean;
import com.onecric.live.model.HeadlineBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.model.LiveBean;
import com.onecric.live.model.MatchListBean;
import com.onecric.live.model.UserBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface SearchComplexView extends BaseView<JsonBean> {
    void getDataSuccess(boolean isRefresh, List<UserBean> anchorList, List<LiveBean> liveList, List<MatchListBean> matchList, List<HeadlineBean> headlineList, List<CommunityBean> communityList);

    void doFollowSuccess(int id);

    void doReserveSuccess(int position);
}
