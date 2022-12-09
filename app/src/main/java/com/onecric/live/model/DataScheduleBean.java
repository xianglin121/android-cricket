package com.onecric.live.model;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/3/3
 */
public class DataScheduleBean {

    /**
     * id : 3609536
     * home_team_id : 10149
     * away_team_id : 10134
     * match_time : 1633289400
     * kind : 3
     * home_name : 湖人
     * away_name : 篮网
     * home_scores_total : 97
     * away_scores_total : 123
     * home_scores_halftime : 49
     * away_scores_halftime : 57
     * is_win : 2
     * day : 10-04
     * hour : 03:30
     * is_normal : 0
     */

    private int id;
    private int home_team_id;
    private int away_team_id;
    private int match_time;
    private int kind;
    private String home_name;
    private String away_name;
    private int home_scores_total;
    private int away_scores_total;
    private int home_scores_halftime;
    private int away_scores_halftime;
    private int is_win;
    private String day;
    private String hour;
    private int is_normal;
    private int home_corner_licks;
    private int away_corner_licks;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHome_team_id() {
        return home_team_id;
    }

    public void setHome_team_id(int home_team_id) {
        this.home_team_id = home_team_id;
    }

    public int getAway_team_id() {
        return away_team_id;
    }

    public void setAway_team_id(int away_team_id) {
        this.away_team_id = away_team_id;
    }

    public int getMatch_time() {
        return match_time;
    }

    public void setMatch_time(int match_time) {
        this.match_time = match_time;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

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

    public int getHome_scores_total() {
        return home_scores_total;
    }

    public void setHome_scores_total(int home_scores_total) {
        this.home_scores_total = home_scores_total;
    }

    public int getAway_scores_total() {
        return away_scores_total;
    }

    public void setAway_scores_total(int away_scores_total) {
        this.away_scores_total = away_scores_total;
    }

    public int getHome_scores_halftime() {
        return home_scores_halftime;
    }

    public void setHome_scores_halftime(int home_scores_halftime) {
        this.home_scores_halftime = home_scores_halftime;
    }

    public int getAway_scores_halftime() {
        return away_scores_halftime;
    }

    public void setAway_scores_halftime(int away_scores_halftime) {
        this.away_scores_halftime = away_scores_halftime;
    }

    public int getIs_win() {
        return is_win;
    }

    public void setIs_win(int is_win) {
        this.is_win = is_win;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public int getIs_normal() {
        return is_normal;
    }

    public void setIs_normal(int is_normal) {
        this.is_normal = is_normal;
    }

    public int getHome_corner_licks() {
        return home_corner_licks;
    }

    public void setHome_corner_licks(int home_corner_licks) {
        this.home_corner_licks = home_corner_licks;
    }

    public int getAway_corner_licks() {
        return away_corner_licks;
    }

    public void setAway_corner_licks(int away_corner_licks) {
        this.away_corner_licks = away_corner_licks;
    }
}
