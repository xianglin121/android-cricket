package com.onecric.live.model;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/28
 */
public class BasketballEventBean {

    /**
     * team_id : 3622681
     * conducttime : 12:00
     * goal_type : 1
     * chapter : 1
     * home_score : 0
     * away_score : 0
     * info : 北京首钢,刘晓宇、田宇翔、翟晓川、朱彦西、李慕豪,首发出场
     * raw_data : 1^12:00^1^0^0-0^北京首钢 刘晓宇、田宇翔、翟晓川、朱彦西、李慕豪 首发出场^0^0^0^0^0,0
     */

    private int team_id;
    private String conducttime;
    private String goal_type;
    private int chapter;
    private String home_score;
    private String away_score;
    private String info;
    private String raw_data;
    private boolean isHide;

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public String getConducttime() {
        return conducttime;
    }

    public void setConducttime(String conducttime) {
        this.conducttime = conducttime;
    }

    public String getGoal_type() {
        return goal_type;
    }

    public void setGoal_type(String goal_type) {
        this.goal_type = goal_type;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public String getHome_score() {
        return home_score;
    }

    public void setHome_score(String home_score) {
        this.home_score = home_score;
    }

    public String getAway_score() {
        return away_score;
    }

    public void setAway_score(String away_score) {
        this.away_score = away_score;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getRaw_data() {
        return raw_data;
    }

    public void setRaw_data(String raw_data) {
        this.raw_data = raw_data;
    }

    public boolean isHide() {
        return isHide;
    }

    public void setHide(boolean hide) {
        isHide = hide;
    }
}
