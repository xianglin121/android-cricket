package com.onecric.live.model;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/11/2
 */
public class BasketballIndexDetailBean {

    /**
     * company_name : BET365
     * company_id : 2
     * host : 1.2
     * early : 0
     * guest : 4.3
     * date : 09.26
     * change_time : 22:12
     * sourcetime : 1632665541
     * time_name : 赛后1'
     */

    private String company_name;
    private int company_id;
    private String host;
    private String early;
    private String guest;
    private String date;
    private String change_time;
    private int sourcetime;
    private String time_name;

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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getEarly() {
        return early;
    }

    public void setEarly(String early) {
        this.early = early;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getChange_time() {
        return change_time;
    }

    public void setChange_time(String change_time) {
        this.change_time = change_time;
    }

    public int getSourcetime() {
        return sourcetime;
    }

    public void setSourcetime(int sourcetime) {
        this.sourcetime = sourcetime;
    }

    public String getTime_name() {
        return time_name;
    }

    public void setTime_name(String time_name) {
        this.time_name = time_name;
    }
}
