package com.longya.live.model;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/3/4
 */
public class DataBestLeftBean {
    private int type;
    private String name;
    private boolean isSelect;

    public DataBestLeftBean(int type, String name, boolean isSelect) {
        this.type = type;
        this.name = name;
        this.isSelect = isSelect;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
