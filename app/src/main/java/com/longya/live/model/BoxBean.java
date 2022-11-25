package com.longya.live.model;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/1/14
 */
public class BoxBean {

    /**
     * id : 1
     * time : 5
     * gift_name : 点赞
     * gift_img : https://app.longya.tv/admin/20211208/4a70766d34ffd1bc005c37d6ec5fd975.png
     * num : 1
     * status : 1
     */

    private int id;
    private int time;
    private String gift_name;
    private String gift_img;
    private int num;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getGift_name() {
        return gift_name;
    }

    public void setGift_name(String gift_name) {
        this.gift_name = gift_name;
    }

    public String getGift_img() {
        return gift_img;
    }

    public void setGift_img(String gift_img) {
        this.gift_img = gift_img;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
