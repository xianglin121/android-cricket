package com.longya.live.model;

import java.io.Serializable;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/12/22
 */
public class MatchSocketBean implements Serializable {

    /**
     * id : 3664879
     * status : 8
     * home_score : 8
     * away_score : 2
     * home_yellow_card : 1
     * away_yellow_card : 0
     * home_red_card : 0
     * away_red_card : 0
     * half_score : 3-1
     * corner_kick : 8-1
     * overtime : 0-0
     * penalty_kick : 0-0
     * match_str :
     * type : football_match
     */

    private int id;
    private int match_id;
    private int status;
    private int home_score;
    private int away_score;
    private int home_yellow_card;
    private int away_yellow_card;
    private int home_red_card;
    private int away_red_card;
    private String half_score;
    private String corner_kick;
    private String overtime;
    private String penalty_kick;
    private String match_str;
    private String home_team_name;
    private String away_team_name;
    //篮球
    private String time_left;
    private int status_id;
    private List<Integer> home_scores;
    private List<Integer> away_scores;
    private int home_scores_num;
    private int away_scores_num;
    private int total_score;
    private int home_top_half;
    private int home_lower_half;
    private int away_top_half;
    private int away_lower_half;
    private int total_half_time;
    private int audience_score_gap;
    private int halftime_score_gap;

    private String type;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getHome_score() {
        return home_score;
    }

    public void setHome_score(int home_score) {
        this.home_score = home_score;
    }

    public int getAway_score() {
        return away_score;
    }

    public void setAway_score(int away_score) {
        this.away_score = away_score;
    }

    public int getHome_yellow_card() {
        return home_yellow_card;
    }

    public void setHome_yellow_card(int home_yellow_card) {
        this.home_yellow_card = home_yellow_card;
    }

    public int getAway_yellow_card() {
        return away_yellow_card;
    }

    public void setAway_yellow_card(int away_yellow_card) {
        this.away_yellow_card = away_yellow_card;
    }

    public int getHome_red_card() {
        return home_red_card;
    }

    public void setHome_red_card(int home_red_card) {
        this.home_red_card = home_red_card;
    }

    public int getAway_red_card() {
        return away_red_card;
    }

    public void setAway_red_card(int away_red_card) {
        this.away_red_card = away_red_card;
    }

    public String getHalf_score() {
        return half_score;
    }

    public void setHalf_score(String half_score) {
        this.half_score = half_score;
    }

    public String getCorner_kick() {
        return corner_kick;
    }

    public void setCorner_kick(String corner_kick) {
        this.corner_kick = corner_kick;
    }

    public String getOvertime() {
        return overtime;
    }

    public void setOvertime(String overtime) {
        this.overtime = overtime;
    }

    public String getPenalty_kick() {
        return penalty_kick;
    }

    public void setPenalty_kick(String penalty_kick) {
        this.penalty_kick = penalty_kick;
    }

    public String getMatch_str() {
        return match_str;
    }

    public void setMatch_str(String match_str) {
        this.match_str = match_str;
    }

    public String getTime_left() {
        return time_left;
    }

    public void setTime_left(String time_left) {
        this.time_left = time_left;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
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

    public int getHome_scores_num() {
        return home_scores_num;
    }

    public void setHome_scores_num(int home_scores_num) {
        this.home_scores_num = home_scores_num;
    }

    public int getAway_scores_num() {
        return away_scores_num;
    }

    public void setAway_scores_num(int away_scores_num) {
        this.away_scores_num = away_scores_num;
    }

    public int getTotal_score() {
        return total_score;
    }

    public void setTotal_score(int total_score) {
        this.total_score = total_score;
    }

    public int getHome_top_half() {
        return home_top_half;
    }

    public void setHome_top_half(int home_top_half) {
        this.home_top_half = home_top_half;
    }

    public int getHome_lower_half() {
        return home_lower_half;
    }

    public void setHome_lower_half(int home_lower_half) {
        this.home_lower_half = home_lower_half;
    }

    public int getAway_top_half() {
        return away_top_half;
    }

    public void setAway_top_half(int away_top_half) {
        this.away_top_half = away_top_half;
    }

    public int getAway_lower_half() {
        return away_lower_half;
    }

    public void setAway_lower_half(int away_lower_half) {
        this.away_lower_half = away_lower_half;
    }

    public int getTotal_half_time() {
        return total_half_time;
    }

    public void setTotal_half_time(int total_half_time) {
        this.total_half_time = total_half_time;
    }

    public int getAudience_score_gap() {
        return audience_score_gap;
    }

    public void setAudience_score_gap(int audience_score_gap) {
        this.audience_score_gap = audience_score_gap;
    }

    public int getHalftime_score_gap() {
        return halftime_score_gap;
    }

    public void setHalftime_score_gap(int halftime_score_gap) {
        this.halftime_score_gap = halftime_score_gap;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHome_team_name() {
        return home_team_name;
    }

    public void setHome_team_name(String home_team_name) {
        this.home_team_name = home_team_name;
    }

    public String getAway_team_name() {
        return away_team_name;
    }

    public void setAway_team_name(String away_team_name) {
        this.away_team_name = away_team_name;
    }
}
