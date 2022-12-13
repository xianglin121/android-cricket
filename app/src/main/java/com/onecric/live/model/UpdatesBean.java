package com.onecric.live.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/9/5
 */
public class UpdatesBean implements MultiItemEntity {
    public static final int HEAD = 1;
    public static final int CONTENT = 0;

    private int id;
    private String title;
    private String player;
    private String lastupdatedate;
    private String img;
    private String addtime;

    private int itemType;

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getLastupdatedate() {
        return lastupdatedate;
    }

    public void setLastupdatedate(String lastupdatedate) {
        this.lastupdatedate = lastupdatedate;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }
}
