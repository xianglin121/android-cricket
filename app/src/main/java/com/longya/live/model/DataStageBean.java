package com.longya.live.model;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/3/5
 */
public class DataStageBean {

    /**
     * id : 1
     * name : A组
     */

    private int id;
    private String name;
    private List<DataStageBean> lower;

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

    public List<DataStageBean> getLower() {
        return lower;
    }

    public void setLower(List<DataStageBean> lower) {
        this.lower = lower;
    }
}
