package com.longya.live.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/26
 */
public class FootballMatchBean implements MultiItemEntity {

    /**
     * id : 52230
     * status : 1
     * competition_name_zh : 卡塔乙
     * competition_name_zht : 卡塔乙
     * competition_name_en : QAT Second Division
     * primary_color :
     * secondary_color :
     * home_team_name_zh : 穆艾塔罗
     * home_team_name_zht : 穆艾塔羅
     * home_team_name_en : Muaither SC
     * away_team_name_zh : 艾马希亚
     * away_team_name_zht : 艾馬希亞
     * away_team_name_en : Al Markhiya
     * time : 23:45
     * match_time :
     * half_score :
     * corner_kick :
     * home_score :
     * away_score :
     * home_yellow_card : 0
     * away_yellow_card : 0
     * home_red_card : 0
     * away_red_card : 0
     * mlive : 0
     */

    private int id;
    private int status;
    private String competition_name_zh;
    private String competition_name_zht;
    private String competition_name_en;
    private String primary_color;
    private String secondary_color;
    private String home_team_name_zh;
    private String home_team_name_zht;
    private String home_team_name_en;
    private String away_team_name_zh;
    private String away_team_name_zht;
    private String away_team_name_en;
    private String time;
    private Long match_time;
    private String match_str;
    private String half_score;
    private String corner_kick;
    private String home_score;
    private String away_score;
    private int home_yellow_card;
    private int away_yellow_card;
    private int home_red_card;
    private int away_red_card;
    private int mlive;
    private int is_collect;
    private LiveAnchorBean anchor;

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

    public String getPrimary_color() {
        return primary_color;
    }

    public void setPrimary_color(String primary_color) {
        this.primary_color = primary_color;
    }

    public String getSecondary_color() {
        return secondary_color;
    }

    public void setSecondary_color(String secondary_color) {
        this.secondary_color = secondary_color;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getMatch_time() {
        return match_time;
    }

    public void setMatch_time(Long match_time) {
        this.match_time = match_time;
    }

    public String getMatch_str() {
        return match_str;
    }

    public void setMatch_str(String match_str) {
        this.match_str = match_str;
    }

    public String getHalf_score() {
        return half_score;
    }

    public void setHalf_score(String half_score) {
        this.half_score = half_score;
    }

    public String getCorner_kick() {
        return corner_kick;
    }

    public void setCorner_kick(String corner_kick) {
        this.corner_kick = corner_kick;
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

    public int getHome_yellow_card() {
        return home_yellow_card;
    }

    public void setHome_yellow_card(int home_yellow_card) {
        this.home_yellow_card = home_yellow_card;
    }

    public int getAway_yellow_card() {
        return away_yellow_card;
    }

    public void setAway_yellow_card(int away_yellow_card) {
        this.away_yellow_card = away_yellow_card;
    }

    public int getHome_red_card() {
        return home_red_card;
    }

    public void setHome_red_card(int home_red_card) {
        this.home_red_card = home_red_card;
    }

    public int getAway_red_card() {
        return away_red_card;
    }

    public void setAway_red_card(int away_red_card) {
        this.away_red_card = away_red_card;
    }

    public int getMlive() {
        return mlive;
    }

    public void setMlive(int mlive) {
        this.mlive = mlive;
    }

    public int getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
    }

    public LiveAnchorBean getAnchor() {
        return anchor;
    }

    public void setAnchor(LiveAnchorBean anchor) {
        this.anchor = anchor;
    }

    public static final int HEAD = 1;
    public static final int CONTENT = 0;

    private int itemType;

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return 0;
    }
}
