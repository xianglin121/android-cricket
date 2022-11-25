package com.longya.live.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/11
 */
public class IndexBean implements MultiItemEntity {
    public static final int HEAD = 1;
    public static final int CONTENT = 0;

    private String name;
    private boolean isSelected;
    private int itemType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
