package com.onecric.live.model;

public class ScorecardBowlerBean {

    private String id;
    private String name;
    private int overs_bowled;
    private int maidens;
    private int runs_conceded;
    private int wides;
    private float economy_rate;
    public int wickets;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOvers_bowled() {
        return overs_bowled;
    }

    public void setOvers_bowled(int overs_bowled) {
        this.overs_bowled = overs_bowled;
    }

    public int getMaidens() {
        return maidens;
    }

    public void setMaidens(int maidens) {
        this.maidens = maidens;
    }

    public int getRuns_conceded() {
        return runs_conceded;
    }

    public void setRuns_conceded(int runs_conceded) {
        this.runs_conceded = runs_conceded;
    }

    public int getWides() {
        return wides;
    }

    public void setWides(int wides) {
        this.wides = wides;
    }

    public float getEconomy_rate() {
        return economy_rate;
    }

    public void setEconomy_rate(float economy_rate) {
        this.economy_rate = economy_rate;
    }
}
