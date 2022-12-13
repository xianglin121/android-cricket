package com.onecric.live.model;

public class CricketPointsInnerBean {

    private int away_id;
    private String match_result;
    private String competitor;
    private String venue_name;
    private String date;
    private int winner;

    public int getAway_id() {
        return away_id;
    }

    public void setAway_id(int away_id) {
        this.away_id = away_id;
    }

    public String getMatch_result() {
        return match_result;
    }

    public void setMatch_result(String match_result) {
        this.match_result = match_result;
    }

    public String getCompetitor() {
        return competitor;
    }

    public void setCompetitor(String competitor) {
        this.competitor = competitor;
    }

    public String getVenue_name() {
        return venue_name;
    }

    public void setVenue_name(String venue_name) {
        this.venue_name = venue_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }
}
