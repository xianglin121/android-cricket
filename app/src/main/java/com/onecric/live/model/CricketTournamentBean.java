package com.onecric.live.model;

import java.util.List;

public class CricketTournamentBean {
    private int tournament_id;
    private String name;
    private String type;
    private List<CricketMatchBean> cricket_match;

    //自定义参数
    private boolean isCheck;

    public int getTournament_id() {
        return tournament_id;
    }

    public void setTournament_id(int tournament_id) {
        this.tournament_id = tournament_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<CricketMatchBean> getCricket_match() {
        return cricket_match;
    }

    public void setCricket_match(List<CricketMatchBean> cricket_match) {
        this.cricket_match = cricket_match;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
