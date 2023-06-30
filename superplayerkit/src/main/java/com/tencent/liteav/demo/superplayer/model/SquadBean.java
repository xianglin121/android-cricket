package com.tencent.liteav.demo.superplayer.model;

import java.util.List;

public class SquadBean {
    public String name;
    public List<SquadInnerBean> list;

    public static class SquadInnerBean {
        public Double strikeRate;
        public Integer playerId;
        public Integer sixes;
        public String playerLogo;
        public String playerName;
        public Integer fours;
        public String bat;
    }
}
