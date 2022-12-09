package com.onecric.live.model;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/28
 */
public class BasketballStatisticBean {

    /**
     * team_id : 3622681
     * type : 1
     * hits : 17
     * shots : 32
     * three_pointer_shot : 13
     * three_pointer : 6
     * free_throw : 3
     * free_throw_shots : 6
     * offensive_rebound : 0
     * defensive_rebound : 0
     * total_rebounds : 14
     * assists : 15
     * steals : 6
     * blocks : 1
     * number_of_mistakes : 8
     * individual_fouls : 11
     * score : 43
     * info : 0^17-32^6-13^3-6^0^0^14^15^6^1^8^11^0^43
     */

    private int team_id;
    private int type;
    private String hits;
    private String shots;
    private String three_pointer_shot;
    private String three_pointer;
    private String free_throw;
    private String free_throw_shots;
    private String offensive_rebound;
    private String defensive_rebound;
    private String total_rebounds;
    private String assists;
    private String steals;
    private String blocks;
    private String number_of_mistakes;
    private String individual_fouls;
    private String score;
    private String info;

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getHits() {
        return hits;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }

    public String getShots() {
        return shots;
    }

    public void setShots(String shots) {
        this.shots = shots;
    }

    public String getThree_pointer_shot() {
        return three_pointer_shot;
    }

    public void setThree_pointer_shot(String three_pointer_shot) {
        this.three_pointer_shot = three_pointer_shot;
    }

    public String getThree_pointer() {
        return three_pointer;
    }

    public void setThree_pointer(String three_pointer) {
        this.three_pointer = three_pointer;
    }

    public String getFree_throw() {
        return free_throw;
    }

    public void setFree_throw(String free_throw) {
        this.free_throw = free_throw;
    }

    public String getFree_throw_shots() {
        return free_throw_shots;
    }

    public void setFree_throw_shots(String free_throw_shots) {
        this.free_throw_shots = free_throw_shots;
    }

    public String getOffensive_rebound() {
        return offensive_rebound;
    }

    public void setOffensive_rebound(String offensive_rebound) {
        this.offensive_rebound = offensive_rebound;
    }

    public String getDefensive_rebound() {
        return defensive_rebound;
    }

    public void setDefensive_rebound(String defensive_rebound) {
        this.defensive_rebound = defensive_rebound;
    }

    public String getTotal_rebounds() {
        return total_rebounds;
    }

    public void setTotal_rebounds(String total_rebounds) {
        this.total_rebounds = total_rebounds;
    }

    public String getAssists() {
        return assists;
    }

    public void setAssists(String assists) {
        this.assists = assists;
    }

    public String getSteals() {
        return steals;
    }

    public void setSteals(String steals) {
        this.steals = steals;
    }

    public String getBlocks() {
        return blocks;
    }

    public void setBlocks(String blocks) {
        this.blocks = blocks;
    }

    public String getNumber_of_mistakes() {
        return number_of_mistakes;
    }

    public void setNumber_of_mistakes(String number_of_mistakes) {
        this.number_of_mistakes = number_of_mistakes;
    }

    public String getIndividual_fouls() {
        return individual_fouls;
    }

    public void setIndividual_fouls(String individual_fouls) {
        this.individual_fouls = individual_fouls;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
