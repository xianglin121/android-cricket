package com.longya.live.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/11/9
 */
public class FootballHistoryIndexDetailInnerBean implements MultiItemEntity {
    public static final int HEAD = 1;
    public static final int CONTENT = 0;
    /**
     * competition : 日职联
     * home_team : FC东京
     * away_team : 磐田喜悦
     * home_score : 1
     * away_score : 0
     * finale : 1.89
     * is_win : 赢
     * match_time : 2019/05/12
     */

    private String competition;
    private String home_team;
    private String away_team;
    private int home_score;
    private int away_score;
    private double finale;
    private String is_win;
    private String match_time;
    private int itemType;

    public String getCompetition() {
        return competition;
    }

    public void setCompetition(String competition) {
        this.competition = competition;
    }

    public String getHome_team() {
        return home_team;
    }

    public void setHome_team(String home_team) {
        this.home_team = home_team;
    }

    public String getAway_team() {
        return away_team;
    }

    public void setAway_team(String away_team) {
        this.away_team = away_team;
    }

    public int getHome_score() {
        return home_score;
    }

    public void setHome_score(int home_score) {
        this.home_score = home_score;
    }

    public int getAway_score() {
        return away_score;
    }

    public void setAway_score(int away_score) {
        this.away_score = away_score;
    }

    public double getFinale() {
        return finale;
    }

    public void setFinale(double finale) {
        this.finale = finale;
    }

    public String getIs_win() {
        return is_win;
    }

    public void setIs_win(String is_win) {
        this.is_win = is_win;
    }

    public String getMatch_time() {
        return match_time;
    }

    public void setMatch_time(String match_time) {
        this.match_time = match_time;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
