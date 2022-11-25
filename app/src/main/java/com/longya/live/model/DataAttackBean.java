package com.longya.live.model;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/3/3
 */
public class DataAttackBean {

    /**
     * logo : https://cdn.sportnanoapi.com/basketball/team/42d7b5ec22b2eb411d68f94a04eab742.png
     * name : 独行侠
     * defensive_rebounds : 122.33
     */

    private String logo;
    private String name;
    private String defensive_rebounds;

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

    public String getDefensive_rebounds() {
        return defensive_rebounds;
    }

    public void setDefensive_rebounds(String defensive_rebounds) {
        this.defensive_rebounds = defensive_rebounds;
    }
}
