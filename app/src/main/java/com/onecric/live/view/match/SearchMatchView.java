package com.onecric.live.view.match;

import com.onecric.live.model.Channel;
import com.onecric.live.model.MatchListBean;
import com.onecric.live.model.MatchSearchBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface SearchMatchView extends BaseView<MatchSearchBean> {
    void getMoreData(int type,List<MatchSearchBean> list);
    void getDataSuccess(List<MatchSearchBean> bean,int type);
    void getDataFail(String msg,int type);
}
