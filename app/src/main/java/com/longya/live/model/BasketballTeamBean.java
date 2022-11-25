package com.longya.live.model;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/26
 */
public class BasketballTeamBean {

    /**
     * sourceid : 16972
     * id : 16972
     * short_name_zh : 奈良雄心
     * short_name_zht : 奈良雄心
     * short_name_en : Bambitious Nara
     * logo : https://cdn.sportnanoapi.com/basketball/team/d071a6b9e144b301579cd9536a53d431.png
     */

    private int sourceid;
    private int id;
    private String short_name_zh;
    private String short_name_zht;
    private String short_name_en;
    private String logo;

    public int getSourceid() {
        return sourceid;
    }

    public void setSourceid(int sourceid) {
        this.sourceid = sourceid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
