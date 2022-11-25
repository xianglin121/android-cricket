package com.longya.live.model;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/11/9
 */
public class HistoryIndexDetailBean {
    private int id;
    private String name;
    private HistoryIndexInnerBean tonglian;
    private HistoryIndexInnerBean all;
    private HistoryIndexInnerBean analysis;
    private List<HistoryIndexDetailInnerBean> tonglian_list;
    private List<HistoryIndexDetailInnerBean> list;

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

    public HistoryIndexInnerBean getTonglian() {
        return tonglian;
    }

    public void setTonglian(HistoryIndexInnerBean tonglian) {
        this.tonglian = tonglian;
    }

    public HistoryIndexInnerBean getAll() {
        return all;
    }

    public void setAll(HistoryIndexInnerBean all) {
        this.all = all;
    }

    public HistoryIndexInnerBean getAnalysis() {
        return analysis;
    }

    public void setAnalysis(HistoryIndexInnerBean analysis) {
        this.analysis = analysis;
    }

    public List<HistoryIndexDetailInnerBean> getTonglian_list() {
        return tonglian_list;
    }

    public void setTonglian_list(List<HistoryIndexDetailInnerBean> tonglian_list) {
        this.tonglian_list = tonglian_list;
    }

    public List<HistoryIndexDetailInnerBean> getList() {
        return list;
    }

    public void setList(List<HistoryIndexDetailInnerBean> list) {
        this.list = list;
    }
}
