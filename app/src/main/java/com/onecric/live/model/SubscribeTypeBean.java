package com.onecric.live.model;

public class SubscribeTypeBean {
    private String type;

    private String name;

    private int is_subscribe;

    public int getSelect_subscribe() {
        return select_subscribe;
    }

    public void setSelect_subscribe(int select_subscribe) {
        this.select_subscribe = select_subscribe;
    }

    private int select_subscribe;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setIs_subscribe(int is_subscribe) {
        this.is_subscribe = is_subscribe;
    }

    public int getIs_subscribe() {
        return this.is_subscribe;
    }
}
