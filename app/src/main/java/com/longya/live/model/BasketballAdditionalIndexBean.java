package com.longya.live.model;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/11/2
 */
public class BasketballAdditionalIndexBean {
    private BasketballIndexTwoBean highest;
    private BasketballIndexTwoBean lowest;
    private BasketballIndexTwoBean average;

    public BasketballIndexTwoBean getHighest() {
        return highest;
    }

    public void setHighest(BasketballIndexTwoBean highest) {
        this.highest = highest;
    }

    public BasketballIndexTwoBean getLowest() {
        return lowest;
    }

    public void setLowest(BasketballIndexTwoBean lowest) {
        this.lowest = lowest;
    }

    public BasketballIndexTwoBean getAverage() {
        return average;
    }

    public void setAverage(BasketballIndexTwoBean average) {
        this.average = average;
    }
}
