package com.onecric.live.model;

import java.util.List;

public class CricketFantasyBean {

    private double home_win_probabilities;
    private double away_win_probabilities;
    private int home_id;
    private int away_id;
    private String scheduled;
    private String home_name;
    private String away_name;
    private String home_logo;
    private String away_logo;
    private List<String> home;
    private List<String> away;
    private List<CricketMatchBean> home_list;
    private List<CricketMatchBean> away_list;
    private int home_win_num;
    private int away_win_num;
    private int no_result;
    private List<CricketMatchBean> historical_confrontation;

    public double getHome_win_probabilities() {
        return home_win_probabilities;
    }

    public void setHome_win_probabilities(double home_win_probabilities) {
        this.home_win_probabilities = home_win_probabilities;
    }

    public double getAway_win_probabilities() {
        return away_win_probabilities;
    }

    public void setAway_win_probabilities(double away_win_probabilities) {
        this.away_win_probabilities = away_win_probabilities;
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

    public String getScheduled() {
        return scheduled;
    }

    public void setScheduled(String scheduled) {
        this.scheduled = scheduled;
    }

    public List<String> getHome() {
        return home;
    }

    public void setHome(List<String> home) {
        this.home = home;
    }

    public List<String> getAway() {
        return away;
    }

    public void setAway(List<String> away) {
        this.away = away;
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

    public String getHome_logo() {
        return home_logo;
    }

    public void setHome_logo(String home_logo) {
        this.home_logo = home_logo;
    }

    public String getAway_logo() {
        return away_logo;
    }

    public void setAway_logo(String away_logo) {
        this.away_logo = away_logo;
    }

    public List<CricketMatchBean> getHome_list() {
        return home_list;
    }

    public void setHome_list(List<CricketMatchBean> home_list) {
        this.home_list = home_list;
    }

    public List<CricketMatchBean> getAway_list() {
        return away_list;
    }

    public void setAway_list(List<CricketMatchBean> away_list) {
        this.away_list = away_list;
    }

    public int getHome_win_num() {
        return home_win_num;
    }

    public void setHome_win_num(int home_win_num) {
        this.home_win_num = home_win_num;
    }

    public int getAway_win_num() {
        return away_win_num;
    }

    public void setAway_win_num(int away_win_num) {
        this.away_win_num = away_win_num;
    }

    public int getNo_result() {
        return no_result;
    }

    public void setNo_result(int no_result) {
        this.no_result = no_result;
    }

    public List<CricketMatchBean> getHistorical_confrontation() {
        return historical_confrontation;
    }

    public void setHistorical_confrontation(List<CricketMatchBean> historical_confrontation) {
        this.historical_confrontation = historical_confrontation;
    }
}
