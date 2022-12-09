package com.onecric.live.model;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/29
 */
public class FootballEventBean {

    /**
     * data : 3' - 3分钟，沙尔克04获得本场第1个角球
     * main : 1
     * time : 3'
     * type : 2
     * position : 2
     * type_str : 角球
     */

    private String data;
    private int main;
    private String time;
    private int type;
    private int position;
    private String type_str;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getMain() {
        return main;
    }

    public void setMain(int main) {
        this.main = main;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getType_str() {
        return type_str;
    }

    public void setType_str(String type_str) {
        this.type_str = type_str;
    }
}
