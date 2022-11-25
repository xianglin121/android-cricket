package com.longya.live.model;

import java.io.Serializable;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/11/27
 */
public class NobelListBean implements Serializable {

    /**
     * id : 9
     * name : 测试
     * icon : http://cunchu.mhuan.shop//admin/20211126/0f820cd197f687c81660d9fb69744483.png
     * swf : http://cunchu.mhuan.shop//admin/20211125/0896339ac13f491d8044c88231c4f6a8.svga
     * swf_name : 战马
     * rebate : 5
     * coin : 10
     * renew_coin : 9
     * barrage_id : 5
     * length_type : 1
     * length : 1
     * length_time : 2592000
     * list_order : 9999
     * trumpet : 0
     * gorgeous_id : 0
     * gorgeous_num : 0
     * color : #B981EE
     * barrage_level : 1
     */

    private int id;
    private String name;
    private String icon;
    private String swf;
    private String swf_name;
    private int rebate;
    private int coin;
    private int renew_coin;
    private int barrage_id;
    private int length_type;
    private int length;
    private int length_time;
    private int list_order;
    private int trumpet;
    private int gorgeous_id;
    private int gorgeous_num;
    private String color;
    private int barrage_level;
    private int order;
    private long endtime;

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSwf() {
        return swf;
    }

    public void setSwf(String swf) {
        this.swf = swf;
    }

    public String getSwf_name() {
        return swf_name;
    }

    public void setSwf_name(String swf_name) {
        this.swf_name = swf_name;
    }

    public int getRebate() {
        return rebate;
    }

    public void setRebate(int rebate) {
        this.rebate = rebate;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getRenew_coin() {
        return renew_coin;
    }

    public void setRenew_coin(int renew_coin) {
        this.renew_coin = renew_coin;
    }

    public int getBarrage_id() {
        return barrage_id;
    }

    public void setBarrage_id(int barrage_id) {
        this.barrage_id = barrage_id;
    }

    public int getLength_type() {
        return length_type;
    }

    public void setLength_type(int length_type) {
        this.length_type = length_type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getLength_time() {
        return length_time;
    }

    public void setLength_time(int length_time) {
        this.length_time = length_time;
    }

    public int getList_order() {
        return list_order;
    }

    public void setList_order(int list_order) {
        this.list_order = list_order;
    }

    public int getTrumpet() {
        return trumpet;
    }

    public void setTrumpet(int trumpet) {
        this.trumpet = trumpet;
    }

    public int getGorgeous_id() {
        return gorgeous_id;
    }

    public void setGorgeous_id(int gorgeous_id) {
        this.gorgeous_id = gorgeous_id;
    }

    public int getGorgeous_num() {
        return gorgeous_num;
    }

    public void setGorgeous_num(int gorgeous_num) {
        this.gorgeous_num = gorgeous_num;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getBarrage_level() {
        return barrage_level;
    }

    public void setBarrage_level(int barrage_level) {
        this.barrage_level = barrage_level;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public long getEndtime() {
        return endtime;
    }

    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }
}
