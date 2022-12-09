package com.onecric.live.model;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/29
 */
public class FootballDetailBean {

    /**
     * id : 3625185
     * status : 8
     * competition_name_zh : 德国杯
     * competition_name_zht : 德國盃
     * competition_name_en : GER Cup
     * home_team_name_zh : 慕尼黑1860
     * home_team_name_zht : 慕尼黑1860
     * home_team_name_en : TSV 1860 Munchen
     * away_team_name_zh : 沙尔克04
     * away_team_name_zht : 史浩克零四
     * away_team_name_en : Schalke 04
     * home_team_log : https://cdn.sportnanoapi.com/football/team/7500f685a80b5c3fa89986da91d1f987.png
     * away_team_log : https://cdn.sportnanoapi.com/football/team/4533ab7ef9ad5e53c981f7ea7bea7033.png
     * time : 2021-10-23 10:50
     * half_score : 1-0
     * mlive : 1
     * home_data : [1,2,0,81,50,40,5,13,8]
     * away_data : [0,1,1,104,51,60,6,11,8]
     */

    private int id;
    private int status;
    private int is_collect;
    private String competition_name_zh;
    private String competition_name_zht;
    private String competition_name_en;
    private String home_team_name_zh;
    private String home_team_name_zht;
    private String home_team_name_en;
    private String away_team_name_zh;
    private String away_team_name_zht;
    private String away_team_name_en;
    private String home_team_log;
    private String away_team_log;
    private String time;
    private String half_score;
    private String live_animation_url;
    private String pushurl1;
    private String pushurl2;
    private int mlive;
    private List<Integer> home_data;
    private List<Integer> away_data;
    private FootballSpaceBean space;
    private EnvironmentBean environment;
    private List<FootballEventBean> tlive;
    private List<FootballTypeBean> trend;
    private List<FootballStatisticBean> technology;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
    }

    public String getCompetition_name_zh() {
        return competition_name_zh;
    }

    public void setCompetition_name_zh(String competition_name_zh) {
        this.competition_name_zh = competition_name_zh;
    }

    public String getCompetition_name_zht() {
        return competition_name_zht;
    }

    public void setCompetition_name_zht(String competition_name_zht) {
        this.competition_name_zht = competition_name_zht;
    }

    public String getCompetition_name_en() {
        return competition_name_en;
    }

    public void setCompetition_name_en(String competition_name_en) {
        this.competition_name_en = competition_name_en;
    }

    public String getHome_team_name_zh() {
        return home_team_name_zh;
    }

    public void setHome_team_name_zh(String home_team_name_zh) {
        this.home_team_name_zh = home_team_name_zh;
    }

    public String getHome_team_name_zht() {
        return home_team_name_zht;
    }

    public void setHome_team_name_zht(String home_team_name_zht) {
        this.home_team_name_zht = home_team_name_zht;
    }

    public String getHome_team_name_en() {
        return home_team_name_en;
    }

    public void setHome_team_name_en(String home_team_name_en) {
        this.home_team_name_en = home_team_name_en;
    }

    public String getAway_team_name_zh() {
        return away_team_name_zh;
    }

    public void setAway_team_name_zh(String away_team_name_zh) {
        this.away_team_name_zh = away_team_name_zh;
    }

    public String getAway_team_name_zht() {
        return away_team_name_zht;
    }

    public void setAway_team_name_zht(String away_team_name_zht) {
        this.away_team_name_zht = away_team_name_zht;
    }

    public String getAway_team_name_en() {
        return away_team_name_en;
    }

    public void setAway_team_name_en(String away_team_name_en) {
        this.away_team_name_en = away_team_name_en;
    }

    public String getHome_team_log() {
        return home_team_log;
    }

    public void setHome_team_log(String home_team_log) {
        this.home_team_log = home_team_log;
    }

    public String getAway_team_log() {
        return away_team_log;
    }

    public void setAway_team_log(String away_team_log) {
        this.away_team_log = away_team_log;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHalf_score() {
        return half_score;
    }

    public void setHalf_score(String half_score) {
        this.half_score = half_score;
    }

    public int getMlive() {
        return mlive;
    }

    public void setMlive(int mlive) {
        this.mlive = mlive;
    }

    public List<Integer> getHome_data() {
        return home_data;
    }

    public void setHome_data(List<Integer> home_data) {
        this.home_data = home_data;
    }

    public List<Integer> getAway_data() {
        return away_data;
    }

    public void setAway_data(List<Integer> away_data) {
        this.away_data = away_data;
    }

    public FootballSpaceBean getSpace() {
        return space;
    }

    public void setSpace(FootballSpaceBean space) {
        this.space = space;
    }

    public EnvironmentBean getEnvironment() {
        return environment;
    }

    public void setEnvironment(EnvironmentBean environment) {
        this.environment = environment;
    }

    public List<FootballEventBean> getTlive() {
        return tlive;
    }

    public void setTlive(List<FootballEventBean> tlive) {
        this.tlive = tlive;
    }

    public List<FootballTypeBean> getTrend() {
        return trend;
    }

    public void setTrend(List<FootballTypeBean> trend) {
        this.trend = trend;
    }

    public List<FootballStatisticBean> getTechnology() {
        return technology;
    }

    public void setTechnology(List<FootballStatisticBean> technology) {
        this.technology = technology;
    }

    public String getLive_animation_url() {
        return live_animation_url;
    }

    public void setLive_animation_url(String live_animation_url) {
        this.live_animation_url = live_animation_url;
    }

    public String getPushurl1() {
        return pushurl1;
    }

    public void setPushurl1(String pushurl1) {
        this.pushurl1 = pushurl1;
    }

    public String getPushurl2() {
        return pushurl2;
    }

    public void setPushurl2(String pushurl2) {
        this.pushurl2 = pushurl2;
    }
}
