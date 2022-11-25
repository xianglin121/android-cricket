package com.longya.live.model;

public class RecentMatchesBean {

    private int player_id;
    private String bat;
    private String bowl;
    private String match_time;
    private String match_home_name;
    private String match_away_name;
    private String tournament_type;
    private String type;

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }

    public String getBat() {
        return bat;
    }

    public void setBat(String bat) {
        this.bat = bat;
    }

    public String getBowl() {
        return bowl;
    }

    public void setBowl(String bowl) {
        this.bowl = bowl;
    }

    public String getMatch_time() {
        return match_time;
    }

    public void setMatch_time(String match_time) {
        this.match_time = match_time;
    }

    public String getMatch_home_name() {
        return match_home_name;
    }

    public void setMatch_home_name(String match_home_name) {
        this.match_home_name = match_home_name;
    }

    public String getMatch_away_name() {
        return match_away_name;
    }

    public void setMatch_away_name(String match_away_name) {
        this.match_away_name = match_away_name;
    }

    public String getTournament_type() {
        return tournament_type;
    }

    public void setTournament_type(String tournament_type) {
        this.tournament_type = tournament_type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
