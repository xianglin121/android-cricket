package com.onecric.live.model;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/3/3
 */
public class FootballDataStatusBean {
    private String total;
    private String totalScore;
    private String totalPointsPerGame;
    private String homeTeamVictory;
    private String awayTeamWins;
    private String draw;
    private List<Integer> cornerLicksStatistics;
    private List<Integer> numberOfPointsDifference;
    private List<DataWinRateBean> winRate;
    private List<DataAttackBean> attack;
    private List<DataDefenseBean> defense;

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

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public List<Integer> getCornerLicksStatistics() {
        return cornerLicksStatistics;
    }

    public void setCornerLicksStatistics(List<Integer> cornerLicksStatistics) {
        this.cornerLicksStatistics = cornerLicksStatistics;
    }
}
