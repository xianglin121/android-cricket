package com.longya.live.view.live;

import com.longya.live.model.JsonBean;
import com.longya.live.model.MovingBean;
import com.longya.live.model.ReserveLiveBean;
import com.longya.live.model.ReserveMatchBean;
import com.longya.live.view.BaseView;

import java.util.List;

public interface LiveAnchorView extends BaseView<List<MovingBean>> {
    void getReserveListSuccess(List<ReserveLiveBean> list);

    void doReserveSuccess(int position);
}
