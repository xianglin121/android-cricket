package com.onecric.live.model;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/2/25
 */
public class MatchDataFirstBean {

    /**
     * id : 1
     * category_id : 2
     * name_zh : 英格兰
     * logo : https://cdn.sportnanoapi.com/football/country
     */

    private int id;
    private int category_id;
    private String name_zh;
    private String logo;
    private List<MatchDataSecondBean> list;
    private boolean isExpand;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getName_zh() {
        return name_zh;
    }

    public void setName_zh(String name_zh) {
        this.name_zh = name_zh;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<MatchDataSecondBean> getList() {
        return list;
    }

    public void setList(List<MatchDataSecondBean> list) {
        this.list = list;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }
}
