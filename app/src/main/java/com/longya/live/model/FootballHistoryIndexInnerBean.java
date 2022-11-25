package com.longya.live.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/11/6
 */
public class FootballHistoryIndexInnerBean implements MultiItemEntity, Serializable {
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

    private String won;
    private String draw;
    private String loss;
    private String win_rate;
    private String draw_rate;
    private String loss_rate;
    private String company_name;
    private int itemType;

    public String getWon() {
        return won;
    }

    public void setWon(String won) {
        this.won = won;
    }

    public String getDraw() {
        return draw;
    }

    public void setDraw(String draw) {
        this.draw = draw;
    }

    public String getLoss() {
        return loss;
    }

    public void setLoss(String loss) {
        this.loss = loss;
    }

    public String getWin_rate() {
        return win_rate;
    }

    public void setWin_rate(String win_rate) {
        this.win_rate = win_rate;
    }

    public String getDraw_rate() {
        return draw_rate;
    }

    public void setDraw_rate(String draw_rate) {
        this.draw_rate = draw_rate;
    }

    public String getLoss_rate() {
        return loss_rate;
    }

    public void setLoss_rate(String loss_rate) {
        this.loss_rate = loss_rate;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
