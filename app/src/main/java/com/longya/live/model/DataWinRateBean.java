package com.longya.live.model;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/3/3
 */
public class DataWinRateBean {

    /**
     * logo : https://cdn.sportnanoapi.com/basketball/team/8c88df221129169246c5b8a82955fa34.png
     * name : 爵士
     * won_rate : 72%
     */

    private String logo;
    private String name;
    private String won_rate;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWon_rate() {
        return won_rate;
    }

    public void setWon_rate(String won_rate) {
        this.won_rate = won_rate;
    }
}
