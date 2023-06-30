package com.tencent.liteav.demo.superplayer.model;

import java.util.List;

public class SquadDataBean {
    public String score;
    public String name;
    public List<SquadBean> list;
    public WinBean win;

    public static class WinBean {
        public String homeName;
        public String awayName;
        public String homeLogo;
        public String awayLogo;
        public double homeWinProbabilities;
        public double awayWinProbabilities;
    }

}
