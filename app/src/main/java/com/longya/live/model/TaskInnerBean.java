package com.longya.live.model;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/1/11
 */
public class TaskInnerBean {

    /**
     * value : 100-201
     * lz_num : 100
     * withdraw : 1
     */

    private String value;
    private int lz_num;
    private int withdraw;
    private String str;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getLz_num() {
        return lz_num;
    }

    public void setLz_num(int lz_num) {
        this.lz_num = lz_num;
    }

    public int getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(int withdraw) {
        this.withdraw = withdraw;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
