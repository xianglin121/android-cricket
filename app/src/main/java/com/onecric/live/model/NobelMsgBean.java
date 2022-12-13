package com.onecric.live.model;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/11/26
 */
public class NobelMsgBean {
    private int level;
    private String exp_icon;//等级图片
    private String guard_icon;//贵族图标
    private String guard_name;//坐骑名称
    private String guard_swf;//坐骑svga
    private int is_guard;//是否是贵族 0：否 1：是
    private int is_room;//是否是房主 0：否 1：是

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public String getGuard_name() {
        return guard_name;
    }

    public void setGuard_name(String guard_name) {
        this.guard_name = guard_name;
    }

    public String getGuard_swf() {
        return guard_swf;
    }

    public void setGuard_swf(String guard_swf) {
        this.guard_swf = guard_swf;
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
