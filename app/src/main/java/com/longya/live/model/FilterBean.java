package com.longya.live.model;

import java.io.Serializable;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/18
 */
public class FilterBean implements Serializable {
    private String name;
    private List<FilterInnerBean> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FilterInnerBean> getList() {
        return list;
    }

    public void setList(List<FilterInnerBean> list) {
        this.list = list;
    }
}
