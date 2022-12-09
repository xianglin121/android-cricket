package com.onecric.live.model;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/26
 */
public class BasketballMatchBean {

    /**
     * id : 3610304
     * competition_id : 90
     * home_team_id : 16972
     * away_team_id : 12247
     * kind : 1
     * status_id : 1
     * match_time : 16:00
     * home_scores : [0,0,0,0,0]
     * away_scores : [0,0,0,0,0]
     * mlive : 1
     * short_name_zh : 日篮B2
     * short_name_zht : 日籃B2
     * short_name_en : Japan B2 League
     * status_name : 未开赛
     * time_left :
     * kind_name : 常规赛
     */

    private int id;
    private int competition_id;
    private int home_team_id;
    private int away_team_id;
    private int kind;
    private int status_id;
    private String match_time;
    private List<Integer> home_scores;
    private List<Integer> away_scores;
    private int mlive;
    private int is_collect;
    private String short_name_zh;
    private String short_name_zht;
    private String short_name_en;
    private String status_name;
    private String time_left;
    private String kind_name;
    private BasketballTeamBean home_team_data;
    private BasketballTeamBean away_team_data;
    private int home_scores_total;
    private int away_scores_total;
    private LiveAnchorBean anchor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompetition_id() {
        return competition_id;
    }

    public void setCompetition_id(int competition_id) {
        this.competition_id = competition_id;
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

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public String getMatch_time() {
        return match_time;
    }

    public void setMatch_time(String match_time) {
        this.match_time = match_time;
    }

    public List<Integer> getHome_scores() {
        return home_scores;
    }

    public void setHome_scores(List<Integer> home_scores) {
        this.home_scores = home_scores;
    }

    public List<Integer> getAway_scores() {
        return away_scores;
    }

    public void setAway_scores(List<Integer> away_scores) {
        this.away_scores = away_scores;
    }

    public int getMlive() {
        return mlive;
    }

    public void setMlive(int mlive) {
        this.mlive = mlive;
    }

    public int getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
    }

    public String getShort_name_zh() {
        return short_name_zh;
    }

    public void setShort_name_zh(String short_name_zh) {
        this.short_name_zh = short_name_zh;
    }

    public String getShort_name_zht() {
        return short_name_zht;
    }

    public void setShort_name_zht(String short_name_zht) {
        this.short_name_zht = short_name_zht;
    }

    public String getShort_name_en() {
        return short_name_en;
    }

    public void setShort_name_en(String short_name_en) {
        this.short_name_en = short_name_en;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public String getTime_left() {
        return time_left;
    }

    public void setTime_left(String time_left) {
        this.time_left = time_left;
    }

    public String getKind_name() {
        return kind_name;
    }

    public void setKind_name(String kind_name) {
        this.kind_name = kind_name;
    }

    public BasketballTeamBean getHome_team_data() {
        return home_team_data;
    }

    public void setHome_team_data(BasketballTeamBean home_team_data) {
        this.home_team_data = home_team_data;
    }

    public BasketballTeamBean getAway_team_data() {
        return away_team_data;
    }

    public void setAway_team_data(BasketballTeamBean away_team_data) {
        this.away_team_data = away_team_data;
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

    public LiveAnchorBean getAnchor() {
        return anchor;
    }

    public void setAnchor(LiveAnchorBean anchor) {
        this.anchor = anchor;
    }
}
