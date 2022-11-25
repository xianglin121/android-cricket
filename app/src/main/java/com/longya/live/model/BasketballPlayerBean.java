package com.longya.live.model;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/28
 */
public class BasketballPlayerBean {
    private BasketballStatisticBean home;
    private BasketballStatisticBean away;

    public BasketballStatisticBean getHome() {
        return home;
    }

    public void setHome(BasketballStatisticBean home) {
        this.home = home;
    }

    public BasketballStatisticBean getAway() {
        return away;
    }

    public void setAway(BasketballStatisticBean away) {
        this.away = away;
    }
}
