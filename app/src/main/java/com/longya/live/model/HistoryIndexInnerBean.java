package com.longya.live.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/11/6
 */
public class HistoryIndexInnerBean implements MultiItemEntity, Serializable {
    public static final int HEAD = 1;
    public static final int CONTENT = 0;
    /**
     * home : 16
     * away : 14
     * flat : 0
     * home_percentage : 53.3
     * away_percentage : 46.7
     * flat_percentage : 0
     */

    private int home;
    private int away;
    private int flat;
    private String home_percentage;
    private String away_percentage;
    private String flat_percentage;
    private int id;
    private String company_name;
    private String win_rate;
    private String loss_rate;
    private int itemType;

    public int getHome() {
        return home;
    }

    public void setHome(int home) {
        this.home = home;
    }

    public int getAway() {
        return away;
    }

    public void setAway(int away) {
        this.away = away;
    }

    public int getFlat() {
        return flat;
    }

    public void setFlat(int flat) {
        this.flat = flat;
    }

    public String getHome_percentage() {
        return home_percentage;
    }

    public void setHome_percentage(String home_percentage) {
        this.home_percentage = home_percentage;
    }

    public String getAway_percentage() {
        return away_percentage;
    }

    public void setAway_percentage(String away_percentage) {
        this.away_percentage = away_percentage;
    }

    public String getFlat_percentage() {
        return flat_percentage;
    }

    public void setFlat_percentage(String flat_percentage) {
        this.flat_percentage = flat_percentage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getWin_rate() {
        return win_rate;
    }

    public void setWin_rate(String win_rate) {
        this.win_rate = win_rate;
    }

    public String getLoss_rate() {
        return loss_rate;
    }

    public void setLoss_rate(String loss_rate) {
        this.loss_rate = loss_rate;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
