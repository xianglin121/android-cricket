package com.onecric.live.model;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/2/25
 */
public class MatchDataFollowBean {

    /**
     * id : 2
     * name : WNBA
     * logo : https://cdn.sportnanoapi.com/basketball/competition/86f522333da4c3e2c144996fc4d2520b.png
     * type : 1
     */

    private int id;
    private String name;
    private String logo;
    private int type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
