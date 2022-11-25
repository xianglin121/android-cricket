package com.longya.live.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/11/9
 */
public class HistoryIndexDetailInnerBean implements MultiItemEntity {
    public static final int HEAD = 1;
    public static final int CONTENT = 0;
    /**
     * home_name : 鲁西尼克
     * away_name : 斯比斯
     * competition_name : 斯伐甲
     * match_time : 2020/03/19
     * final_set : 0
     * is_win : 输
     * home_score : 72
     * away_score : 76
     */

    private String home_name;
    private String away_name;
    private String competition_name;
    private String match_time;
    private int final_set;
    private String is_win;
    private int home_score;
    private int away_score;
    private int itemType;

    public String getHome_name() {
        return home_name;
    }

    public void setHome_name(String home_name) {
        this.home_name = home_name;
    }

    public String getAway_name() {
        return away_name;
    }

    public void setAway_name(String away_name) {
        this.away_name = away_name;
    }

    public String getCompetition_name() {
        return competition_name;
    }

    public void setCompetition_name(String competition_name) {
        this.competition_name = competition_name;
    }

    public String getMatch_time() {
        return match_time;
    }

    public void setMatch_time(String match_time) {
        this.match_time = match_time;
    }

    public int getFinal_set() {
        return final_set;
    }

    public void setFinal_set(int final_set) {
        this.final_set = final_set;
    }

    public String getIs_win() {
        return is_win;
    }

    public void setIs_win(String is_win) {
        this.is_win = is_win;
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

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
