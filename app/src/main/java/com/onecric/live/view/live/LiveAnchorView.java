package com.onecric.live.view.live;

import com.onecric.live.model.MovingBean;
import com.onecric.live.model.ReserveLiveBean;
import com.onecric.live.view.BaseView;

import java.util.List;

public interface LiveAnchorView extends BaseView<List<MovingBean>> {
    void getReserveListSuccess(List<ReserveLiveBean> list);

    void doReserveSuccess(int position);
}
