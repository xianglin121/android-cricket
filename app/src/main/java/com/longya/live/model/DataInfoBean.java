package com.longya.live.model;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/3/3
 */
public class DataInfoBean {

    /**
     * logo : https://cdn.sportnanoapi.com/basketball/competition/6f951003a8744611c9c59f65acb74ea6.jfif
     * name : NBA
     * name_en : National Basketball Association
     */

    private String logo;
    private String name;
    private String name_en;

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

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }
}
