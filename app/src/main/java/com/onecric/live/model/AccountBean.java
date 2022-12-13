package com.onecric.live.model;

import java.io.Serializable;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/12/16
 */
public class AccountBean implements Serializable {

    /**
     * id : 5
     * uid : 2
     * bank_name : 中国银行
     * card_number : 0000000000000000
     * name : 张三
     * id_card : 360722199002155568
     * card_number_hide : 0000************000
     * id_card_hide : 3607***********5568
     * name_hide : 张**
     */

    private int id;
    private int uid;
    private String bank_name;
    private String card_number;
    private String name;
    private String id_card;
    private String card_number_hide;
    private String id_card_hide;
    private String name_hide;

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

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getCard_number_hide() {
        return card_number_hide;
    }

    public void setCard_number_hide(String card_number_hide) {
        this.card_number_hide = card_number_hide;
    }

    public String getId_card_hide() {
        return id_card_hide;
    }

    public void setId_card_hide(String id_card_hide) {
        this.id_card_hide = id_card_hide;
    }

    public String getName_hide() {
        return name_hide;
    }

    public void setName_hide(String name_hide) {
        this.name_hide = name_hide;
    }
}
