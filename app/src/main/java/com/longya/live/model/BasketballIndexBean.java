package com.longya.live.model;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/11/2
 */
public class BasketballIndexBean {
    private List<BasketballIndexOneBean> asia;
    private List<BasketballIndexOneBean> eu;
    private List<BasketballIndexOneBean> bs;
    private BasketballAdditionalIndexBean asia_additional;
    private BasketballAdditionalIndexBean eu_additional;
    private BasketballAdditionalIndexBean bs_additional;
    private List<HistoryIndexBean> compensation;
    private List<FootballHistoryIndexBean> compensations;

    public List<BasketballIndexOneBean> getAsia() {
        return asia;
    }

    public void setAsia(List<BasketballIndexOneBean> asia) {
        this.asia = asia;
    }

    public List<BasketballIndexOneBean> getEu() {
        return eu;
    }

    public void setEu(List<BasketballIndexOneBean> eu) {
        this.eu = eu;
    }

    public List<BasketballIndexOneBean> getBs() {
        return bs;
    }

    public void setBs(List<BasketballIndexOneBean> bs) {
        this.bs = bs;
    }

    public BasketballAdditionalIndexBean getAsia_additional() {
        return asia_additional;
    }

    public void setAsia_additional(BasketballAdditionalIndexBean asia_additional) {
        this.asia_additional = asia_additional;
    }

    public BasketballAdditionalIndexBean getEu_additional() {
        return eu_additional;
    }

    public void setEu_additional(BasketballAdditionalIndexBean eu_additional) {
        this.eu_additional = eu_additional;
    }

    public BasketballAdditionalIndexBean getBs_additional() {
        return bs_additional;
    }

    public void setBs_additional(BasketballAdditionalIndexBean bs_additional) {
        this.bs_additional = bs_additional;
    }

    public List<HistoryIndexBean> getCompensation() {
        return compensation;
    }

    public void setCompensation(List<HistoryIndexBean> compensation) {
        this.compensation = compensation;
    }

    public List<FootballHistoryIndexBean> getCompensations() {
        return compensations;
    }

    public void setCompensations(List<FootballHistoryIndexBean> compensations) {
        this.compensations = compensations;
    }
}
