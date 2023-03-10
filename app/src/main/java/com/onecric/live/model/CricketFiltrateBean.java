package com.onecric.live.model;

public class CricketFiltrateBean {
    private boolean isCheck;
    private String name;
    private String id;

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
