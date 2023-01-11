package com.onecric.live.model;

import java.io.Serializable;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/27
 */
public class UserBean implements Serializable {
    private static final long serialVersionUID = 141315161718191140L;
    /**
     * id : 5
     * sex : 0
     * balance : 0.00
     * user_nickname : 18370800000
     * avatar :
     * mobile : 18370800000
     * token : 05a5f5ec2b447119e8c8c80ffbbd55b5d6165ef8f592839bf582fcdff12d5d7e
     */

    private int id;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    private int uid;
    private int followed_id;
    private int sex;
    private String balance;
    private String balances;
    private String withdrawal_balance;
    private String user_nickname;
    private String signature;
    private String avatar;
    private String mobile;
    private String token;
    private String userSig;

    public int isIs_attention() {
        return is_attention;
    }

    public void setIs_attention(int is_attention) {
        this.is_attention = is_attention;
    }

    private int is_attention;

    public String getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(String onlineTime) {
        this.onlineTime = onlineTime;
    }

    private String onlineTime;
    private int is_live;
    private int is_guard;
    private int is_defray_pass;
    private int is_anchor;
    private int is_writer;
    private String exp_icon;
    private String votestotal_icon;
    private int attention;
    private int attention_num;
    private int difference;
    private int current_exp;
    private NobelListBean guard;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFollowed_id() {
        return followed_id;
    }

    public void setFollowed_id(int followed_id) {
        this.followed_id = followed_id;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getBalances() {
        return balances;
    }

    public void setBalances(String balances) {
        this.balances = balances;
    }

    public String getWithdrawal_balance() {
        return withdrawal_balance;
    }

    public void setWithdrawal_balance(String withdrawal_balance) {
        this.withdrawal_balance = withdrawal_balance;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserSig() {
        return userSig;
    }

    public void setUserSig(String userSig) {
        this.userSig = userSig;
    }

    public int getIs_live() {
        return is_live;
    }

    public void setIs_live(int is_live) {
        this.is_live = is_live;
    }

    public int getIs_guard() {
        return is_guard;
    }

    public void setIs_guard(int is_guard) {
        this.is_guard = is_guard;
    }

    public int getIs_defray_pass() {
        return is_defray_pass;
    }

    public void setIs_defray_pass(int is_defray_pass) {
        this.is_defray_pass = is_defray_pass;
    }

    public String getExp_icon() {
        return exp_icon;
    }

    public void setExp_icon(String exp_icon) {
        this.exp_icon = exp_icon;
    }

    public String getVotestotal_icon() {
        return votestotal_icon;
    }

    public void setVotestotal_icon(String votestotal_icon) {
        this.votestotal_icon = votestotal_icon;
    }

    public int getAttention() {
        return attention;
    }

    public void setAttention(int attention) {
        this.attention = attention;
    }

    public NobelListBean getGuard() {
        return guard;
    }

    public void setGuard(NobelListBean guard) {
        this.guard = guard;
    }

    public int getIs_anchor() {
        return is_anchor;
    }

    public void setIs_anchor(int is_anchor) {
        this.is_anchor = is_anchor;
    }

    public int getIs_writer() {
        return is_writer;
    }

    public void setIs_writer(int is_writer) {
        this.is_writer = is_writer;
    }

    public int getAttention_num() {
        return attention_num;
    }

    public void setAttention_num(int attention_num) {
        this.attention_num = attention_num;
    }

    public int getDifference() {
        return difference;
    }

    public void setDifference(int difference) {
        this.difference = difference;
    }

    public int getCurrent_exp() {
        return current_exp;
    }

    public void setCurrent_exp(int current_exp) {
        this.current_exp = current_exp;
    }
}
