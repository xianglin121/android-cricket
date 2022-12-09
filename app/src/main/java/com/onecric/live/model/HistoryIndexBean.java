package com.onecric.live.model;

import java.io.Serializable;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/11/6
 */
public class HistoryIndexBean implements Serializable {

    /**
     * id : 2
     * name : bet365
     */

    private int id;
    private String name;
    private HistoryIndexInnerBean tonglian;
    private HistoryIndexInnerBean all;
    private HistoryIndexInnerBean analysis;

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
}
