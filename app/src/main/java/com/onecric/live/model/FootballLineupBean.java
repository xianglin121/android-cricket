package com.onecric.live.model;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/30
 */
public class FootballLineupBean {

    /**
     * id : 2
     * sourceid : 3625185
     * confirmed : 1
     * home_formation : 4-1-4-1
     * away_formation : 3-1-4-2
     * addtime : 1635414950
     * home_team_logo : https://cdn.sportnanoapi.com/football/team/7500f685a80b5c3fa89986da91d1f987.png
     * away_team_logo : https://cdn.sportnanoapi.com/football/team/4533ab7ef9ad5e53c981f7ea7bea7033.png
     */

    private int id;
    private int sourceid;
    private int confirmed;
    private String home_formation;
    private String away_formation;
    private int addtime;
    private String home_team_logo;
    private String away_team_logo;
    private FootballLineupCoachBean home_manager;
    private FootballLineupCoachBean away_manager;
    private List<List<FootballLineupPlayerBean>> home_player_first;
    private List<List<FootballLineupPlayerBean>> away_player_first;
    private List<FootballLineupPlayerBean> home_player;
    private List<FootballLineupPlayerBean> away_player;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSourceid() {
        return sourceid;
    }

    public void setSourceid(int sourceid) {
        this.sourceid = sourceid;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public String getHome_formation() {
        return home_formation;
    }

    public void setHome_formation(String home_formation) {
        this.home_formation = home_formation;
    }

    public String getAway_formation() {
        return away_formation;
    }

    public void setAway_formation(String away_formation) {
        this.away_formation = away_formation;
    }

    public int getAddtime() {
        return addtime;
    }

    public void setAddtime(int addtime) {
        this.addtime = addtime;
    }

    public String getHome_team_logo() {
        return home_team_logo;
    }

    public void setHome_team_logo(String home_team_logo) {
        this.home_team_logo = home_team_logo;
    }

    public String getAway_team_logo() {
        return away_team_logo;
    }

    public void setAway_team_logo(String away_team_logo) {
        this.away_team_logo = away_team_logo;
    }

    public FootballLineupCoachBean getHome_manager() {
        return home_manager;
    }

    public void setHome_manager(FootballLineupCoachBean home_manager) {
        this.home_manager = home_manager;
    }

    public FootballLineupCoachBean getAway_manager() {
        return away_manager;
    }

    public void setAway_manager(FootballLineupCoachBean away_manager) {
        this.away_manager = away_manager;
    }

    public List<List<FootballLineupPlayerBean>> getHome_player_first() {
        return home_player_first;
    }

    public void setHome_player_first(List<List<FootballLineupPlayerBean>> home_player_first) {
        this.home_player_first = home_player_first;
    }

    public List<List<FootballLineupPlayerBean>> getAway_player_first() {
        return away_player_first;
    }

    public void setAway_player_first(List<List<FootballLineupPlayerBean>> away_player_first) {
        this.away_player_first = away_player_first;
    }

    public List<FootballLineupPlayerBean> getHome_player() {
        return home_player;
    }

    public void setHome_player(List<FootballLineupPlayerBean> home_player) {
        this.home_player = home_player;
    }

    public List<FootballLineupPlayerBean> getAway_player() {
        return away_player;
    }

    public void setAway_player(List<FootballLineupPlayerBean> away_player) {
        this.away_player = away_player;
    }
}
