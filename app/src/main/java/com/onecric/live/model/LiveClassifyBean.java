package com.onecric.live.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/11
 */
public class LiveClassifyBean implements MultiItemEntity {
    public static final int HEAD = 1;
    public static final int CONTENT = 0;

    private int itemType;
    /**
     * sourceid : 3660707
     * competition : 世欧预
     * match_time : 03-30 02:45
     * home_team_logo :
     * away_team_logo :
     * home_scores : 0
     * away_scores : 0
     * status_id : 1
     * mlive : 0
     * type : 0
     * collect : 0
     * match_str :
     * match_date : 2022-03-30
     */

    private int sourceid;
    private String competition;
    private String competition_zht;
    private String competition_en;
    private String match_time;
    private String home_team_name;
    private String home_team_name_zht;
    private String home_team_name_en;
    private String away_team_name;
    private String away_team_name_zht;
    private String away_team_name_en;
    private String home_team_logo;
    private String away_team_logo;
    private String status_str;
    private int home_scores;
    private int away_scores;
    private int status_id;
    private int status_type;
    private int reserve;
    private int mlive;
    private int type;
    private int collect;
    private String match_str;
    private String match_date;

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public int getSourceid() {
        return sourceid;
    }

    public void setSourceid(int sourceid) {
        this.sourceid = sourceid;
    }

    public String getCompetition() {
        return competition;
    }

    public void setCompetition(String competition) {
        this.competition = competition;
    }

    public String getCompetition_zht() {
        return competition_zht;
    }

    public void setCompetition_zht(String competition_zht) {
        this.competition_zht = competition_zht;
    }

    public String getCompetition_en() {
        return competition_en;
    }

    public void setCompetition_en(String competition_en) {
        this.competition_en = competition_en;
    }

    public String getHome_team_name() {
        return home_team_name;
    }

    public void setHome_team_name(String home_team_name) {
        this.home_team_name = home_team_name;
    }

    public String getAway_team_name() {
        return away_team_name;
    }

    public void setAway_team_name(String away_team_name) {
        this.away_team_name = away_team_name;
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

    public String getStatus_str() {
        return status_str;
    }

    public void setStatus_str(String status_str) {
        this.status_str = status_str;
    }

    public String getMatch_time() {
        return match_time;
    }

    public void setMatch_time(String match_time) {
        this.match_time = match_time;
    }

    public String getHome_team_logo() {
        return home_team_logo;
    }

    public void setHome_team_logo(String home_team_logo) {
        this.home_team_logo = home_team_logo;
    }

    public String getAway_team_logo() {
        return away_team_logo;
    }

    public void setAway_team_logo(String away_team_logo) {
        this.away_team_logo = away_team_logo;
    }

    public int getHome_scores() {
        return home_scores;
    }

    public void setHome_scores(int home_scores) {
        this.home_scores = home_scores;
    }

    public int getAway_scores() {
        return away_scores;
    }

    public void setAway_scores(int away_scores) {
        this.away_scores = away_scores;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public int getMlive() {
        return mlive;
    }

    public void setMlive(int mlive) {
        this.mlive = mlive;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCollect() {
        return collect;
    }

    public void setCollect(int collect) {
        this.collect = collect;
    }

    public String getMatch_str() {
        return match_str;
    }

    public void setMatch_str(String match_str) {
        this.match_str = match_str;
    }

    public String getMatch_date() {
        return match_date;
    }

    public void setMatch_date(String match_date) {
        this.match_date = match_date;
    }

    public int getStatus_type() {
        return status_type;
    }

    public void setStatus_type(int status_type) {
        this.status_type = status_type;
    }

    public int getReserve() {
        return reserve;
    }

    public void setReserve(int reserve) {
        this.reserve = reserve;
    }

}
