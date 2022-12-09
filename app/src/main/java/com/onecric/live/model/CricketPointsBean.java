package com.onecric.live.model;

import java.util.List;

public class CricketPointsBean {

    private int rank;
    private int p;
    private int w;
    private int l;
    private int nr;
    private double nrr;
    private int pts;
    private int competitor_id;
    private int season_id;
    private String competitor;
    private List<CricketPointsInnerBean> match;

    //自定义属性
    private boolean expand;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getL() {
        return l;
    }

    public void setL(int l) {
        this.l = l;
    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public double getNrr() {
        return nrr;
    }

    public void setNrr(double nrr) {
        this.nrr = nrr;
    }

    public int getPts() {
        return pts;
    }

    public void setPts(int pts) {
        this.pts = pts;
    }

    public int getCompetitor_id() {
        return competitor_id;
    }

    public void setCompetitor_id(int competitor_id) {
        this.competitor_id = competitor_id;
    }

    public int getSeason_id() {
        return season_id;
    }

    public void setSeason_id(int season_id) {
        this.season_id = season_id;
    }

    public String getCompetitor() {
        return competitor;
    }

    public void setCompetitor(String competitor) {
        this.competitor = competitor;
    }

    public List<CricketPointsInnerBean> getMatch() {
        return match;
    }

    public void setMatch(List<CricketPointsInnerBean> match) {
        this.match = match;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }
}
