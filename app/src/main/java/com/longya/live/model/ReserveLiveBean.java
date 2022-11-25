package com.longya.live.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/12/15
 */
public class ReserveLiveBean implements MultiItemEntity {
    public static final int HEAD = 1;
    public static final int CONTENT = 0;
    /**
     * id : 16
     * uid : 2
     * live_id : 4
     * type : 0
     * type2 : 1
     * match_id : 3502808
     * typeName : 足球
     * match_time : 1678114800
     * away_logo : https://cdn.sportnanoapi.com/football/team/38f1a7d02100edb107af962e0e5a4d2e.png
     * away_name : 乐高城
     * home_logo : https://cdn.sportnanoapi.com/football/team/38f1a7d02100edb107af962e0e5a4d2e.png
     * home_name : 泰奇曼城
     * home_scores : 0
     * away_scores : 0
     * status_id : 1
     * name : 加纳超
     * key : 03-06
     * start : 23:00
     */

    private int itemType;
    private int id;
    private int uid;
    private int live_id;
    private int type;
    private int type2;
    private int match_id;
    private String typeName;
    private String match_time;
    private String away_logo;
    private String away_name;
    private String home_logo;
    private String home_name;
    private int home_scores;
    private int away_scores;
    private int status_id;
    private String name;
    private String key;
    private String start;
    private String start2;
    private int is_reserve;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getLive_id() {
        return live_id;
    }

    public void setLive_id(int live_id) {
        this.live_id = live_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType2() {
        return type2;
    }

    public void setType2(int type2) {
        this.type2 = type2;
    }

    public int getMatch_id() {
        return match_id;
    }

    public void setMatch_id(int match_id) {
        this.match_id = match_id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getMatch_time() {
        return match_time;
    }

    public void setMatch_time(String match_time) {
        this.match_time = match_time;
    }

    public String getAway_logo() {
        return away_logo;
    }

    public void setAway_logo(String away_logo) {
        this.away_logo = away_logo;
    }

    public String getAway_name() {
        return away_name;
    }

    public void setAway_name(String away_name) {
        this.away_name = away_name;
    }

    public String getHome_logo() {
        return home_logo;
    }

    public void setHome_logo(String home_logo) {
        this.home_logo = home_logo;
    }

    public String getHome_name() {
        return home_name;
    }

    public void setHome_name(String home_name) {
        this.home_name = home_name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getStart2() {
        return start2;
    }

    public void setStart2(String start2) {
        this.start2 = start2;
    }

    public int getIs_reserve() {
        return is_reserve;
    }

    public void setIs_reserve(int is_reserve) {
        this.is_reserve = is_reserve;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
