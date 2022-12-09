package com.onecric.live.model;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/2/25
 */
public class MatchDataSecondBean {

    /**
     * id : 1
     * name_zh : 美国男子职业篮球联赛
     * name_zht : 美國男子職業籃球聯賽
     * name_en : National Basketball Association
     * short_name_zh : NBA
     * short_name_zht : NBA
     * short_name_en : NBA
     * logo : https://cdn.sportnanoapi.com/basketball/competition/6f951003a8744611c9c59f65acb74ea6.jfif
     * name : NBA
     */

    private int id;
    private String name_zh;
    private String name_zht;
    private String name_en;
    private String short_name_zh;
    private String short_name_zht;
    private String short_name_en;
    private String logo;
    private String name;
    private boolean isFollow;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_zh() {
        return name_zh;
    }

    public void setName_zh(String name_zh) {
        this.name_zh = name_zh;
    }

    public String getName_zht() {
        return name_zht;
    }

    public void setName_zht(String name_zht) {
        this.name_zht = name_zht;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getShort_name_zh() {
        return short_name_zh;
    }

    public void setShort_name_zh(String short_name_zh) {
        this.short_name_zh = short_name_zh;
    }

    public String getShort_name_zht() {
        return short_name_zht;
    }

    public void setShort_name_zht(String short_name_zht) {
        this.short_name_zht = short_name_zht;
    }

    public String getShort_name_en() {
        return short_name_en;
    }

    public void setShort_name_en(String short_name_en) {
        this.short_name_en = short_name_en;
    }

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

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }
}
