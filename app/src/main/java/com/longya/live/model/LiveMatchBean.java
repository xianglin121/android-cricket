package com.longya.live.model;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/12/4
 */
public class LiveMatchBean {

    /**
     * sourceid : 3659315
     * competition : 俄室内足
     * match_time : 11-30 00:15
     * home_team_name : 乌赫塔室内足球队
     * home_team_logo :
     * away_team_name : 即锡纳拉室内足球队
     * away_team_logo :
     * home_scores : 0
     * away_scores : 0
     * status_id : 1
     * mlive : 0
     * type : 0
     */

    private int sourceid;
    private String competition;
    private String match_time;
    private String home_team_name;
    private String home_team_logo;
    private String away_team_name;
    private String away_team_logo;
    private int home_scores;
    private int away_scores;
    private int status_id;
    private int mlive;
    private int type;
    private int reserve;
    private int status_type;

    public int getSourceid() {
        return sourceid;
    }

    public void setSourceid(int sourceid) {
        this.sourceid = sourceid;
    }

    public String getCompetition() {
        return competition;
    }

    public void setCompetition(String competition) {
        this.competition = competition;
    }

    public String getMatch_time() {
        return match_time;
    }

    public void setMatch_time(String match_time) {
        this.match_time = match_time;
    }

    public String getHome_team_name() {
        return home_team_name;
    }

    public void setHome_team_name(String home_team_name) {
        this.home_team_name = home_team_name;
    }

    public String getHome_team_logo() {
        return home_team_logo;
    }

    public void setHome_team_logo(String home_team_logo) {
        this.home_team_logo = home_team_logo;
    }

    public String getAway_team_name() {
        return away_team_name;
    }

    public void setAway_team_name(String away_team_name) {
        this.away_team_name = away_team_name;
    }

    public String getAway_team_logo() {
        return away_team_logo;
    }

    public void setAway_team_logo(String away_team_logo) {
        this.away_team_logo = away_team_logo;
    }

    public int getHome_scores() {
        return home_scores;
    }

    public void setHome_scores(int home_scores) {
        this.home_scores = home_scores;
    }

    public int getAway_scores() {
        return away_scores;
    }

    public void setAway_scores(int away_scores) {
        this.away_scores = away_scores;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public int getMlive() {
        return mlive;
    }

    public void setMlive(int mlive) {
        this.mlive = mlive;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getReserve() {
        return reserve;
    }

    public void setReserve(int reserve) {
        this.reserve = reserve;
    }

    public int getStatus_type() {
        return status_type;
    }

    public void setStatus_type(int status_type) {
        this.status_type = status_type;
    }
}
