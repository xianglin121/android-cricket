package com.longya.live.event;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/3/7
 */
public class UpdateMatchDataFollowEvent {
    public int id;
    public int type;

    public UpdateMatchDataFollowEvent(int id, int type) {
        this.id = id;
        this.type = type;
    }
}
