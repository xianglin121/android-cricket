package com.longya.live.model;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/12/18
 */
public class RankingBean {

    /**
     * amount : 1234
     * user_nickname : 手机用户5
     * avatar :
     * uid : 7
     * gift_id : 10
     * type : 1
     * anchor_id : 9
     * is_guard : 0
     */

    private String amount;
    private String user_nickname;
    private String avatar;
    private int uid;
    private int gift_id;
    private int type;
    private int anchor_id;
    private int is_guard;
    private int order;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getGift_id() {
        return gift_id;
    }

    public void setGift_id(int gift_id) {
        this.gift_id = gift_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAnchor_id() {
        return anchor_id;
    }

    public void setAnchor_id(int anchor_id) {
        this.anchor_id = anchor_id;
    }

    public int getIs_guard() {
        return is_guard;
    }

    public void setIs_guard(int is_guard) {
        this.is_guard = is_guard;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
