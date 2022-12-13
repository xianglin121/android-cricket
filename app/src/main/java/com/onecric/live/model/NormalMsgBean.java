package com.onecric.live.model;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/12/11
 */
public class NormalMsgBean {
    private int isXCBarrage;//是否是气泡 0：否 1：是
    private int xcBarrageType;//气泡等级
    private String text;//文本内容
    private String exp_icon;//等级图片
    private String guard_icon;//贵族图标
    private int is_guard;//是否是贵族 0：否 1：是
    private int is_room;//是否是房主 0：否 1：是

    public int getIsXCBarrage() {
        return isXCBarrage;
    }

    public void setIsXCBarrage(int isXCBarrage) {
        this.isXCBarrage = isXCBarrage;
    }

    public int getXcBarrageType() {
        return xcBarrageType;
    }

    public void setXcBarrageType(int xcBarrageType) {
        this.xcBarrageType = xcBarrageType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getExp_icon() {
        return exp_icon;
    }

    public void setExp_icon(String exp_icon) {
        this.exp_icon = exp_icon;
    }

    public String getGuard_icon() {
        return guard_icon;
    }

    public void setGuard_icon(String guard_icon) {
        this.guard_icon = guard_icon;
    }

    public int getIs_guard() {
        return is_guard;
    }

    public void setIs_guard(int is_guard) {
        this.is_guard = is_guard;
    }

    public int getIs_room() {
        return is_room;
    }

    public void setIs_room(int is_room) {
        this.is_room = is_room;
    }
}
