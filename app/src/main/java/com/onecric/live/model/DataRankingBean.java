package com.onecric.live.model;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/3/4
 */
public class DataRankingBean {
    private String name;
    private List<DataRankingInnerBean> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DataRankingInnerBean> getList() {
        return list;
    }

    public void setList(List<DataRankingInnerBean> list) {
        this.list = list;
    }
}
