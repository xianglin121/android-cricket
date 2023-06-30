package com.tencent.liteav.demo.superplayer.model;

import java.util.List;

public class CompetitionBean {
    public CompetitionBean(int id,String score,String name,String order){
        this.id = id;
        this.score = score;
        this.name = name;
        this.order = order;
    }
    public int id;
    public String score;
    public String name;
    public String order;
    public ListDataBean bean;
    public boolean isSelected;

    public static class ListDataBean{
        public List<ScorecardBatterBean> batterList;
        public List<ScorecardBowlerBean> bowlerList;
        public List<ScorecardWicketBean> wicketList;
        public String extras;
        public String noBattingName;

        public ListDataBean(List<ScorecardBatterBean> batterList, List<ScorecardBowlerBean> bowlerList, List<ScorecardWicketBean> wicketList, String extras, String noBattingName) {
            this.batterList = batterList;
            this.bowlerList = bowlerList;
            this.wicketList = wicketList;
            this.extras = extras;
            this.noBattingName = noBattingName;
        }

        public List<ScorecardBatterBean> getBatterList() {
            return batterList;
        }

        public void setBatterList(List<ScorecardBatterBean> batterList) {
            this.batterList = batterList;
        }

        public List<ScorecardBowlerBean> getBowlerList() {
            return bowlerList;
        }

        public void setBowlerList(List<ScorecardBowlerBean> bowlerList) {
            this.bowlerList = bowlerList;
        }

        public List<ScorecardWicketBean> getWicketList() {
            return wicketList;
        }

        public void setWicketList(List<ScorecardWicketBean> wicketList) {
            this.wicketList = wicketList;
        }

        public String getExtras() {
            return extras;
        }

        public void setExtras(String extras) {
            this.extras = extras;
        }

        public String getNoBattingName() {
            return noBattingName;
        }

        public void setNoBattingName(String noBattingName) {
            this.noBattingName = noBattingName;
        }
    }

}
