package com.onecric.live.model;

public class BattingBean {

    /**
      {
              "average":"45.35",
              "balls":26562,
              "fastest100balls":0,
              "match_id":0,
              "strike":"46.95",
              "catches":175,
              "inning_id":0,
              "matches":161,
              "run100":33,
              "stumpings":0,
              "highest":294,
              "innings":291,
              "fastest50balls":0,
              "run50":57,
              "notout":16,
              "run4":1442,
              "runs":12472,
              "run6":11
          }
     */
    private int matches;
    private int innings;
    private int balls_faced;
    private int not_outs;
    private int runs;
    private double average;
    private double strike_rate;
    private int hundreds;
    private int fifties;
    private int fours;
    private int sixes;

    public int balls;
    public double strike;
    public int notout;
    public int run100;
    public int run50;
    public int run4;
    public int run6;

    public int getMatches() {
        return matches;
    }

    public void setMatches(int matches) {
        this.matches = matches;
    }

    public int getInnings() {
        return innings;
    }

    public void setInnings(int innings) {
        this.innings = innings;
    }

    public int getBalls_faced() {
        return balls_faced;
    }

    public void setBalls_faced(int balls_faced) {
        this.balls_faced = balls_faced;
    }

    public int getNot_outs() {
        return not_outs;
    }

    public void setNot_outs(int not_outs) {
        this.not_outs = not_outs;
    }

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public double getStrike_rate() {
        return strike_rate;
    }

    public void setStrike_rate(double strike_rate) {
        this.strike_rate = strike_rate;
    }

    public int getHundreds() {
        return hundreds;
    }

    public void setHundreds(int hundreds) {
        this.hundreds = hundreds;
    }

    public int getFifties() {
        return fifties;
    }

    public void setFifties(int fifties) {
        this.fifties = fifties;
    }

    public int getFours() {
        return fours;
    }

    public void setFours(int fours) {
        this.fours = fours;
    }

    public int getSixes() {
        return sixes;
    }

    public void setSixes(int sixes) {
        this.sixes = sixes;
    }
}
