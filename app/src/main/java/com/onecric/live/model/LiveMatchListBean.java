package com.onecric.live.model;

import java.util.List;

public class LiveMatchListBean {

    private List<MatchItemBean> toay;
    private List<MatchItemBean> upcoming;

    public List<MatchItemBean> getToay() {
        return toay;
    }

    public void setToay(List<MatchItemBean> toay) {
        this.toay = toay;
    }

    public List<MatchItemBean> getUpcoming() {
        return upcoming;
    }

    public void setUpcoming(List<MatchItemBean> upcoming) {
        this.upcoming = upcoming;
    }

    /**
     *
     * "scheduled": "2018-02-24 16:00:00",
     * "title": "Pakistan Super League",
     * "home_name": "Quetta Gladiators",
     * "home_logo": "https:\/\/cdn.sportmonks.com\/images\/cricket\/teams\/16\/16.png",
     * "away_name": "Lahore Qalandars",
     * "away_logo": "https:\/\/cdn.sportmonks.com\/images\/cricket\/teams\/13\/13.png",
     * "home_score": "0\/0(0)",
     * "away_score": "0\/0(0)",
     * "match_id": 13679151,
     * "uid": 64,
     * "islive": 0
     *
     * "scheduled": "2018-02-24 16:00:00",
     * "title": "Sheffield Shield",
     * "home_name": "New South Wales Blues",
     * "home_logo": "https:\/\/api.onecric.tv\/upload\/diy\/BLU.png",
     * "away_name": "Victoria",
     * "away_logo": "https:\/\/cdn.sportmonks.com\/images\/cricket\/teams\/26\/122.png",
     * "home_score": 0,
     * "home_wickets": 0,
     * "home_display_overs": 0,
     * "away_score": 0,
     * "away_wickets": 0,
     * "away_display_overs": 0,
     * "match_id": 0
     */
    public static class MatchItemBean {
        private String scheduled;
        private String title;
        private String home_name;
        private String home_logo;
        private String away_name;
        private String away_logo;
        private int match_id;
        private String home_score;
        private String away_score;
        private int uid;
        private int islive;

        public String getScheduled() {
            return scheduled;
        }

        public void setScheduled(String scheduled) {
            this.scheduled = scheduled;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getHome_name() {
            return home_name;
        }

        public void setHome_name(String home_name) {
            this.home_name = home_name;
        }

        public String getHome_logo() {
            return home_logo;
        }

        public void setHome_logo(String home_logo) {
            this.home_logo = home_logo;
        }

        public String getAway_name() {
            return away_name;
        }

        public void setAway_name(String away_name) {
            this.away_name = away_name;
        }

        public String getAway_logo() {
            return away_logo;
        }

        public void setAway_logo(String away_logo) {
            this.away_logo = away_logo;
        }

        public int getMatch_id() {
            return match_id;
        }

        public void setMatch_id(int match_id) {
            this.match_id = match_id;
        }

        public String getHome_score() {
            return home_score;
        }

        public void setHome_score(String home_score) {
            this.home_score = home_score;
        }

        public String getAway_score() {
            return away_score;
        }

        public void setAway_score(String away_score) {
            this.away_score = away_score;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getIslive() {
            return islive;
        }

        public void setIslive(int islive) {
            this.islive = islive;
        }
    }
}
