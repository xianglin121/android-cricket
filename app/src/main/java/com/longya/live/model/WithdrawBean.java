package com.longya.live.model;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/1/8
 */
public class WithdrawBean {

    /**
     * id : 1
     * addtime : 2021-12-02 14:24:43
     * uid : 2
     * bank_card_id : 5
     * masonry : 100
     * amount : 1.00
     * arrive_amount : 1.00
     * rate : 0.00
     * status : 0
     * info :
     * check_time :
     * name : 提现
     */

    private int id;
    private String addtime;
    private int uid;
    private int bank_card_id;
    private int masonry;
    private String amount;
    private String arrive_amount;
    private String rate;
    private int status;
    private String info;
    private String check_time;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getBank_card_id() {
        return bank_card_id;
    }

    public void setBank_card_id(int bank_card_id) {
        this.bank_card_id = bank_card_id;
    }

    public int getMasonry() {
        return masonry;
    }

    public void setMasonry(int masonry) {
        this.masonry = masonry;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getArrive_amount() {
        return arrive_amount;
    }

    public void setArrive_amount(String arrive_amount) {
        this.arrive_amount = arrive_amount;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCheck_time() {
        return check_time;
    }

    public void setCheck_time(String check_time) {
        this.check_time = check_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
