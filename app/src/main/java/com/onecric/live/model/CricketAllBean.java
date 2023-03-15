package com.onecric.live.model;

import java.util.List;

public class CricketAllBean {
    private String endDay;
    private List<CricketDayBean> item;
    private String frontDay;

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public List<CricketDayBean> getItem() {
        return item;
    }

    public void setItem(List<CricketDayBean> item) {
        this.item = item;
    }

    public String getFrontDay() {
        return frontDay;
    }

    public void setFrontDay(String frontDay) {
        this.frontDay = frontDay;
    }

    public static class CricketDayBean {
        private List<CricketNewBean> list;
        private String day;

        public List<CricketNewBean> getList() {
            return list;
        }

        public void setList(List<CricketNewBean> list) {
            this.list = list;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

    }
}
