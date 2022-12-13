package com.onecric.live.model;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/3/3
 */
public class DataStatusBean {
    private String blocks;
    private String rebounds;
    private String assists;
    private String steals;
    private String totalPointsPerGame;
    private String total;
    private String homeTeamVictory;
    private String awayTeamWins;
    private String draw;
    private List<Double> singleQuarterScore;
    private List<Integer> numberOfPointsDifference;
    private List<DataWinRateBean> winRate;
    private List<DataAttackBean> attack;
    private List<DataDefenseBean> defense;

    public String getBlocks() {
        return blocks;
    }

    public void setBlocks(String blocks) {
        this.blocks = blocks;
    }

    public String getRebounds() {
        return rebounds;
    }

    public void setRebounds(String rebounds) {
        this.rebounds = rebounds;
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

    public String getTotalPointsPerGame() {
        return totalPointsPerGame;
    }

    public void setTotalPointsPerGame(String totalPointsPerGame) {
        this.totalPointsPerGame = totalPointsPerGame;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getHomeTeamVictory() {
        return homeTeamVictory;
    }

    public void setHomeTeamVictory(String homeTeamVictory) {
        this.homeTeamVictory = homeTeamVictory;
    }

    public String getAwayTeamWins() {
        return awayTeamWins;
    }

    public void setAwayTeamWins(String awayTeamWins) {
        this.awayTeamWins = awayTeamWins;
    }

    public String getDraw() {
        return draw;
    }

    public void setDraw(String draw) {
        this.draw = draw;
    }

    public List<Double> getSingleQuarterScore() {
        return singleQuarterScore;
    }

    public void setSingleQuarterScore(List<Double> singleQuarterScore) {
        this.singleQuarterScore = singleQuarterScore;
    }

    public List<Integer> getNumberOfPointsDifference() {
        return numberOfPointsDifference;
    }

    public void setNumberOfPointsDifference(List<Integer> numberOfPointsDifference) {
        this.numberOfPointsDifference = numberOfPointsDifference;
    }

    public List<DataWinRateBean> getWinRate() {
        return winRate;
    }

    public void setWinRate(List<DataWinRateBean> winRate) {
        this.winRate = winRate;
    }

    public List<DataAttackBean> getAttack() {
        return attack;
    }

    public void setAttack(List<DataAttackBean> attack) {
        this.attack = attack;
    }

    public List<DataDefenseBean> getDefense() {
        return defense;
    }

    public void setDefense(List<DataDefenseBean> defense) {
        this.defense = defense;
    }
}
