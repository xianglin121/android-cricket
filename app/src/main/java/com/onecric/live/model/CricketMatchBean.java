package com.onecric.live.model;

import java.io.Serializable;

public class CricketMatchBean implements Serializable {

    private int id;
    private int match_id;
    private String scheduled;
    private String match_result;
    private int home_id;
    private int away_id;
    private String tournament_id;
    private String match_result_type;
    private String live_time;
    private String home_display_score;
    private String away_display_score;
    private String home_display_overs;
    private String away_display_overs;
    private String home_name;
    private String away_name;
    private String away_logo;
    private String home_logo;
    private String match_num;
    private int status;
    private String venue_name;
    private String live_path;
    private String live_url;
    private int winner_id;
    private String venue;
    private String date;
    private String opponent_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMatch_id() {
        return match_id;
    }

    public void setMatch_id(int match_id) {
        this.match_id = match_id;
    }

    public String getScheduled() {
        return scheduled;
    }

    public void setScheduled(String scheduled) {
        this.scheduled = scheduled;
    }

    public String getMatch_result() {
        return match_result;
    }

    public void setMatch_result(String match_result) {
        this.match_result = match_result;
    }

    public int getHome_id() {
        return home_id;
    }

    public void setHome_id(int home_id) {
        this.home_id = home_id;
    }

    public int getAway_id() {
        return away_id;
    }

    public void setAway_id(int away_id) {
        this.away_id = away_id;
    }

    public String getTournament_id() {
        return tournament_id;
    }

    public void setTournament_id(String tournament_id) {
        this.tournament_id = tournament_id;
    }

    public String getMatch_result_type() {
        return match_result_type;
    }

    public void setMatch_result_type(String match_result_type) {
        this.match_result_type = match_result_type;
    }

    public String getLive_time() {
        return live_time;
    }

    public void setLive_time(String live_time) {
        this.live_time = live_time;
    }

    public String getHome_display_score() {
        return home_display_score;
    }

    public void setHome_display_score(String home_display_score) {
        this.home_display_score = home_display_score;
    }

    public String getAway_display_score() {
        return away_display_score;
    }

    public void setAway_display_score(String away_display_score) {
        this.away_display_score = away_display_score;
    }

    public String getHome_display_overs() {
        return home_display_overs;
    }

    public void setHome_display_overs(String home_display_overs) {
        this.home_display_overs = home_display_overs;
    }

    public String getAway_display_overs() {
        return away_display_overs;
    }

    public void setAway_display_overs(String away_display_overs) {
        this.away_display_overs = away_display_overs;
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

    public String getAway_logo() {
        return away_logo;
    }

    public void setAway_logo(String away_logo) {
        this.away_logo = away_logo;
    }

    public String getHome_logo() {
        return home_logo;
    }

    public void setHome_logo(String home_logo) {
        this.home_logo = home_logo;
    }

    public String getMatch_num() {
        return match_num;
    }

    public void setMatch_num(String match_num) {
        this.match_num = match_num;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getVenue_name() {
        return venue_name;
    }

    public void setVenue_name(String venue_name) {
        this.venue_name = venue_name;
    }

    public String getLive_path() {
        return live_path;
    }

    public void setLive_path(String live_path) {
        this.live_path = live_path;
    }

    public String getLive_url() {
        return live_url;
    }

    public void setLive_url(String live_url) {
        this.live_url = live_url;
    }

    public int getWinner_id() {
        return winner_id;
    }

    public void setWinner_id(int winner_id) {
        this.winner_id = winner_id;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOpponent_name() {
        return opponent_name;
    }

    public void setOpponent_name(String opponent_name) {
        this.opponent_name = opponent_name;
    }
}
