package com.longya.live.model;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/12/4
 */
public class CoinBean {

    /**
     * id : 6
     * addtime : 1607601037
     * name : 60钻石
     * coin : 60
     * coin_ios : 60
     * money : 6.00
     * money_ios : 0.00
     * product_id : coin_60
     * give : 0
     * list_order : 9999
     */

    private int id;
    private int addtime;
    private String name;
    private int coin;
    private int coin_ios;
    private String money;
    private String money_ios;
    private String product_id;
    private int give;
    private int list_order;

    private boolean isSelect;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getCoin_ios() {
        return coin_ios;
    }

    public void setCoin_ios(int coin_ios) {
        this.coin_ios = coin_ios;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getMoney_ios() {
        return money_ios;
    }

    public void setMoney_ios(String money_ios) {
        this.money_ios = money_ios;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public int getGive() {
        return give;
    }

    public void setGive(int give) {
        this.give = give;
    }

    public int getList_order() {
        return list_order;
    }

    public void setList_order(int list_order) {
        this.list_order = list_order;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
