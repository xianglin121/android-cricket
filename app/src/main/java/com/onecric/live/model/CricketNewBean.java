package com.onecric.live.model;

import java.util.List;

public class CricketNewBean {
    private String name;
    private String type;
    private List<CricketMatchNewBean> cricketMatch;
    private String tournamentId;

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

    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }

    public static class CricketMatchNewBean {
        private String awayName;
        private int fastStatus;
        private String homeDisplayScore;
        private String scheduled;
        private String tournamentId;
        private int awayId;
        private String matchNum;
        private String awayDisplayScore;
        private String homeName;
        private String matchResult;
        private int homeId;
        private int id;
        private String liveStatus;
        private int islive;
        private String awayLogo;
        private String liveTime;
        private String tname;
        private String todaystart;
        private String matchStatus;
        private int liveId;
        private String homeLogo;
        private String ttype;
        private String liveUid;
        private long liveTimeUnix;
        private int isSubscribe;
        private int status;
        private int channel;
        private int matchLive;
        private int winId;

        public String getAwayName() {
            return awayName;
        }

        public void setAwayName(String awayName) {
            this.awayName = awayName;
        }

        public int getFastStatus() {
            return fastStatus;
        }

        public void setFastStatus(int fastStatus) {
            this.fastStatus = fastStatus;
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

        public String getLiveStatus() {
            return liveStatus;
        }

        public void setLiveStatus(String liveStatus) {
            this.liveStatus = liveStatus;
        }

        public int getIslive() {
            return islive;
        }

        public void setIslive(int islive) {
            this.islive = islive;
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

        public String getTname() {
            return tname;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        public String getTodaystart() {
            return todaystart;
        }

        public void setTodaystart(String todaystart) {
            this.todaystart = todaystart;
        }

        public String getMatchStatus() {
            return matchStatus;
        }

        public void setMatchStatus(String matchStatus) {
            this.matchStatus = matchStatus;
        }

        public int getLiveId() {
            return liveId;
        }

        public void setLiveId(int liveId) {
            this.liveId = liveId;
        }

        public String getHomeLogo() {
            return homeLogo;
        }

        public void setHomeLogo(String homeLogo) {
            this.homeLogo = homeLogo;
        }

        public String getTtype() {
            return ttype;
        }

        public void setTtype(String ttype) {
            this.ttype = ttype;
        }

        public String getLiveUid() {
            return liveUid;
        }

        public void setLiveUid(String liveUid) {
            this.liveUid = liveUid;
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

        public int getChannel() {
            return channel;
        }

        public void setChannel(int channel) {
            this.channel = channel;
        }

        public int getMatchLive() {
            return matchLive;
        }

        public void setMatchLive(int matchLive) {
            this.matchLive = matchLive;
        }

        public int getWinId() {
            return winId;
        }

        public void setWinId(int winId) {
            this.winId = winId;
        }
    }
}
