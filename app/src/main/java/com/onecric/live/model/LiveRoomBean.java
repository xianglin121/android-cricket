package com.onecric.live.model;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/11/20
 */
public class LiveRoomBean {
    private LiveRoomInfoBean info;
    private LiveUserBean userData;

    public LiveRoomInfoBean getInfo() {
        return info;
    }

    public void setInfo(LiveRoomInfoBean info) {
        this.info = info;
    }

    public LiveUserBean getUserData() {
        return userData;
    }

    public void setUserData(LiveUserBean userData) {
        this.userData = userData;
    }
}
