package com.longya.live.model;

public class CricketPlayerBean {

    private int id;
    private int competitor_id;
    private int tournament_id;
    private int player_id;
    private String type;
    private String batting_style;
    private String bowling_style;
    private String players_name;
    private String players_logo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompetitor_id() {
        return competitor_id;
    }

    public void setCompetitor_id(int competitor_id) {
        this.competitor_id = competitor_id;
    }

    public int getTournament_id() {
        return tournament_id;
    }

    public void setTournament_id(int tournament_id) {
        this.tournament_id = tournament_id;
    }

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBatting_style() {
        return batting_style;
    }

    public void setBatting_style(String batting_style) {
        this.batting_style = batting_style;
    }

    public String getBowling_style() {
        return bowling_style;
    }

    public void setBowling_style(String bowling_style) {
        this.bowling_style = bowling_style;
    }

    public String getPlayers_name() {
        return players_name;
    }

    public void setPlayers_name(String players_name) {
        this.players_name = players_name;
    }

    public String getPlayers_logo() {
        return players_logo;
    }

    public void setPlayers_logo(String players_logo) {
        this.players_logo = players_logo;
    }
}
