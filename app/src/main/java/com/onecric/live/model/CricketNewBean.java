package com.onecric.live.model;

import java.util.List;

public class CricketNewBean {
    private String name;
    private String type;
    private List<CricketMatchNewBean> cricketMatch;
    private int tournamentId;
    public boolean isHasTitle;

    public  String date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<CricketMatchNewBean> getCricketMatch() {
        return cricketMatch;
    }

    public void setCricketMatch(List<CricketMatchNewBean> cricketMatch) {
        this.cricketMatch = cricketMatch;
    }

    public int getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
    }

    public static class CricketMatchNewBean {
        private String awayName;
        private String awayLogo;
        private String liveTime;
        private String homeDisplayScore;
        private String scheduled;
        private String tournamentId;
        private int awayId;
        private int liveId;
        private String matchNum;
        private String awayDisplayScore;
        private String homeName;
        private String matchResult;
        private String homeLogo;
        private int liveUid;
        private int homeId;
        private int id;
        private long liveTimeUnix;
        private int isSubscribe;
        private int status;
        private int liveStatus;
        private String match_live;
        private int win_id;
        private int fast_status;

        public int getFast_status() {
            return fast_status;
        }

        public void setFast_status(int fast_status) {
            this.fast_status = fast_status;
        }

        public int getWin_id() {
            return win_id;
        }

        public void setWin_id(int win_id) {
            this.win_id = win_id;
        }

        public String getMatch_live() {
            return match_live;
        }

        public void setMatch_live(String match_live) {
            this.match_live = match_live;
        }

        public String getAwayName() {
            return awayName;
        }

        public void setAwayName(String awayName) {
            this.awayName = awayName;
        }

        public String getAwayLogo() {
            return awayLogo;
        }

        public void setAwayLogo(String awayLogo) {
            this.awayLogo = awayLogo;
        }

        public String getLiveTime() {
            return liveTime;
        }

        public void setLiveTime(String liveTime) {
            this.liveTime = liveTime;
        }

        public String getHomeDisplayScore() {
            return homeDisplayScore;
        }

        public void setHomeDisplayScore(String homeDisplayScore) {
            this.homeDisplayScore = homeDisplayScore;
        }

        public String getScheduled() {
            return scheduled;
        }

        public void setScheduled(String scheduled) {
            this.scheduled = scheduled;
        }

        public String getTournamentId() {
            return tournamentId;
        }

        public void setTournamentId(String tournamentId) {
            this.tournamentId = tournamentId;
        }

        public int getAwayId() {
            return awayId;
        }

        public void setAwayId(int awayId) {
            this.awayId = awayId;
        }

        public int getLiveId() {
            return liveId;
        }

        public void setLiveId(int liveId) {
            this.liveId = liveId;
        }

        public String getMatchNum() {
            return matchNum;
        }

        public void setMatchNum(String matchNum) {
            this.matchNum = matchNum;
        }

        public String getAwayDisplayScore() {
            return awayDisplayScore;
        }

        public void setAwayDisplayScore(String awayDisplayScore) {
            this.awayDisplayScore = awayDisplayScore;
        }

        public String getHomeName() {
            return homeName;
        }

        public void setHomeName(String homeName) {
            this.homeName = homeName;
        }

        public String getMatchResult() {
            return matchResult;
        }

        public void setMatchResult(String matchResult) {
            this.matchResult = matchResult;
        }

        public String getHomeLogo() {
            return homeLogo;
        }

        public void setHomeLogo(String homeLogo) {
            this.homeLogo = homeLogo;
        }

        public int getLiveUid() {
            return liveUid;
        }

        public void setLiveUid(int liveUid) {
            this.liveUid = liveUid;
        }

        public int getHomeId() {
            return homeId;
        }

        public void setHomeId(int homeId) {
            this.homeId = homeId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getLiveTimeUnix() {
            return liveTimeUnix;
        }

        public void setLiveTimeUnix(long liveTimeUnix) {
            this.liveTimeUnix = liveTimeUnix;
        }

        public int getIsSubscribe() {
            return isSubscribe;
        }

        public void setIsSubscribe(int isSubscribe) {
            this.isSubscribe = isSubscribe;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getLiveStatus() {
            return liveStatus;
        }

        public void setLiveStatus(int liveStatus) {
            this.liveStatus = liveStatus;
        }
    }
}
