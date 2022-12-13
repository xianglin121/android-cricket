package com.onecric.live.model;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/29
 */
public class FootballTypeBean {
    private int num;
    private List<FootballTypeInnerBean> event;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<FootballTypeInnerBean> getEvent() {
        return event;
    }

    public void setEvent(List<FootballTypeInnerBean> event) {
        this.event = event;
    }
}
