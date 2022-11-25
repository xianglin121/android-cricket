package com.longya.live.model;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/11/25
 */
public class CustomMsgBean {
    private int type;
    private GiftBean gift;
    private ColorMsgBean color;
    private NobelMsgBean nobel;
    private NormalMsgBean normal;
    private BroadcastMsgBean info;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public GiftBean getGift() {
        return gift;
    }

    public void setGift(GiftBean gift) {
        this.gift = gift;
    }

    public ColorMsgBean getColor() {
        return color;
    }

    public void setColor(ColorMsgBean color) {
        this.color = color;
    }

    public NobelMsgBean getNobel() {
        return nobel;
    }

    public void setNobel(NobelMsgBean nobel) {
        this.nobel = nobel;
    }

    public NormalMsgBean getNormal() {
        return normal;
    }

    public void setNormal(NormalMsgBean normal) {
        this.normal = normal;
    }

    public BroadcastMsgBean getInfo() {
        return info;
    }

    public void setInfo(BroadcastMsgBean info) {
        this.info = info;
    }
}
