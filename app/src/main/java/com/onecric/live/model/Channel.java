package com.onecric.live.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

public class Channel implements MultiItemEntity, Serializable {
    private static final long serialVersionUID = 141315161718191140L;

    public static final int TYPE_MY = 1;
    public static final int TYPE_OTHER = 2;
    public static final int TYPE_MY_CHANNEL = 3;
    public static final int TYPE_OTHER_CHANNEL = 4;

    public int sourceid;
    public String short_name_zh;
    public String channelCode;
    private int type;//比赛类型 0.足球 1.篮球
    public int selected;//是否是默认分类
    public int itemType;
    private String logoUrl;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}