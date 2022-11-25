package com.longya.live.model;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/28
 */
public class BasketballDetailBean {

    /**
     * id : 3622681
     * competition_id : 3
     * home_team_id : 10726
     * away_team_id : 10326
     * kind : 1
     * status_id : 5
     * match_time : 19:35
     * home_scores : [20,23,0,0,0]
     * away_scores : [19,18,0,0,0]
     * mlive : 1
     * short_name_zh : CBA
     * short_name_zht : CBA
     * short_name_en : China CBA
     * status_name : 第二节完
     * time_left : 12:00
     * kind_name : 常规赛
     */

    private int id;
    private int competition_id;
    private int home_team_id;
    private int away_team_id;
    private int kind;
    private int status_id;
    private int is_collect;
    private String match_time;
    private List<Integer> home_scores;
    private List<Integer> away_scores;
    private String home_scores_total;
    private String away_scores_total;
    private int mlive;
    private String short_name_zh;
    private String short_name_zht;
    private String short_name_en;
    private String status_name;
    private String time_left;
    private String kind_name;
    private BasketballTeamBean home_team_data;
    private BasketballTeamBean away_team_data;
    private List<List<BasketballEventBean>> tlive;
    private BasketballPlayerBean players;

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

    public int getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
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

    public List<List<BasketballEventBean>> getTlive() {
        return tlive;
    }

    public void setTlive(List<List<BasketballEventBean>> tlive) {
        this.tlive = tlive;
    }

    public BasketballPlayerBean getPlayers() {
        return players;
    }

    public void setPlayers(BasketballPlayerBean players) {
        this.players = players;
    }

    public String getHome_scores_total() {
        return home_scores_total;
    }

    public void setHome_scores_total(String home_scores_total) {
        this.home_scores_total = home_scores_total;
    }

    public String getAway_scores_total() {
        return away_scores_total;
    }

    public void setAway_scores_total(String away_scores_total) {
        this.away_scores_total = away_scores_total;
    }
}
