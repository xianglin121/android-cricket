package com.longya.live.model;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/12/25
 */
public class RankingUserBean {

    /**
     * uid : 2
     * amount : 0
     * avatar : http://cunchu.mhuan.shop//avatar.png
     * user_nickname : 手机用户0152
     * exp : 2
     * votestotal : 1
     * is_guard : 1
     * votestotal_icon : http://cunchu.mhuan.shop//ico/grade/level_anchor_1.png
     * exp_icon : http://cunchu.mhuan.shop//ico/level/level_2.png
     * is_watchlist : 0
     * is_live : 0
     */

    private int uid;
    private String amount;
    private String avatar;
    private String user_nickname;
    private String exp;
    private String votestotal;
    private int is_guard;
    private String votestotal_icon;
    private String exp_icon;
    private int is_watchlist;
    private int is_live;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getVotestotal() {
        return votestotal;
    }

    public void setVotestotal(String votestotal) {
        this.votestotal = votestotal;
    }

    public int getIs_guard() {
        return is_guard;
    }

    public void setIs_guard(int is_guard) {
        this.is_guard = is_guard;
    }

    public String getVotestotal_icon() {
        return votestotal_icon;
    }

    public void setVotestotal_icon(String votestotal_icon) {
        this.votestotal_icon = votestotal_icon;
    }

    public String getExp_icon() {
        return exp_icon;
    }

    public void setExp_icon(String exp_icon) {
        this.exp_icon = exp_icon;
    }

    public int getIs_watchlist() {
        return is_watchlist;
    }

    public void setIs_watchlist(int is_watchlist) {
        this.is_watchlist = is_watchlist;
    }

    public int getIs_live() {
        return is_live;
    }

    public void setIs_live(int is_live) {
        this.is_live = is_live;
    }
}
