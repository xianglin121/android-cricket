package com.onecric.live.model;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/30
 */
public class FootballLineupPlayerBean {

    /**
     * name : 凯文·戈登
     * shirt_number : 16
     * position : D
     * x : 0
     * y : 0
     */

    private String name;
    private int shirt_number;
    private String position;
    private int x;
    private int y;
    private List<FootballLineupEventBean> incidents;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getShirt_number() {
        return shirt_number;
    }

    public void setShirt_number(int shirt_number) {
        this.shirt_number = shirt_number;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public List<FootballLineupEventBean> getIncidents() {
        return incidents;
    }

    public void setIncidents(List<FootballLineupEventBean> incidents) {
        this.incidents = incidents;
    }
}
