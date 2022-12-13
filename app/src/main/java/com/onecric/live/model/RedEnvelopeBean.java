package com.onecric.live.model;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/1/19
 */
public class RedEnvelopeBean {

    /**
     * id : 21
     * addtime : 1642497903
     * amount : 100
     * number : 5
     * user_nickname : 手机用户0152
     * countdown_time : 170
     * receive_time : 5
     */

    private int id;
    private int addtime;
    private int amount;
    private int number;
    private String user_nickname;
    private int countdown_time;
    private int receive_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAddtime() {
        return addtime;
    }

    public void setAddtime(int addtime) {
        this.addtime = addtime;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public int getCountdown_time() {
        return countdown_time;
    }

    public void setCountdown_time(int countdown_time) {
        this.countdown_time = countdown_time;
    }

    public int getReceive_time() {
        return receive_time;
    }

    public void setReceive_time(int receive_time) {
        this.receive_time = receive_time;
    }
}
