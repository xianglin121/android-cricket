package com.onecric.live.model;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/11/9
 */
public class FootballHistoryIndexDetailBean {
    private int id;
    private String name;
    private FootballHistoryIndexInnerBean tonglian;
    private FootballHistoryIndexInnerBean all;
    private String initial_won;
    private String initial_draw;
    private String initial_loss;
    private List<FootballHistoryIndexDetailInnerBean> tonglian_list;
    private List<FootballHistoryIndexDetailInnerBean> list;

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

    public FootballHistoryIndexInnerBean getTonglian() {
        return tonglian;
    }

    public void setTonglian(FootballHistoryIndexInnerBean tonglian) {
        this.tonglian = tonglian;
    }

    public FootballHistoryIndexInnerBean getAll() {
        return all;
    }

    public void setAll(FootballHistoryIndexInnerBean all) {
        this.all = all;
    }

    public String getInitial_won() {
        return initial_won;
    }

    public void setInitial_won(String initial_won) {
        this.initial_won = initial_won;
    }

    public String getInitial_draw() {
        return initial_draw;
    }

    public void setInitial_draw(String initial_draw) {
        this.initial_draw = initial_draw;
    }

    public String getInitial_loss() {
        return initial_loss;
    }

    public void setInitial_loss(String initial_loss) {
        this.initial_loss = initial_loss;
    }

    public List<FootballHistoryIndexDetailInnerBean> getTonglian_list() {
        return tonglian_list;
    }

    public void setTonglian_list(List<FootballHistoryIndexDetailInnerBean> tonglian_list) {
        this.tonglian_list = tonglian_list;
    }

    public List<FootballHistoryIndexDetailInnerBean> getList() {
        return list;
    }

    public void setList(List<FootballHistoryIndexDetailInnerBean> list) {
        this.list = list;
    }
}
