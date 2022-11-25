package com.longya.live.model;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/30
 */
public class FootballLineupSubstituteBean {
    private FootballLineupPlayerBean home_player;
    private FootballLineupPlayerBean away_player;

    public FootballLineupPlayerBean getHome_player() {
        return home_player;
    }

    public void setHome_player(FootballLineupPlayerBean home_player) {
        this.home_player = home_player;
    }

    public FootballLineupPlayerBean getAway_player() {
        return away_player;
    }

    public void setAway_player(FootballLineupPlayerBean away_player) {
        this.away_player = away_player;
    }
}
