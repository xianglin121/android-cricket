package com.onecric.live.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/11/2
 */
public class BasketballIndexOneBean implements MultiItemEntity {
    public static final int HEAD = 1;
    public static final int CONTENT = 0;
    /**
     * early_host : 1.06
     * early_early : 0
     * early_guest : 8
     * scilicet_guest : 4.3
     * scilicet_early : 0
     * scilicet_host : 1.2
     * company_name : BET365
     * company_id : 2
     */

    private String early_host;
    private String early_early;
    private String early_guest;
    private String scilicet_guest;
    private String scilicet_early;
    private String scilicet_host;
    private String company_name;
    private int company_id;
    private boolean isSelected;
    private int itemType;

    public String getEarly_host() {
        return early_host;
    }

    public void setEarly_host(String early_host) {
        this.early_host = early_host;
    }

    public String getEarly_early() {
        return early_early;
    }

    public void setEarly_early(String early_early) {
        this.early_early = early_early;
    }

    public String getEarly_guest() {
        return early_guest;
    }

    public void setEarly_guest(String early_guest) {
        this.early_guest = early_guest;
    }

    public String getScilicet_guest() {
        return scilicet_guest;
    }

    public void setScilicet_guest(String scilicet_guest) {
        this.scilicet_guest = scilicet_guest;
    }

    public String getScilicet_early() {
        return scilicet_early;
    }

    public void setScilicet_early(String scilicet_early) {
        this.scilicet_early = scilicet_early;
    }

    public String getScilicet_host() {
        return scilicet_host;
    }

    public void setScilicet_host(String scilicet_host) {
        this.scilicet_host = scilicet_host;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
